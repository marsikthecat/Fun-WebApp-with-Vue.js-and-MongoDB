package org.example.backend.controller;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import javax.crypto.SecretKey;
import org.example.backend.SafetyCat;
import org.example.backend.TokenManager;
import org.example.backend.model.User;
import org.example.backend.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {
  private static final String SECRET_PASS = "storeThisInTheEnv!";
  public int counter = 0;
  private final UserRepository repository;

  public UserController(UserRepository repository) {
    this.repository = repository;
  }

  @GetMapping
  public List<User> getAllUsers() {
    return repository.findAll();
  }

  @PostMapping("/registering")
  public boolean register(@RequestBody User user) throws Exception {
    if (isUserRegistered(user.getName(), user.getPassword()) != null) {
      return false;
    } else {
      byte[] otpKey = SafetyCat.generateOtpKey(user.getPassword().length());
      byte[] encryptedPassword = SafetyCat.encryptWithOtp(user.getPassword(), otpKey);
      byte[] salt = new byte[16];
      new SecureRandom().nextBytes(salt);
      SecretKey aesKey = SafetyCat.aesKeyFromPasswort(user.getPassword(), salt);
      byte[] encryptedOtpKey = SafetyCat.encryptOtpKeyWithAes(otpKey, aesKey);
      User newUser = new User(user.getName(),
              Base64.getEncoder().encodeToString(encryptedPassword),
              Base64.getEncoder().encodeToString(encryptedOtpKey),
              Base64.getEncoder().encodeToString(salt));
      newUser.setId(counter++);
      repository.save(newUser);
      return true;
    }
  }

  public User isUserRegistered(String username, String password) {
    List<User> userList = repository.findAll();
    for (User user : userList) {
      if (user.getName().equals(username)) {
        try {
          byte[] encryptedPassword = Base64.getDecoder().decode(user.getPassword());
          byte[] encryptedOtpKey = Base64.getDecoder().decode(user.getOtpKey());
          byte[] salt = Base64.getDecoder().decode(user.getSalt());
          SecretKey aesKey = SafetyCat.aesKeyFromPasswort(password, salt);
          byte[] otpKey = SafetyCat.decryptOtpKeyWithAes(encryptedOtpKey, aesKey);
          byte[] encryptedInputPassword = SafetyCat.encryptWithOtp(password, otpKey);
          if (MessageDigest.isEqual(encryptedInputPassword, encryptedPassword)) {
            return user;
          }
        } catch (Exception e) {
          System.out.println(e.getMessage());
        }
      }
    }
    return null;
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody User user) {
    if (user.getName().equals("admin-marsik") && user.getPassword().equals(SECRET_PASS)) {
      return ResponseEntity.ok().body("admin-login");
    }
    User u = isUserRegistered(user.getName(), user.getPassword());
    if (u != null) {
      String token = TokenManager.createToken(16);
      int userIdInt = Integer.parseInt(u.getId());
      TokenManager.addToken(token, userIdInt);
      return ResponseEntity.ok().body(u.getId());
    } else {
      return ResponseEntity.status(
              HttpStatus.UNAUTHORIZED).body("Login failed, check your data or sign up");
    }
  }

  @GetMapping("/logout")
  public boolean logout(@RequestParam String userId) {
    return TokenManager.removeToken(Integer.parseInt(userId)) != null;
  }

  @GetMapping("/checkUser")
  public boolean checkUser(@RequestParam String userId) {
    return TokenManager.exists(Integer.parseInt(userId));
  }
}
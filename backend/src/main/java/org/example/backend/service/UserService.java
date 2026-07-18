package org.example.backend.service;

import org.example.backend.SafetyCat;
import org.example.backend.TokenManager;
import org.example.backend.model.User;
import org.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import javax.crypto.SecretKey;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.List;

@Service
public class UserService {

  private static final int TOKEN_SIZE = 256;

  @Value("${spring.datasource.username}")
  private String admin_username;

  @Value("${spring.datasource.password}")
  private String admin_password;

  public int counter;
  private final UserRepository repository;

  public UserService(UserRepository repository) {
    this.repository = repository;
    counter = repository.findAll().size();
  }

  public List<User> getAllUsers(String token) {
    if (TokenManager.exists(token)) {
      return repository.findAll();
    }
    return null;
  }

  public boolean register(@RequestBody User user) throws Exception {
    if (isUserRegistered(user.getName(), user.getPassword()) != null) {
      return false;
    } else {
      byte[] otpKey = SafetyCat.generateOtpKey(user.getPassword().length());
      byte[] encryptedPassword = SafetyCat.encryptWithOtp(user.getPassword(), otpKey);
      byte[] salt = SafetyCat.generateSalt();
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

  private User isUserRegistered(String username, String password) {
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

  public String login(@RequestBody User user) {
    User u = isUserRegistered(user.getName(), user.getPassword());
    if (u != null) {
      return TokenManager.setToken(TOKEN_SIZE);
    } else {
      return "";
    }
  }

  public String adminLogin(@RequestBody User user) {
    if (user.getName().equals(admin_username) && user.getPassword().equals(admin_password)) {
      return TokenManager.setToken(TOKEN_SIZE);
    } else {
      return "";
    }
  }
}
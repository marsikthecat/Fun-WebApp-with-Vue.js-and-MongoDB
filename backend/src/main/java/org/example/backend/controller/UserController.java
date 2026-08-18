package org.example.backend.controller;

import java.util.List;
import java.util.Map;

import org.example.backend.LoginResult;
import org.example.backend.TokenManager;
import org.example.backend.model.User;
import org.example.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public ResponseEntity<?> getAllUsers(@RequestHeader("Authorization") String token) {
    List<User> users = userService.getAllUsers(token);
    if (users == null) {
      ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    return ResponseEntity.status(HttpStatus.OK).body(users);
  }

  @PostMapping("/registering")
  public ResponseEntity<?> register(@RequestBody User user) throws Exception {
    boolean isSuccess = userService.register(user);
    if (isSuccess) {
      return ResponseEntity.status(HttpStatus.OK).build();
    }
    return ResponseEntity.status(HttpStatus.CONFLICT).build();
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody User user) {
    LoginResult loginResult = userService.login(user);
    if (loginResult.token().isEmpty()) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    return ResponseEntity.status(HttpStatus.OK).body(Map.of(
      "token", loginResult.token(),
      "isAdmin", loginResult.isAdmin()
    ));
  }

  @PostMapping("/logout")
  public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
    if (TokenManager.destroyToken(token) != null) {
      return ResponseEntity.status(HttpStatus.OK).build();
    }
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }

  @PostMapping("/checkUser")
  public ResponseEntity<?> checkUser(@RequestHeader("Authorization") String token) {
    if (TokenManager.exists(token)) {
      return ResponseEntity.status(HttpStatus.OK).build();
    }
    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
  }
}
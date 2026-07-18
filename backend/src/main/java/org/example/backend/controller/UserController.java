package org.example.backend.controller;

import org.example.backend.TokenManager;
import org.example.backend.model.User;
import org.example.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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
    String token = userService.login(user);
    if (token.isEmpty()) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    return ResponseEntity.status(HttpStatus.OK).body(token);
  }

  @PostMapping("/admin-login")
  public ResponseEntity<?> adminLogin(@RequestBody User user) {
    String token = userService.adminLogin(user);
    if (token.isEmpty()) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(token);
    } else {
      return ResponseEntity.status(HttpStatus.OK).body(token);
    }
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
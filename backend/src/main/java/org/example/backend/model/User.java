package org.example.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {
  @Id
  private String id;
  private final String name;
  private final String password;
  private final String otpKey;
  private final String salt;

  public User(String name, String password, String otpKey, String salt) {
    this.name = name;
    this.password = password;
    this.otpKey = otpKey;
    this.salt = salt;
  }

  public String getName() {
    return name;
  }

  public void setId(int id) {
    this.id = String.valueOf(id);
  }

  public String getId() {
    return id;
  }

  public String getPassword() {
    return password;
  }

  public String getOtpKey() {
    return otpKey;
  }

  public String getSalt() {
    return salt;
  }
}
package org.example.backend.controller;

import org.example.backend.TokenManager;
import org.example.backend.model.Message;
import org.example.backend.service.MailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/message")
@CrossOrigin(origins = "http://localhost:5173")
public class MailController {

  private final MailService mailService;

  public MailController(MailService mailService) {
    this.mailService = mailService;
  }

  @GetMapping
  public ResponseEntity<?> getAllMessages(@RequestHeader("Authorization") String token) {
    if (!TokenManager.exists(token)) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    List<Message> messages = mailService.getAllMessages();
    return ResponseEntity.status(HttpStatus.OK).body(messages);
  }

  @PostMapping("/send")
  public ResponseEntity<?> storeMessage(@RequestBody Message message) {
    mailService.storeMessage(message);
    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
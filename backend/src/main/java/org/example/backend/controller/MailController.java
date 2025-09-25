package org.example.backend.controller;

import java.util.List;
import org.example.backend.model.Message;
import org.example.backend.repository.MessageRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
@CrossOrigin(origins = "http://localhost:5173")
public class MailController {
  private final MessageRepository messageRepository;

  public MailController(MessageRepository messageRepository) {
    this.messageRepository = messageRepository;
  }

  @GetMapping
  public List<Message> getAllMessages() {
    return messageRepository.findAll();
  }

  @PostMapping("/send")
  public ResponseEntity<?> storeMessage(@RequestBody Message message) {
    messageRepository.save(message);
    return ResponseEntity.status(HttpStatus.OK).body("Message successfully send!");
  }
}
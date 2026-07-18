package org.example.backend.service;

import org.example.backend.model.Message;
import org.example.backend.repository.MessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

@Service
public class MailService {

  private final MessageRepository messageRepository;

  public MailService(MessageRepository messageRepository) {
    this.messageRepository = messageRepository;
  }

  public List<Message> getAllMessages() {
    return messageRepository.findAll();
  }

  public void storeMessage(@RequestBody Message message) {
    messageRepository.save(message);
  }
}
package org.example.backend.controller;

import java.util.List;
import org.example.backend.TokenManager;
import org.example.backend.model.QuizQuestion;
import org.example.backend.service.QuizService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quiz")
@CrossOrigin(origins = "http://localhost:5173")
public class QuizController {

  private final QuizService quizService;

  public QuizController(QuizService quizService) {
    this.quizService = quizService;
  }

  @GetMapping
  public ResponseEntity<?> getAllQuestions(@RequestHeader("Authorization") String token) {
    if (!TokenManager.exists(token)) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    List<QuizQuestion> questionList = quizService.getAllQuestions();
    return ResponseEntity.status(HttpStatus.OK).body(questionList);
  }

  @PostMapping("/store")
  public ResponseEntity<?> storeQuestion(@RequestParam String question,
                                         @RequestParam int correctIndex,
                                         @RequestBody List<String> options) {
    if (correctIndex < 0 || correctIndex > 3) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Correct index out of bounds");
    } else if (options.size() != 4) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong number of options");
    } else if (question.trim().isEmpty()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Question is empty");
    }
    QuizQuestion newQuestion = quizService.storeQuestion(question, correctIndex, options);
    return ResponseEntity.status(HttpStatus.OK).body(newQuestion);
  }

  @DeleteMapping("/deleteQuestion")
  public ResponseEntity<?> deleteQuestion(@RequestParam String questionID) {
    QuizQuestion question = quizService.deleteQuestion(questionID);
    if (question == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Question with id: " + questionID + " not found");
    }
    return ResponseEntity.status(HttpStatus.OK).body(question);
  }
}
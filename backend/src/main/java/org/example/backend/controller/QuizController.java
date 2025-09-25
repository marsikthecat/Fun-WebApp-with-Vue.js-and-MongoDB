package org.example.backend.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.example.backend.model.QuizQuestion;
import org.example.backend.repository.QuizRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quiz")
@CrossOrigin(origins = "http://localhost:5173")
public class QuizController {

  private final QuizRepository quizRepository;

  public QuizController(QuizRepository quizRepository) {
    this.quizRepository = quizRepository;
  }

  @GetMapping
  public ResponseEntity<?> getAllQuestions() {
    List<QuizQuestion> questionList = quizRepository.findAll();
    Collections.shuffle(questionList);
    return ResponseEntity.status(HttpStatus.OK).body(questionList);
  }

  @PostMapping("/store")
  public ResponseEntity<?> storeQuestion(@RequestParam String question,
                                         @RequestParam int correctIndex,
                                         @RequestBody List<String> options) {
    if (correctIndex < 0 || correctIndex > 3) {
      return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
              "Index have to be between 0 and 3");
    } else if (options.size() != 4) {
      return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("You need to have 4 options");
    } else if (question.trim().isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Question cannot be empty");
    } else {
      QuizQuestion quizQuestion = new QuizQuestion(question, options, correctIndex);
      quizRepository.save(quizQuestion);
      return ResponseEntity.status(HttpStatus.OK).body(quizQuestion);
    }
  }

  @DeleteMapping("/deleteQuestion")
  public ResponseEntity<?> deleteQuestion(@RequestParam String questionID) {
    Optional<QuizQuestion> question = quizRepository.findById(questionID);
    if (question.isPresent()) {
      quizRepository.delete(question.get());
      return ResponseEntity.status(HttpStatus.ACCEPTED).body("Question successfully deleted");
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
              "Question with id: " + questionID + "not found");
    }
  }
}
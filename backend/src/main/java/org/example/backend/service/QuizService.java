package org.example.backend.service;

import org.example.backend.model.QuizQuestion;
import org.example.backend.repository.QuizRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

  private final QuizRepository quizRepository;

  public QuizService(QuizRepository quizRepository) {
    this.quizRepository = quizRepository;
  }

  public List<QuizQuestion> getAllQuestions() {
    List<QuizQuestion> questionList = quizRepository.findAll();
    Collections.shuffle(questionList);
    return questionList;
  }

  public QuizQuestion storeQuestion(String question, int correctIndex, List<String> options) {
      QuizQuestion quizQuestion = new QuizQuestion(question, options, correctIndex);
      quizRepository.save(quizQuestion);
      return quizQuestion;
  }

  public QuizQuestion deleteQuestion(@RequestParam String questionID) {
    Optional<QuizQuestion> question = quizRepository.findById(questionID);
    if (question.isPresent()) {
      quizRepository.delete(question.get());
      return question.get();
    } else {
      return null;
    }
  }
}
package org.example.backend.model;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "questions")
public class QuizQuestion {
  @Id
  private String id;
  private final String question;
  private final int correctIndex;
  private final List<String> options;

  public QuizQuestion(String question, List<String> options, int correctIndex) {
    this.question = question;
    this.options = options;
    this.correctIndex = correctIndex;
  }

  public int getCorrectIndex() {
    return correctIndex;
  }

  public List<String> getOptions() {
    return options;
  }

  public String getQuestion() {
    return question;
  }

  public String getId() {
    return id;
  }
}
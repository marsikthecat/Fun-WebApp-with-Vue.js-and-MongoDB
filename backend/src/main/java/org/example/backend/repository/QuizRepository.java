package org.example.backend.repository;

import org.example.backend.model.QuizQuestion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends MongoRepository<QuizQuestion, String> {}
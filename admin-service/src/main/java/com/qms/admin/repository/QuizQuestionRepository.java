package com.qms.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qms.admin.model.QuizQuestion;

@Repository
public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Long> {

	boolean existsByQuizId(Long quizId);

	boolean existsByQuestionId(Long questionId);
}

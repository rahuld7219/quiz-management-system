package com.qms.admin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qms.admin.model.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

	Optional<Question> findByIdAndDeleted(Long id, String deleted);

//	boolean existsByQuizId(Long quizId); TODO: find a better way

}

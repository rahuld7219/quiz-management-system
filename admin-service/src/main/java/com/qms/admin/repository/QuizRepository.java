package com.qms.admin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qms.admin.model.Question;
import com.qms.admin.model.Quiz;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

	Optional<Quiz> findByCategoryId(Long id);
	
	Long countByDeleted(String deleted);

	Optional<Quiz> findByIdAndDeleted(Long id, String deleted);

//	boolean existsByQuestionId(Long questionId); //TODO: find a better way
	
//	boolean existsById(Long id);
}

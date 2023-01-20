package com.qms.common.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qms.common.constant.Deleted;
import com.qms.common.dto.QuizQuestionQuestion;
import com.qms.common.model.QuizQuestion;

@Repository
public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Long> {

	boolean existsByQuizId(Long quizId);

	boolean existsByQuestionIdAndDeleted(Long questionId, Deleted deleted);

	List<QuizQuestionQuestion> getQuestionByQuizIdAndDeleted(Long quizId, Deleted deleted);

	Optional<List<QuizQuestion>> findAllByQuizIdAndDeletedAndQuestionIdIn(Long id, Deleted deleted,
			List<Long> existingQuestionIds);

	boolean existsByQuizIdAndDeleted(Long quizId, Deleted deleted);

}

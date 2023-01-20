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

//	@Query(value = "SELECT distinct q.id questionId, q.question questionDetail, q.option_a optionA, q.option_b optionB, q.option_c optionC, q.option_d optionD FROM question q INNER JOIN quiz_question qq ON q.id= qq.question_id WHERE qq.quiz_id=?1 AND qq.deleted = 'N'", nativeQuery = true)
	List<QuizQuestionQuestion> getQuestionByQuizIdAndDeleted(Long quizId, Deleted deleted); // TODO: above query is
																							// better doing in single
																							// join,
	// while this method doing in 2 joins

	
	Optional<List<QuizQuestion>> findAllByQuizIdAndDeletedAndQuestionIdIn(Long id, Deleted deleted,
			List<Long> existingQuestionIds);

	boolean existsByQuizIdAndDeleted(Long quizId, Deleted deleted);

}

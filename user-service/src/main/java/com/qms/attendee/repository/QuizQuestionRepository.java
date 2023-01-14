package com.qms.attendee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.qms.attendee.dto.QuizQuestionQuestion;
import com.qms.attendee.model.Question;
import com.qms.attendee.model.QuizQuestion;

@Repository
public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Long> {

//	@Query(value = "SELECT distinct q.id, q.question,q.option_a,q.option_b,q.option_c,q.option_d FROM question q INNER JOIN quiz_question qq ON q.id= qq.question_id WHERE qq.quiz_id=?1",
//			nativeQuery = true)
	List<QuizQuestionQuestion> getQuestionByQuizId(Long quizId); // TODO: above query is better doing in single join,
																	// while this method doing in 2 joins

	@Query(value = "SELECT distinct q.id questionId, q.question questionDetail, q.option_a optionA, q.option_b optionB, q.option_c optionC, q.option_d optionD FROM question q INNER JOIN quiz_question qq ON q.id= qq.question_id WHERE qq.quiz_id=?1", nativeQuery = true)
	List<Map<>> getQuestionByQuizIdCopy(Long quizId); // TODO: delete this and decide which one to keep
															// or combine...

}

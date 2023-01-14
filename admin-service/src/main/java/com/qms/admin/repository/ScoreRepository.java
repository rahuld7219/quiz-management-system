package com.qms.admin.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.qms.admin.model.Score;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {

	boolean existsByQuizId(Long quizId);

	// TODO: optimize, find way to write using only JPA method OR use JPQL
	// instead of native, (native vs JPQL ??)
	
	@Query(value = "Select quiz_id quizId, count(*) attendees "
			+ "from user_score "
			+ "group by quizId "
			+ "order by attendees desc "
			+ "limit 5", nativeQuery = true)
	List<Map<String, Object>> getAttendeeCountGroupByQuiz();

	@Query(value = "select c.name categoryName, q.title quizTitle, u.email_id userId, s.score "
			+ "from user u, user_score s, category c, quiz q "
			+ "where u.id = s.user_id AND q.id = s.quiz_id AND c.id = q.category_id "
			+ "order by c.name, q.title, s.score desc " + "limit 10", nativeQuery = true)
	List<Map<String, Object>> getRecordFilteredByCategoryThenQuizThenAttendeeScore(); // TODO: show only top score for a user id in paricular category and particular quiz

}

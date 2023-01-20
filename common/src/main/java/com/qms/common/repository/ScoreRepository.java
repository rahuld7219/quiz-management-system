package com.qms.common.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.qms.common.dto.RankDetail;
import com.qms.common.model.Score;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {

	boolean existsByQuizId(Long quizId);

	@Query(value = "Select quiz_id quizId, count(DISTINCT user_id) attendees " + "from user_score " + "group by quizId "
			+ "order by attendees desc " + "limit 5", nativeQuery = true)
	List<Map<String, Object>> getAttendeeCountGroupByQuiz();

	@Query(value = "SELECT ROW_NUMBER() OVER (ORDER BY max(score) DESC) AS `rank`, CONCAT_WS(\" \", first_name, last_name) AS name, email_id AS email, max(score) AS totalScore FROM user AS u, user_score AS s WHERE u.id = s.user_id AND s.quiz_id = ?1 GROUP BY email_id ORDER BY `rank`", nativeQuery = true)
	List<RankDetail> getTopScorers(Long quizId);

	@Query(value = "Select count(distinct s.quiz_id) attemptedQuizCount from user_score s where s.user_id = ?1", nativeQuery = true)
	Long countDistinctQuizByUserId(Long userId);

	@Query(value = "SELECT `rank`, name,  email, totalScore FROM (WITH top_scorers AS (SELECT ROW_NUMBER() OVER (ORDER BY max(score) DESC) AS `rank`, CONCAT_WS(\" \", first_name, last_name) AS name, email_id AS email, max(score) AS totalScore FROM user AS u, user_score AS s WHERE u.id = s.user_id AND s.quiz_id = ?1 GROUP BY email_id ORDER BY `rank`) SELECT *, 1 AS my_filter FROM top_scorers WHERE email = ?2 UNION ALL SELECT *, 2 AS my_filter FROM top_scorers) AS rank_list ORDER BY my_filter, `rank`", nativeQuery = true)
	List<RankDetail> getTopScorers(Long quizId, String emailId);

}

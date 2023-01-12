package com.qms.attendee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qms.attendee.model.Score;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {

//	@Query(value =
//			"Select count(distinct s.quiz_id) attemptedQuizCount "
//			+ "from user_score s "
//			+ "where s.user_id = ?1",
//			nativeQuery = true)
	Long countDistinctQuizIdByUserId(Long userId); // TODO: uncomment query if not working

	// TODO: optimize, find way to write using only JPA method OR use JPQL
	// instead of native, (native vs JPQL ??)

}

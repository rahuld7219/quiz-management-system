package com.qms.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qms.admin.model.Score;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {

	boolean existsByQuizId(Long quizId);

}

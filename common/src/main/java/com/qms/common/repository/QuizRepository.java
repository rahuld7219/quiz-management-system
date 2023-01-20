package com.qms.common.repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.qms.common.constant.Deleted;
import com.qms.common.model.Quiz;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

	Optional<Quiz> findByCategoryId(Long id);

	Long countByDeleted(Deleted deleted);

	Optional<Quiz> findByIdAndDeleted(Long id, Deleted deleted);

	@Query(value = "select c.name categoryName, count(*) quizzes " + "from category c, quiz q "
			+ "where c.id = q.category_id And q.deleted = 'N' " + "group by c.name", nativeQuery = true)
	List<Map<String, Object>> countQuizByCategory();

	@Query(value = "select c.name categoryName, count(DISTINCT q.id) attemptedQuizzes "
			+ "from category c, quiz q, user_score s "
			+ "where c.id = q.category_id AND q.id = s.quiz_id AND s.user_id = ?1 "
			+ "group by c.name", nativeQuery = true)
	List<Map<String, Object>> countAttendedQuizByCategory(Long userId);

	String getTitleById(Long quizId);

	boolean existsByCategoryId(Long id);

	Collection<Quiz> findAllByDeleted(Deleted deleted);
}

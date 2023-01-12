package com.qms.attendee.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.qms.attendee.model.Quiz;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

//	Optional<Quiz> findByCategoryId(Long id);

	Long countByDeleted(String deleted);

//	Optional<Quiz> findByIdAndDeleted(Long id, String deleted);

	@Query(value = "select c.name categoryName, count(*) quizzes "
			+ "from category c, quiz q "
			+ "where c.id = q.category_id And q.deleted = 'N' "
			+ "group by c.name",
			nativeQuery = true)
	List<Map<String, Object>> countQuizByCategory(); // TODO: show count 0 for category not having any quiz OR we can also delete a category when its last associated quiz get deleted

	@Query(value = "select c.name, count(DISTINCT q.id) attemptedQuizzes "
			+ "from category c, quiz q, user_score s "
			+ "where c.id = q.category_id AND q.id = s.quiz_id AND s.user_id = 2 "
			+ "group by c.name",
			nativeQuery = true)
	List<Map<String, Object>> countAttendedQuizByCategory();
}

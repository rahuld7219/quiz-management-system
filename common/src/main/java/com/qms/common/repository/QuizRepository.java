package com.qms.common.repository;

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

	Long countByDeleted(String deleted);

	Optional<Quiz> findByIdAndDeleted(Long id, Deleted deleted);

//	boolean existsByQuestionId(Long questionId); //TODO: find a better way

//	boolean existsById(Long id);

//	Optional<Quiz> findByCategoryId(Long id);

//	Optional<Quiz> findByIdAndDeleted(Long id, String deleted);

	@Query(value = "select c.name categoryName, count(*) quizzes " + "from category c, quiz q "
			+ "where c.id = q.category_id And q.deleted = 'N' " + "group by c.name", nativeQuery = true)
	List<Map<String, Object>> countQuizByCategory(); // TODO: show count 0 for category not having any quiz OR we can
														// also delete a category when its last associated quiz get
														// deleted

	@Query(value = "select c.name, count(DISTINCT q.id) attemptedQuizzes " + "from category c, quiz q, user_score s "
			+ "where c.id = q.category_id AND q.id = s.quiz_id AND s.user_id = 2 "
			+ "group by c.name", nativeQuery = true)
	List<Map<String, Object>> countAttendedQuizByCategory();

	String getTitleById(Long quizId);
}

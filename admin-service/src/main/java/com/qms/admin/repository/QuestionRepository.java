package com.qms.admin.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qms.common.constant.Deleted;
import com.qms.common.model.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

	Optional<Question> findByIdAndDeleted(Long id, Deleted deleted);

	Optional<List<Question>> findAllByIdInAndDeleted(List<Long> questionsIds, Deleted deleted);

	Collection<Question> findAllByDeleted(Deleted deleted);

}

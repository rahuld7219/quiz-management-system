package com.qms.admin.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qms.admin.dto.QuizDTO;
import com.qms.admin.repository.CategoryRepository;
import com.qms.admin.repository.QuestionRepository;
import com.qms.admin.service.QuizService;
import com.qms.common.model.Category;
import com.qms.common.model.Quiz;
import com.qms.common.repository.QuizQuestionRepository;
import com.qms.common.repository.QuizRepository;
import com.qms.common.repository.ScoreRepository;

@Service
public class QuizServiceImpl implements QuizService {

	@Autowired
	private QuizRepository quizRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private ScoreRepository scoreRepository;

	@Autowired
	private QuizQuestionRepository quizQuestionRepository;

	@Override
	public Long addQuiz(final QuizDTO quizDTO) {

		return quizRepository.save(mapToModel(new Quiz(), quizDTO)).getId();
	}

	@Override
	public void updateQuiz(final String quizId, final QuizDTO quizDTO) {
		// check if quiz exist
		// update quiz

		Quiz quiz = quizRepository.findById(Long.valueOf(quizId))
				.orElseThrow(() -> new RuntimeException("Quiz not exist.")); // TODO: create custom exception

		quizRepository.save(mapToModel(quiz, quizDTO));
	}

	@Override
	public void deleteQuiz(final String quizId) {
		// check if quiz exist -> hard delete the quiz if quiz not have any
		// question(also consider if deleted is Y)
		// else soft delete iff quiz not have been attempted

		Quiz quiz = quizRepository.findById(Long.valueOf(quizId))
				.orElseThrow(() -> new RuntimeException("Quiz not exist.")); // TODO: create custom exception

		if (!quizQuestionRepository.existsByQuizId(Long.valueOf(quizId))) { // TODO: put in questionService, also
																			// consider if deleted is 'Y' ==>
																			// quizQuestionRepository.existsByQuizIdAndDeleted(quizId,
																			// "N")
			quizRepository.delete(quiz);
			return;
		}

		if (scoreRepository.existsByQuizId(Long.valueOf(quizId))) { // TODO: put in scoreService
			throw new RuntimeException("Cannot delete quiz, it has been attempted.");
		}

		quizRepository.save(quiz.setDeleted("Y"));

	}

	@Override
	public QuizDTO getQuiz(final String quizId) {
		Quiz quiz = quizRepository.findById(Long.valueOf(quizId))
				.orElseThrow(() -> new RuntimeException("Quiz not exist.")); // TODO: create custom exception

		return mapToDTO(quiz);
	}

	@Override
	public List<QuizDTO> getQuizList() {
		return quizRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toCollection(ArrayList::new));
	}

	@Override
	public Long getQuizCount() {
		return quizRepository.countByDeleted("N");
	}

	private Quiz mapToModel(final Quiz quiz, final QuizDTO quizDTO) {
		final Category category = categoryRepository.findById(quizDTO.getCategoryId()) // TODO: put in categoryService
				.orElseThrow(() -> new RuntimeException("Category not exist.")); // TODO: create custom
		return quiz.setTitle(quizDTO.getTitle()).setCategory(category);
	}

	private QuizDTO mapToDTO(final Quiz quiz) {
		return new QuizDTO().setTitle(quiz.getTitle()).setCategoryId(quiz.getCategory().getId());
	}

}

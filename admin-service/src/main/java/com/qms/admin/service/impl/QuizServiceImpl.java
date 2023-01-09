package com.qms.admin.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qms.admin.dto.request.QuizDTO;
import com.qms.admin.model.Category;
import com.qms.admin.model.Quiz;
import com.qms.admin.repository.CategoryRepository;
import com.qms.admin.repository.QuizRepository;
import com.qms.admin.service.QuizService;

@Service
public class QuizServiceImpl implements QuizService {

	@Autowired
	private QuizRepository quizRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public Long addQuiz(final QuizDTO quizDTO) {

		return quizRepository.save(mapToModel(quizDTO)).getId();
	}

	@Override
	public void updateQuiz(final String quizId, final QuizDTO quizDTO) {
		// check if quiz exist
		// update quiz

		Quiz quiz = quizRepository.findById(Long.valueOf(quizId))
				.orElseThrow(() -> new RuntimeException("Quiz not exist.")); // TODO: create custom exception
		final Category category = categoryRepository.findById(quizDTO.getCategoryId())
				.orElseThrow(() -> new RuntimeException("Category not exist.")); // TODO: create custom exception
		quiz.setCategory(category).setTitle(quizDTO.getTitle());
		quizRepository.save(quiz);
	}

	@Override
	public void deleteQuiz(String quizId) {
		// TODO check if quiz exist -> hard delete if quiz not have any question
		// else soft delete iff quiz not have been attempted

	}

	@Override
	public QuizDTO getQuiz(String quizId) {
		Quiz quiz = quizRepository.findById(Long.valueOf(quizId))
				.orElseThrow(() -> new RuntimeException("Quiz not exist.")); // TODO: create custom exception

		return mapToDTO(quiz);
	}

	@Override
	public List<QuizDTO> getQuizList() {
		return quizRepository.findAll().stream().map(this::mapToDTO)
				.collect(Collectors.toCollection(ArrayList::new));
	}

	private Quiz mapToModel(final QuizDTO quizDTO) {
		final Category category = categoryRepository.findById(quizDTO.getCategoryId())
				.orElseThrow(() -> new RuntimeException("Category not exist.")); // TODO: create custom
		return new Quiz().setTitle(quizDTO.getTitle()).setCategory(category);
	}

	private QuizDTO mapToDTO(Quiz quiz) {
		return new QuizDTO().setTitle(quiz.getTitle()).setCategoryId(quiz.getCategory().getId());
	}

}

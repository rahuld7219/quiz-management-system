package com.qms.admin.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.qms.admin.constant.AdminMessageConstant;
import com.qms.admin.dto.QuizDTO;
import com.qms.admin.dto.request.QuizRequest;
import com.qms.admin.dto.response.ListQuizResponse;
import com.qms.admin.dto.response.QuizCountResponse;
import com.qms.admin.dto.response.QuizResponse;
import com.qms.admin.exception.custom.CategoryNotExistException;
import com.qms.admin.exception.custom.QuizConstraintViolationException;
import com.qms.admin.repository.CategoryRepository;
import com.qms.admin.service.QuizService;
import com.qms.common.constant.CommonMessageConstant;
import com.qms.common.constant.Deleted;
import com.qms.common.exception.custom.QuizNotExistException;
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
	private ScoreRepository scoreRepository;

	@Autowired
	private QuizQuestionRepository quizQuestionRepository;

	@Override
	public QuizResponse addQuiz(final QuizRequest quizRequest) {

		Quiz quiz = quizRepository.save(mapToModel(new Quiz(), quizRequest));

		QuizResponse response = new QuizResponse();
		response.setData(response.new Data(mapToDTO(quiz))).setHttpStatus(HttpStatus.CREATED)
				.setMessage(AdminMessageConstant.QUIZ_CREATED).setResponseTime(LocalDateTime.now());
		return response;
	}

	@Override
	public QuizResponse updateQuiz(final Long quizId, final QuizRequest quizRequest) {
		// check if quiz exist
		// update quiz

		Quiz quiz = quizRepository.findByIdAndDeleted(quizId, Deleted.N)
				.orElseThrow(() -> new QuizNotExistException(CommonMessageConstant.QUIZ_NOT_EXIST));

		quizRepository.save(mapToModel(quiz, quizRequest));

		QuizResponse response = new QuizResponse();
		response.setData(response.new Data(mapToDTO(quiz))).setHttpStatus(HttpStatus.OK)
				.setMessage(AdminMessageConstant.QUIZ_UPDATED).setResponseTime(LocalDateTime.now());
		return response;
	}

	// TODO: analyse it more along with linking quiz questions API
	@Override
	public void deleteQuiz(final Long quizId) {
		// check if quiz exist -> hard delete the quiz if quiz not have any
		// question
		// else soft delete iff quiz not have been attempted

		Quiz quiz = quizRepository.findByIdAndDeleted(quizId, Deleted.N)
				.orElseThrow(() -> new QuizNotExistException(CommonMessageConstant.QUIZ_NOT_EXIST));

		if (scoreRepository.existsByQuizId(quizId)) {
			throw new QuizConstraintViolationException(AdminMessageConstant.QUIZ_SCORE_ASSOCIATION_VIOLATION);
		}

		if (!quizQuestionRepository.existsByQuizIdAndDeleted(quizId, Deleted.N)) {
			quizRepository.delete(quiz);
			return;
		}

		quizRepository.save(quiz.setDeleted(Deleted.Y));

	}

	@Override
	public QuizResponse getQuiz(final Long quizId) {
		Quiz quiz = quizRepository.findByIdAndDeleted(quizId, Deleted.N)
				.orElseThrow(() -> new QuizNotExistException(CommonMessageConstant.QUIZ_NOT_EXIST));

		QuizResponse response = new QuizResponse();
		response.setData(response.new Data(mapToDTO(quiz))).setHttpStatus(HttpStatus.OK)
				.setMessage(AdminMessageConstant.QUIZ_GOT).setResponseTime(LocalDateTime.now());
		return response;
	}

	@Override
	public ListQuizResponse getQuizList() {
		List<QuizDTO> quizzes = quizRepository.findAll().stream().map(this::mapToDTO)
				.collect(Collectors.toCollection(ArrayList::new));

		ListQuizResponse response = new ListQuizResponse();
		response.setData(response.new Data(quizzes)).setHttpStatus(HttpStatus.OK)
				.setMessage(AdminMessageConstant.QUIZ_GOT).setResponseTime(LocalDateTime.now());
		return response;
	}

	@Override
	public QuizCountResponse getQuizCount() {

		QuizCountResponse response = new QuizCountResponse();
		response.setData(response.new Data(quizRepository.countByDeleted(Deleted.N))).setHttpStatus(HttpStatus.OK)
				.setMessage(AdminMessageConstant.QUIZ_COUNTED).setResponseTime(LocalDateTime.now());
		return response;
	}

	private Quiz mapToModel(final Quiz quiz, final QuizRequest quizRequest) {

		final Category category = categoryRepository.findById(quizRequest.getCategoryId())
				.orElseThrow(() -> new CategoryNotExistException(AdminMessageConstant.CATEGORY_NOT_EXIST));

		return quiz.setTitle(quizRequest.getQuizTitle()).setCategory(category);
	}

	private QuizDTO mapToDTO(final Quiz quiz) {
		return new QuizDTO().setQuizId(quiz.getId()).setQuizTitle(quiz.getTitle())
				.setCategoryId(quiz.getCategory().getId());
	}

}

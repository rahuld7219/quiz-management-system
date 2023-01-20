package com.qms.admin.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.qms.admin.constant.AdminMessageConstant;
import com.qms.admin.dto.request.QuestionRequest;
import com.qms.admin.dto.response.ListQuestionResponse;
import com.qms.admin.dto.response.QuestionResponse;
import com.qms.admin.exception.custom.QuestionConstraintViolationException;
import com.qms.admin.exception.custom.QuestionNotExistException;
import com.qms.admin.repository.QuestionRepository;
import com.qms.admin.service.QuestionService;
import com.qms.common.constant.Deleted;
import com.qms.common.dto.QuestionDTO;
import com.qms.common.model.Question;
import com.qms.common.repository.QuizQuestionRepository;

@Service
public class QuestionServiceImpl implements QuestionService {

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private QuizQuestionRepository quizQuestionRepository;

	@Override
	public QuestionResponse addQuestion(final QuestionRequest questionRequest) {

		Question question = questionRepository.save(mapToModel(new Question(), questionRequest));

		QuestionResponse response = new QuestionResponse();
		response.setData(response.new Data(mapToDTO(question))).setHttpStatus(HttpStatus.CREATED)
				.setMessage(AdminMessageConstant.QUESTION_CREATED).setResponseTime(LocalDateTime.now());
		return response;
	}

	@Override
	public QuestionResponse updateQuestion(final Long questionId, final QuestionRequest questionRequest) {

		Question question = questionRepository.findById(questionId)
				.orElseThrow(() -> new QuestionNotExistException(AdminMessageConstant.QUESTION_NOT_EXIST));

		questionRepository.save(mapToModel(question, questionRequest));

		QuestionResponse response = new QuestionResponse();
		response.setData(response.new Data(mapToDTO(question))).setHttpStatus(HttpStatus.OK)
				.setMessage(AdminMessageConstant.QUESTION_UPDATED).setResponseTime(LocalDateTime.now());
		return response;

	}

	@Override
	public void deleteQuestion(final Long questionId) {

		Question question = questionRepository.findByIdAndDeleted(questionId, Deleted.N)
				.orElseThrow(() -> new QuestionNotExistException(AdminMessageConstant.QUESTION_NOT_EXIST));

		// TODO: try and catch databse exception instead of checking here, check CASCADE
		// definition
		if (quizQuestionRepository.existsByQuestionIdAndDeleted(questionId, Deleted.N)) {
			throw new QuestionConstraintViolationException(AdminMessageConstant.QUESTION_QUIZ_ASSOCIATION_VIOLATION);
		}

		question.setDeleted(Deleted.Y);
		questionRepository.save(question);

	}

	@Override
	public QuestionResponse getQuestion(final Long questionId) {
		Question question = questionRepository.findByIdAndDeleted(questionId, Deleted.N)
				.orElseThrow(() -> new QuestionNotExistException(AdminMessageConstant.QUESTION_NOT_EXIST));

		QuestionResponse response = new QuestionResponse();
		response.setData(response.new Data(mapToDTO(question))).setHttpStatus(HttpStatus.OK)
				.setMessage(AdminMessageConstant.QUESTION_GOT).setResponseTime(LocalDateTime.now());
		return response;
	}

	@Override
	public ListQuestionResponse getQuestionList() {

		List<QuestionDTO> questions = questionRepository.findAll().stream().map(this::mapToDTO)
				.collect(Collectors.toCollection(ArrayList::new));

		ListQuestionResponse response = new ListQuestionResponse();
		response.setData(response.new Data(questions)).setHttpStatus(HttpStatus.OK)
				.setMessage(AdminMessageConstant.QUESTION_GOT).setResponseTime(LocalDateTime.now());
		return response;
	}

	private Question mapToModel(final Question question, final QuestionRequest questionRequest) {
		return question.setQuestionDetail(questionRequest.getQuestionDetail()).setOptionA(questionRequest.getOptionA())
				.setOptionB(questionRequest.getOptionB()).setOptionC(questionRequest.getOptionC())
				.setOptionD(questionRequest.getOptionD()).setRightOption(questionRequest.getRightOption())
				.setMarks(questionRequest.getMarks());
	}

	private QuestionDTO mapToDTO(final Question question) {
		return new QuestionDTO().setQuestionId(question.getId()).setQuestionDetail(question.getQuestionDetail())
				.setOptionA(question.getOptionA()).setOptionB(question.getOptionB()).setOptionC(question.getOptionC())
				.setOptionD(question.getOptionD()).setRightOption(question.getRightOption())
				.setMarks(question.getMarks());
	}

}

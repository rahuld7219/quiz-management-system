package com.qms.admin.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qms.admin.dto.QuestionDTO;
import com.qms.admin.model.Question;
import com.qms.admin.repository.QuestionRepository;
import com.qms.admin.repository.QuizQuestionRepository;
import com.qms.admin.repository.QuizRepository;
import com.qms.admin.service.QuestionService;

@Service
public class QuestionServiceImpl implements QuestionService {

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private QuizRepository quizRepository;
	
	@Autowired
	QuizQuestionRepository quizQuestionRepository;

	@Override
	public Long addQuestion(final QuestionDTO questionDTO) {
		return questionRepository.save(mapToModel(new Question(), questionDTO)).getId();
	}

	@Override
	public void updateQuestion(final String questionId, final QuestionDTO questionDTO) {
		// check whether question exist
		// update question

		Question question = questionRepository.findById(Long.valueOf(questionId))
				.orElseThrow(() -> new RuntimeException("Question not exist.")); // TODO: create custom exception

		questionRepository.save(mapToModel(question, questionDTO));

	}

	@Override
	public void deleteQuestion(final String questionId) {
		// find Quiz by question Id -> if exist then cannot delete the Question
		// else soft delete the Question
		if (quizQuestionRepository.existsByQuestionId(Long.valueOf(questionId))) { //TODO: also check id deleted is "N" //TODO: use quizService, check everywhere
			throw new RuntimeException("Cannot delete the question, it has quiz association."); // TODO: throw custom
																								// exception
		}

		Question question = questionRepository.findById(Long.valueOf(questionId))
				.orElseThrow(() -> new RuntimeException("Question not exist.")); // TODO: throw custom exception

		question.setDeleted("Y"); // TODO: use enum
		questionRepository.save(question);

	}

	@Override
	public QuestionDTO getQuestion(final String questionId) {
		Question question = questionRepository.findById(Long.valueOf(questionId))
				.orElseThrow(() -> new RuntimeException("Question not exist.")); // TODO: throw custom exception

		return mapToDTO(question);
	}

	@Override
	public List<QuestionDTO> getQuestionList() {
		return questionRepository.findAll().stream().map(this::mapToDTO)
				.collect(Collectors.toCollection(ArrayList::new));
	}

	private Question mapToModel(final Question question, final QuestionDTO questionDTO) {
		return question.setQuestionDetail(questionDTO.getQuestionDetail()).setOptionA(questionDTO.getOptionA())
				.setOptionB(questionDTO.getOptionB()).setOptionC(questionDTO.getOptionC())
				.setOptionD(questionDTO.getOptionD()).setRightOption(questionDTO.getRightOption())
				.setMarks(questionDTO.getMarks());
	}

	private QuestionDTO mapToDTO(final Question question) {
		return new QuestionDTO().setQuestionDetail(question.getQuestionDetail()).setOptionA(question.getOptionA())
				.setOptionB(question.getOptionB()).setOptionC(question.getOptionC()).setOptionD(question.getOptionD())
				.setRightOption(question.getRightOption()).setMarks(question.getMarks());
	}

}

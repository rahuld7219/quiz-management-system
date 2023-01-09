package com.qms.admin.service;

import java.util.List;

import com.qms.admin.dto.QuestionDTO;

public interface QuestionService {
//	Get questions
//	Add questions
//	Update Questions
//	Delete Questions

	// List Questions

	// Link question to quiz

	Long addQuestion(final QuestionDTO questionDTO);

	void updateQuestion(final String questionId, final QuestionDTO questionDTO);

	void deleteQuestion(final String questionId);

	QuestionDTO getQuestion(final String questionId);

	List<QuestionDTO> getQuestionList();

}

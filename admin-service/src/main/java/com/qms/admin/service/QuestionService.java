package com.qms.admin.service;

import com.qms.admin.dto.request.QuestionRequest;
import com.qms.admin.dto.response.ListQuestionResponse;
import com.qms.admin.dto.response.QuestionResponse;

public interface QuestionService {

	QuestionResponse addQuestion(final QuestionRequest questionRequest);

	QuestionResponse updateQuestion(final Long questionId, final QuestionRequest questionRequest);

	void deleteQuestion(final Long questionId);

	QuestionResponse getQuestion(final Long questionId);

	ListQuestionResponse getQuestionList();

}

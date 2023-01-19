package com.qms.admin.service;

import com.qms.admin.dto.request.QuizRequest;
import com.qms.admin.dto.response.ListQuizResponse;
import com.qms.admin.dto.response.QuizCountResponse;
import com.qms.admin.dto.response.QuizResponse;

public interface QuizService {

	QuizResponse addQuiz(final QuizRequest quizRequest);

	QuizResponse updateQuiz(final Long quizId, final QuizRequest quizRequest);

	void deleteQuiz(final Long quizId);

	QuizResponse getQuiz(final Long quizId);

	ListQuizResponse getQuizList();

	QuizCountResponse getQuizCount();
}

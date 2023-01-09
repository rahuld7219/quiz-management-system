package com.qms.admin.service;

import java.util.List;

import com.qms.admin.dto.request.QuizDTO;

public interface QuizService {
//	Get Quiz
//	Add Quiz
//	Update Quiz
//	Delete Quiz
//	List Quiz

	Long addQuiz(final QuizDTO quizDTO);

	void updateQuiz(final String quizId, final QuizDTO quizDTO);

	void deleteQuiz(final String quizId);

	QuizDTO getQuiz(final String quizId);

	List<QuizDTO> getQuizList();
}

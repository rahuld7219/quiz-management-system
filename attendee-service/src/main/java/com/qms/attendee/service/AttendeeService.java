package com.qms.attendee.service;

import javax.servlet.http.HttpServletResponse;

import com.qms.attendee.dto.request.QuizSubmission;
import com.qms.attendee.dto.response.CountAttendedQuizByCategoryResponse;
import com.qms.attendee.dto.response.CountAttendedQuizResponse;
import com.qms.attendee.dto.response.CountQuizByCategoryResponse;
import com.qms.attendee.dto.response.DashboardResponse;
import com.qms.attendee.dto.response.GetQuizQuestionsReponse;
import com.qms.attendee.dto.response.LeaderboardResponse;
import com.qms.attendee.dto.response.ShowResultResponse;

public interface AttendeeService {

	CountAttendedQuizResponse countAttendedQuiz();

	CountQuizByCategoryResponse countQuizByCategory();

	CountAttendedQuizByCategoryResponse countAttendedQuizByCategory();

	GetQuizQuestionsReponse getQuizQuestions(final Long quizId);

	DashboardResponse dashboard();

	LeaderboardResponse leaderboard(final Long quizId);

	void submitQuiz(final QuizSubmission quizSubmission);

	ShowResultResponse showResult(final Long quizId);

	Object exportPDF(final Long quizId, final HttpServletResponse response);

}

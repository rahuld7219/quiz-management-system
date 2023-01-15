package com.qms.attendee.service;

import java.util.List;
import java.util.Map;

import com.qms.attendee.dto.QuizQuestionDTO;
import com.qms.attendee.dto.QuizResult;
import com.qms.attendee.dto.request.QuizSubmission;
import com.qms.attendee.dto.response.Dashboard;
import com.qms.attendee.dto.response.Leaderboard;

public interface AttendeeService {

	Long countAttendedQuiz();

	List<Map<String, Object>> countQuizByCategory();

	List<Map<String, Object>> countAttendedQuizByCategory();

	List<QuizQuestionDTO> getQuizQuestions(final String quizId);

	Dashboard dashboard();

	void submitQuiz(final QuizSubmission quizSubmission);

	QuizResult showResult(final String quizId);

	Leaderboard leaderboard(final String quizId);

}

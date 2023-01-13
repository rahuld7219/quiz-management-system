package com.qms.attendee.service;

import java.util.List;
import java.util.Map;

import com.qms.attendee.dto.Dashboard;
import com.qms.attendee.dto.QuizQuestionDTO;
import com.qms.attendee.dto.QuizResult;
import com.qms.attendee.dto.QuizSubmission;

public interface AttendeeService {

	Long countAttendedQuiz();

	List<Map<String, Object>> countQuizByCategory();

	List<Map<String, Object>> countAttendedQuizByCategory();

	List<QuizQuestionDTO> getQuizQuestions(final String quizId);

	Dashboard dashboard();

	void submitQuiz(final QuizSubmission quizSubmission);

	QuizResult showResult(String quizId);

}

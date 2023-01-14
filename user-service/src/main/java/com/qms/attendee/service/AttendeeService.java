package com.qms.attendee.service;

import java.util.List;
import java.util.Map;

import com.qms.attendee.dto.QuizQuestionDTO;

public interface AttendeeService {

	Long countAttendedQuiz();

	List<Map<String, Object>> countQuizByCategory();

	List<Map<String, Object>> countAttendedQuizByCategory();

	List<QuizQuestionDTO> getQuizQuestions(String quizId);

}

package com.qms.admin.service;

import java.util.List;
import java.util.Map;

import com.qms.admin.dto.Dashboard;
import com.qms.admin.dto.Leaderboard;
import com.qms.admin.dto.request.LinkQuizQuestion;

public interface AdminService {

	void linkQuestionToQuiz(final LinkQuizQuestion linkQuizQuestion);

	Long countAttendess();

	Long countAttendeesAttemptedQuiz();

	List<Map<String, Object>> countTopFiveQuizWithAttendee();

	Dashboard dashboard();

	Leaderboard leaderboard(final String quizId);

}

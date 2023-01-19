package com.qms.admin.service;

import java.util.List;
import java.util.Map;

import com.qms.admin.dto.Dashboard;
import com.qms.admin.dto.Leaderboard;
import com.qms.admin.dto.request.LinkQuizQuestionRequest;
import com.qms.admin.dto.response.CountAttendeesAttemptedQuizResponse;
import com.qms.admin.dto.response.CountAttendeesResponse;
import com.qms.admin.dto.response.CountTopFiveQuizWithAttendeeResponse;
import com.qms.admin.dto.response.DashboardResponse;
import com.qms.admin.dto.response.LeaderboardResponse;
import com.qms.admin.dto.response.LinkQuizQuestionResponse;

public interface AdminService {

	LinkQuizQuestionResponse linkQuestionToQuiz(final LinkQuizQuestionRequest linkQuizQuestion);

	CountAttendeesResponse countAttendees();

	CountAttendeesAttemptedQuizResponse countAttendeesAttemptedQuiz();

	CountTopFiveQuizWithAttendeeResponse countTopFiveQuizWithAttendee();

	DashboardResponse dashboard();

	LeaderboardResponse leaderboard(final String quizId);

}

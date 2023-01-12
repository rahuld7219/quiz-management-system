package com.qms.admin.service;

import java.util.List;
import java.util.Map;

import com.qms.admin.dto.Dashboard;
import com.qms.admin.dto.LinkQuizQuestionDTO;

public interface AdminService {

	void linkQuestionToQuiz(final LinkQuizQuestionDTO linkQuizQuestionDTO);

	Long countAttendess();

	Long countAttendeesAttemptedQuiz();

	List<Map<String, Object>> countTopFiveQuizWithAttendee();

	Dashboard dashboard();

}

package com.qms.admin.service;

import com.qms.admin.dto.LinkQuizQuestionDTO;

public interface AdminService {

	void linkQuestionToQuiz(final LinkQuizQuestionDTO linkQuizQuestionDTO);

	Long countAttendess();

}

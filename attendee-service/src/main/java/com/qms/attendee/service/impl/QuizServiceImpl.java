package com.qms.attendee.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.qms.attendee.service.QuizService;
import com.qms.common.constant.CommonMessageConstant;
import com.qms.common.constant.Deleted;
import com.qms.common.dto.response.QuizCountResponse;
import com.qms.common.repository.QuizRepository;

@Service
public class QuizServiceImpl implements QuizService {

	@Autowired
	private QuizRepository quizRepository;

	@Override
	public QuizCountResponse getQuizCount() {

		QuizCountResponse response = new QuizCountResponse();
		response.setData(response.new Data(quizRepository.countByDeleted(Deleted.N))).setHttpStatus(HttpStatus.OK)
				.setMessage(CommonMessageConstant.QUIZ_COUNTED).setResponseTime(LocalDateTime.now());
		return response;
	}
}

package com.qms.attendee.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qms.attendee.service.QuizService;
import com.qms.common.repository.QuizRepository;

@Service
public class QuizServiceImpl implements QuizService {

	@Autowired
	private QuizRepository quizRepository;

	@Override
	public Long getQuizCount() {
		return quizRepository.countByDeleted("N");
	}
}

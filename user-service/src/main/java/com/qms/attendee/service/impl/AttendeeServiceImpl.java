package com.qms.attendee.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qms.attendee.dto.QuizQuestionDTO;
import com.qms.attendee.model.Question;
import com.qms.attendee.repository.QuizQuestionRepository;
import com.qms.attendee.repository.QuizRepository;
import com.qms.attendee.repository.ScoreRepository;
import com.qms.attendee.service.AttendeeService;

@Service
public class AttendeeServiceImpl implements AttendeeService {

	@Autowired
	private ScoreRepository scoreRepository;

	@Autowired
	private QuizRepository quizRepository;

	@Autowired
	private QuizQuestionRepository quizQuestionRepository;

	@Override
	public Long countAttendedQuiz() {
//		String email = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
//				.getUsername();
//		Long userId = userRepository.getIdByEmail(email);

		return scoreRepository.countDistinctQuizIdByUserId(2L); // TODO: pass userId extracted from spring security
																// context
	}

	@Override
	public List<Map<String, Object>> countQuizByCategory() {
		return quizRepository.countQuizByCategory();
	}

	@Override
	public List<Map<String, Object>> countAttendedQuizByCategory() {
		return quizRepository.countAttendedQuizByCategory();
	}

	@Override
	public List<QuizQuestionDTO> getQuizQuestions(String quizId) {

		List<Question> questions = quizQuestionRepository.getQuestionsByQuizId(quizId);

		return questions.stream().map(this::mapToDTO).collect(Collectors.toList());
		
		// TODO: if above not work then can also implement by query or by first get question id from quizQuestion repo then get question from question repo 
	}

	private QuizQuestionDTO mapToDTO(Question question) {
		return new QuizQuestionDTO()
				.setQuestionDetail(question.getQuestionDetail())
				.setOptionA(question.getOptionA())
				.setOptionB(question.getOptionB())
				.setOptionC(question.getOptionC())
				.setOptionD(question.getOptionD());
	}
}

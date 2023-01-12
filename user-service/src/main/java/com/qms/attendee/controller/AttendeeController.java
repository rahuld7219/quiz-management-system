package com.qms.attendee.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qms.attendee.dto.QuizQuestionDTO;
import com.qms.attendee.service.AttendeeService;
import com.qms.attendee.service.QuizService;

@RestController
@RequestMapping("/api/v1/attendee")
public class AttendeeController {

	@Autowired
	private AttendeeService attendeeService;

	@Autowired
	private QuizService quizService;

	/**
	 * count all quizzes (not deleted)
	 * 
	 * @return
	 */
	@GetMapping("/countQuiz") // TODO: duplicate
	public ResponseEntity<Long> getQuizCount() {
		return ResponseEntity.ok(quizService.getQuizCount());
	}

	/**
	 * count attended unique quizzes by current user
	 * 
	 * @return
	 */
	@GetMapping("/countAttendedQuiz")
	public ResponseEntity<Long> countAttendedQuiz() {
		return ResponseEntity.ok(attendeeService.countAttendedQuiz());
	}

	@GetMapping("/countQuizByCategory")
	public ResponseEntity<List<Map<String, Object>>> countQuizByCategory() {
		return ResponseEntity.ok(attendeeService.countQuizByCategory());
	}

	@GetMapping("/countAttendedQuizByCategory")
	public ResponseEntity<List<Map<String, Object>>> countAttendedQuizByCategory() {
		return ResponseEntity.ok(attendeeService.countAttendedQuizByCategory());
	}

	@GetMapping("/quizQuestions/{quizId}")
	public ResponseEntity<List<QuizQuestionDTO>> getQuizQuestions(@PathVariable final String quizId) {
		return ResponseEntity.ok(attendeeService.getQuizQuestions(quizId));
	}
}
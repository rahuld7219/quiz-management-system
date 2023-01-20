package com.qms.attendee.controller;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qms.attendee.constant.AttendeeMessageConstant;
import com.qms.attendee.constant.AttendeeURIConstant;
import com.qms.attendee.dto.request.QuizSubmission;
import com.qms.attendee.service.AttendeeService;
import com.qms.attendee.service.QuizService;
import com.qms.common.dto.response.ApiResponse;

@RestController
@RequestMapping(AttendeeURIConstant.BASE_ATTENDEE_URL)
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
	public ResponseEntity<ApiResponse> getQuizCount() {
		return ResponseEntity.ok(quizService.getQuizCount());
	}

	/**
	 * count attended unique quizzes by current user
	 * 
	 * @return
	 */
	@GetMapping("/countAttendedQuiz")
	public ResponseEntity<ApiResponse> countAttendedQuiz() {
		return ResponseEntity.ok(attendeeService.countAttendedQuiz());
	}

	@GetMapping("/countQuizByCategory")
	public ResponseEntity<ApiResponse> countQuizByCategory() {
		return ResponseEntity.ok(attendeeService.countQuizByCategory());
	}

	@GetMapping("/countAttendedQuizByCategory")
	public ResponseEntity<ApiResponse> countAttendedQuizByCategory() {
		return ResponseEntity.ok(attendeeService.countAttendedQuizByCategory());
	}

	@GetMapping("/quizQuestions/{quizId}")
	public ResponseEntity<ApiResponse> getQuizQuestions(@PathVariable final Long quizId) {
		return ResponseEntity.ok(attendeeService.getQuizQuestions(quizId));
	}

	@GetMapping("/dashboard")
	public ResponseEntity<ApiResponse> dashboard() {
		return ResponseEntity.ok(attendeeService.dashboard());
	}

	@GetMapping("/leaderboard/{quizId}")
	public ResponseEntity<ApiResponse> leaderboard(@PathVariable final Long quizId) {
		return ResponseEntity.ok(attendeeService.leaderboard(quizId));
	}

	@PostMapping("/submitQuiz")
	public ResponseEntity<ApiResponse> submitQuiz(@RequestBody final QuizSubmission quizSubmission) {
		attendeeService.submitQuiz(quizSubmission);
		return ResponseEntity.ok(new ApiResponse().setHttpStatus(HttpStatus.OK)
				.setMessage(AttendeeMessageConstant.QUIZ_SUBMITTED).setResponseTime(LocalDateTime.now()));
	}

	@GetMapping("/showResult/{quizId}")
	public ResponseEntity<ApiResponse> showResult(@PathVariable final Long quizId) {
		return ResponseEntity.ok(attendeeService.showResult(quizId));
	}

	@GetMapping("/exportPDF/{quizId}")
	public ResponseEntity<ApiResponse> exportPDF(@PathVariable final Long quizId, final HttpServletResponse response) {
		attendeeService.exportPDF(quizId, response);
		return ResponseEntity.ok(new ApiResponse().setHttpStatus(HttpStatus.OK)
				.setMessage(AttendeeMessageConstant.PDF_EXPORTED).setResponseTime(LocalDateTime.now()));
	}
}

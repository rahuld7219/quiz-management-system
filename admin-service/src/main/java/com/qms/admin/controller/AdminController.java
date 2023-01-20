package com.qms.admin.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qms.admin.constant.AdminURIConstant;
import com.qms.admin.dto.request.LinkQuizQuestionRequest;
import com.qms.admin.service.AdminService;
import com.qms.common.dto.response.ApiResponse;

/**
 * 
 * @author Rahul Dubey
 *
 */
@RestController
@RequestMapping(AdminURIConstant.BASE_ADMIN_URL)
public class AdminController {

	@Autowired
	private AdminService adminService;

	/**
	 * 
	 * Link one or more question(s) to a quiz.
	 * 
	 */
	@PostMapping(AdminURIConstant.LINK_QUIZ_QUESTION)
	public ResponseEntity<ApiResponse> linkQuestionToQuiz(
			@Valid @RequestBody final LinkQuizQuestionRequest linkQuizQuestion) {
		ApiResponse response = adminService.linkQuestionToQuiz(linkQuizQuestion);
		return new ResponseEntity<>(response, response.getHttpStatus());
	}

	/**
	 * 
	 * Count all attendees(student) in the system.
	 * 
	 */
	@GetMapping(AdminURIConstant.COUNT_ATTENDEE)
	public ResponseEntity<ApiResponse> countAttendees() {
		return ResponseEntity.ok(adminService.countAttendees());
	}

	/**
	 * 
	 * Count all attendees who attended one or more quiz
	 * 
	 */
	@GetMapping(AdminURIConstant.COUNT_ATTENDEE_ATTEMPTED_QUIZ)
	public ResponseEntity<ApiResponse> countAttendeesAttemptedQuiz() {
		return ResponseEntity.ok(adminService.countAttendeesAttemptedQuiz());
	}

	/**
	 * Count top 5 five quiz w.r.t. number of attendees
	 * 
	 */
	@GetMapping(AdminURIConstant.TOP_5_QUIZ)
	public ResponseEntity<ApiResponse> countTopFiveQuizWithAttendee() {
		return ResponseEntity.ok(adminService.countTopFiveQuizWithAttendee());
	}

	/**
	 * Dashboard contains total of quizzes, total attendees and top 5 quizzes
	 * 
	 */
	@GetMapping(AdminURIConstant.DASHBOARD)
	public ResponseEntity<ApiResponse> dashboard() {
		return ResponseEntity.ok(adminService.dashboard());
	}

	/**
	 * Leaderboard lists attendees of a given quiz sorted according to their rank,
	 * w.r.t. score in descending order
	 * 
	 */
	@GetMapping(AdminURIConstant.LEADERBOARD + "/{quizId}")
	public ResponseEntity<ApiResponse> leaderboard(@PathVariable final Long quizId) {
		return ResponseEntity.ok(adminService.leaderboard(quizId));
	}
}

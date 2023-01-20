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

@RestController
@RequestMapping(AdminURIConstant.BASE_ADMIN_URL)
public class AdminController {

	@Autowired
	private AdminService adminService;

	/**
	 * 
	 * @return
	 */
	@PostMapping(AdminURIConstant.LINK_QUIZ_QUESTION)
	public ResponseEntity<ApiResponse> linkQuestionToQuiz(
			@Valid @RequestBody final LinkQuizQuestionRequest linkQuizQuestion) {
		ApiResponse response = adminService.linkQuestionToQuiz(linkQuizQuestion);
		return new ResponseEntity<>(response, response.getHttpStatus());
	}

	@GetMapping(AdminURIConstant.COUNT_ATTENDEE)
	public ResponseEntity<ApiResponse> countAttendees() {
		return ResponseEntity.ok(adminService.countAttendees());
	}

	@GetMapping(AdminURIConstant.COUNT_ATTENDEE_ATTEMPTED_QUIZ)
	public ResponseEntity<ApiResponse> countAttendeesAttemptedQuiz() {
		return ResponseEntity.ok(adminService.countAttendeesAttemptedQuiz());
	}

	@GetMapping(AdminURIConstant.TOP_5_QUIZ)
	public ResponseEntity<ApiResponse> countTopFiveQuizWithAttendee() {
		return ResponseEntity.ok(adminService.countTopFiveQuizWithAttendee());
	}

	@GetMapping(AdminURIConstant.DASHBOARD)
	public ResponseEntity<ApiResponse> dashboard() {
		return ResponseEntity.ok(adminService.dashboard());
	}

	@GetMapping(AdminURIConstant.LEADERBOARD + "/{quizId}")
	public ResponseEntity<ApiResponse> leaderboard(@PathVariable final Long quizId) {
		return ResponseEntity.ok(adminService.leaderboard(quizId));
	}

//	@GetMapping("/leaderboard")
//	public ResponseEntity<List<Map<String, Object>>> leaderboard() {
//		return ResponseEntity.ok(adminService.leaderboard());
//	}

}

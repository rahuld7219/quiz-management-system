package com.qms.admin.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qms.admin.constant.AdminMessageConstant;
import com.qms.admin.constant.AdminURIConstant;
import com.qms.admin.dto.Dashboard;
import com.qms.admin.dto.Leaderboard;
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
	public ResponseEntity<ApiResponse> linkQuestionToQuiz(@Valid @RequestBody final LinkQuizQuestionRequest linkQuizQuestion) { // TODO:
																															// link
																															// multiple
																															// question
																															// to
																															// quiz
		adminService.linkQuestionToQuiz(linkQuizQuestion);
		return ResponseEntity.ok(
				new ApiResponse().setHttpStatus(HttpStatus.CREATED).setMessage(AdminMessageConstant.QUESTION_ADDED));
	}

	@GetMapping(AdminURIConstant.COUNT_ATTENDEE)
	public ResponseEntity<Long> countAttendees() {
		return ResponseEntity.ok(adminService.countAttendess());
	}

	@GetMapping(AdminURIConstant.COUNT_ATTENDEE_ATTEMPTED_QUIZ)
	public ResponseEntity<Long> countAttendeesAttemptedQuiz() {
		return ResponseEntity.ok(adminService.countAttendeesAttemptedQuiz());
	}

	@GetMapping(AdminURIConstant.TOP_5_QUIZ)
	public ResponseEntity<List<Map<String, Object>>> countTopFiveQuizWithAttendee() {
		return ResponseEntity.ok(adminService.countTopFiveQuizWithAttendee());
	}

	@GetMapping("/dashboard")
	public ResponseEntity<Dashboard> dashboard() {
		return ResponseEntity.ok(adminService.dashboard());
	}

//	@GetMapping("/leaderboard")
//	public ResponseEntity<List<Map<String, Object>>> leaderboard() {
//		return ResponseEntity.ok(adminService.leaderboard());
//	}

	@GetMapping("/leaderboard/{quizId}")
	public ResponseEntity<Leaderboard> leaderboard(@PathVariable final String quizId) {
		return ResponseEntity.ok(adminService.leaderboard(quizId));
	}
}

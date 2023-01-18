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

import com.qms.admin.constant.MessageConstant;
import com.qms.admin.constant.URIConstant;
import com.qms.admin.dto.Dashboard;
import com.qms.admin.dto.Leaderboard;
import com.qms.admin.dto.request.LinkQuizQuestion;
import com.qms.admin.dto.response.ApiResponse;
import com.qms.admin.service.AdminService;

@RestController
@RequestMapping(URIConstant.BASE_ADMIN_URL)
public class AdminController {

	@Autowired
	private AdminService adminService;

	/**
	 * 
	 * @return
	 */
	@PostMapping(URIConstant.LINK_QUIZ_QUESTION)
	public ResponseEntity<ApiResponse> linkQuestionToQuiz(
			@Valid @RequestBody final LinkQuizQuestion linkQuizQuestion) { // TODO: link multiple question to quiz
		adminService.linkQuestionToQuiz(linkQuizQuestion);
		return ResponseEntity.ok(new ApiResponse().setHttpStatus(HttpStatus.CREATED).setMessage(MessageConstant.QUESTION_ADDED));
	}

	@GetMapping("/countAttendees")
	public ResponseEntity<Long> countAttendees() {
		return ResponseEntity.ok(adminService.countAttendess());
	}

	@GetMapping("/countAttendeesAttemptedQuiz")
	public ResponseEntity<Long> countAttendeesAttemptedQuiz() {
		return ResponseEntity.ok(adminService.countAttendeesAttemptedQuiz());
	}

	@GetMapping("/topFiveQuiz")
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

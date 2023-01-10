package com.qms.admin.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qms.admin.dto.LinkQuizQuestionDTO;
import com.qms.admin.service.AdminService;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

	/**
	 * 
	 * @return
	 */
	@PostMapping("/linkQuizQuestion")
	public ResponseEntity<String> linkQuestionToQuiz(
			@Valid @RequestBody final LinkQuizQuestionDTO linkQuizQuestionDTO) { // TODO: link multiple question to quiz
		adminService.linkQuestionToQuiz(linkQuizQuestionDTO);
		return ResponseEntity.ok("Question added successfully to the quiz");
	}
	
	@GetMapping("/countAttendees")
	public ResponseEntity<Long> countAttendees() {
		return ResponseEntity.ok(adminService.countAttendess());
	}
}

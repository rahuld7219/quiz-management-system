package com.qms.admin.controller;

import java.net.URI;
import java.time.LocalDateTime;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qms.admin.constant.AdminMessageConstant;
import com.qms.admin.constant.AdminURIConstant;
import com.qms.admin.dto.request.QuizRequest;
import com.qms.admin.dto.response.QuizResponse;
import com.qms.admin.service.QuizService;
import com.qms.common.dto.response.ApiResponse;

@RestController
@RequestMapping(AdminURIConstant.BASE_ADMIN_URL + AdminURIConstant.QUIZ_URL)
public class QuizController {

	@Autowired
	private QuizService quizService;

	@PostMapping()
	public ResponseEntity<ApiResponse> addQuiz(@Valid @RequestBody final QuizRequest quizRequest) {

		QuizResponse response = quizService.addQuiz(quizRequest);
		URI location = URI.create(AdminURIConstant.BASE_ADMIN_URL + AdminURIConstant.QUIZ_URL + "/"
				+ response.getData().getQuiz().getQuizId());

		return ResponseEntity.created(location).body(response);
	}

	@PutMapping("/{quizId}")
	public ResponseEntity<ApiResponse> updateQuiz(@Valid @PathVariable final Long quizId,
		@Valid	@RequestBody final QuizRequest quizRequest) {

		return ResponseEntity.ok(quizService.updateQuiz(quizId, quizRequest));
	}

	@DeleteMapping("/{quizId}")
	public ResponseEntity<ApiResponse> deleteQuiz(@PathVariable final Long quizId) {
		quizService.deleteQuiz(quizId);

		return ResponseEntity.ok(new ApiResponse().setHttpStatus(HttpStatus.OK)
				.setMessage(AdminMessageConstant.QUIZ_DELETED).setResponseTime(LocalDateTime.now()));
	}

	@GetMapping("/{quizId}")
	public ResponseEntity<ApiResponse> getQuiz(@PathVariable final Long quizId) {

		return ResponseEntity.ok(quizService.getQuiz(quizId));
	}

	@GetMapping()
	public ResponseEntity<ApiResponse> getQuizList() {
		return ResponseEntity.ok(quizService.getQuizList());
	}

	@GetMapping("/count")
	public ResponseEntity<ApiResponse> getQuizCount() {
		return ResponseEntity.ok(quizService.getQuizCount());
	}
}
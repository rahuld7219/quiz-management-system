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
import com.qms.admin.dto.request.QuestionRequest;
import com.qms.admin.dto.response.QuestionResponse;
import com.qms.admin.service.QuestionService;
import com.qms.common.dto.response.ApiResponse;

@RestController
@RequestMapping(AdminURIConstant.BASE_ADMIN_URL + AdminURIConstant.QUESTION_URL)
public class QuestionController {

	@Autowired
	private QuestionService questionService;

	@PostMapping
	public ResponseEntity<ApiResponse> addQuestion(@Valid @RequestBody QuestionRequest questionRequest) {

		QuestionResponse response = questionService.addQuestion(questionRequest);
		URI location = URI.create(AdminURIConstant.BASE_ADMIN_URL + AdminURIConstant.QUESTION_URL + "/"
				+ response.getData().getQuestion().getQuestionId());

		return ResponseEntity.created(location).body(response);
	}

	@PutMapping("/{questionId}")
	ResponseEntity<ApiResponse> updateQuestion(@Valid @PathVariable Long questionId,
			@RequestBody QuestionRequest questionRequest) {

		return ResponseEntity.ok(questionService.updateQuestion(questionId, questionRequest));
	}

	@DeleteMapping("/{questionId}")
	ResponseEntity<ApiResponse> deleteQuestion(@PathVariable Long questionId) {
		questionService.deleteQuestion(questionId);
		return ResponseEntity.ok(new ApiResponse().setHttpStatus(HttpStatus.OK)
				.setMessage(AdminMessageConstant.QUESTION_DELETED).setResponseTime(LocalDateTime.now()));
	}

	@GetMapping("/{questionId}")
	ResponseEntity<ApiResponse> getQuestion(@PathVariable Long questionId) {
		return ResponseEntity.ok(questionService.getQuestion(questionId));
	}

	@GetMapping()
	ResponseEntity<ApiResponse> getQuestionList() {
		return ResponseEntity.ok(questionService.getQuestionList());
	}

}

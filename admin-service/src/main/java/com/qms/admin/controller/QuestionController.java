package com.qms.admin.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qms.admin.service.QuestionService;
import com.qms.common.dto.QuestionDTO;

@RestController
@RequestMapping("/api/v1/admin/question")
public class QuestionController {

	@Autowired
	private QuestionService questionService;

	@PostMapping
	public ResponseEntity<String> addQuestion(@Valid @RequestBody QuestionDTO questionDTO) {
		Long id = questionService.addQuestion(questionDTO);
		URI location = URI.create("/api/v1/admin/question/" + id); // TODO: use uuid
		return ResponseEntity.created(location).body("Question created successfully");
	}

	@PutMapping("/{questionId}")
	ResponseEntity<String> updateQuestion(@Valid @PathVariable String questionId,
			@RequestBody QuestionDTO questionDTO) {
		questionService.updateQuestion(questionId, questionDTO);
		return ResponseEntity.ok("Question updated successfully.");
	}

	@DeleteMapping("/{questionId}")
	ResponseEntity<String> deleteQuestion(@PathVariable String questionId) {
		questionService.deleteQuestion(questionId);
		return ResponseEntity.ok("Question deleted successfully");
	}

	@GetMapping("/{questionId}")
	ResponseEntity<QuestionDTO> getQuestion(@PathVariable String questionId) {
		return ResponseEntity.ok(questionService.getQuestion(questionId));
	}

	@GetMapping()
	ResponseEntity<List<QuestionDTO>> getQuestionList() {
		return ResponseEntity.ok(questionService.getQuestionList());
	}
	
}

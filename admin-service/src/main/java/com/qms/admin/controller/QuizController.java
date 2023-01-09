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

import com.qms.admin.dto.request.QuizDTO;
import com.qms.admin.service.QuizService;

@RestController
@RequestMapping("/api/v1/admin")
public class QuizController {

	@Autowired
	private QuizService quizService;

	@PostMapping("/quiz")
	public ResponseEntity<String> addQuiz(@Valid @RequestBody final QuizDTO quizDTO) {
		Long id = quizService.addQuiz(quizDTO);
//		URI location = new URI("/category/" + id);
		URI location = URI.create("/api/v1/admin/quiz/" + id);
		return ResponseEntity.created(location).body("Quiz added successfully.");
	}

	@PutMapping("/quiz/{quizId}")
	public ResponseEntity<String> updateQuiz(@Valid @PathVariable final String quizId,
			@RequestBody final QuizDTO quizDTO) {
		quizService.updateQuiz(quizId, quizDTO);
		return ResponseEntity.ok("Quiz updated successfully.");
	}

	@DeleteMapping("/quiz/{quizId}")
	public ResponseEntity<String> deleteQuiz(@PathVariable final String quizId) {
		quizService.deleteQuiz(quizId);
		return ResponseEntity.ok("Quiz delete successfully.");
	}

	@GetMapping("/quiz/{quizId}")
	public ResponseEntity<QuizDTO> getQuiz(@PathVariable final String quizId) {
		return ResponseEntity.ok(quizService.getQuiz(quizId));
	}

	@GetMapping("/quiz")
	public ResponseEntity<List<QuizDTO>> getQuiz() {
		return ResponseEntity.ok(quizService.getQuizList());
	}
}
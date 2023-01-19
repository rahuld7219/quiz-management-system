package com.qms.admin.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.qms.admin.exception.custom.CategoryConstraintViolationException;
import com.qms.admin.exception.custom.CategoryNotExistException;
import com.qms.admin.exception.custom.QuestionConstraintViolationException;
import com.qms.admin.exception.custom.QuizConstraintViolationException;
import com.qms.common.dto.response.ErrorResponse;
import com.qms.common.exception.custom.QuizNotExistException;

@RestControllerAdvice
public class AdminRestExceptionHandler {

//	@ExceptionHandler(QuizNotExistException.class)
//	public ResponseEntity<ErrorResponse> handleQuizNotExistException(final QuizNotExistException exception) {
//		exception.printStackTrace();
//		final ErrorResponse errorResponse = new ErrorResponse();
//		errorResponse.setHttpStatus(HttpStatus.NOT_FOUND);
//		errorResponse.setException(exception.getClass().getSimpleName());
//		errorResponse.setMessage(exception.getMessage());
//		errorResponse.setResponseTime(LocalDateTime.now());
//		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
//	}

	@ExceptionHandler(CategoryNotExistException.class)
	public ResponseEntity<ErrorResponse> handleCategoryNotExistException(final CategoryNotExistException exception) {
		exception.printStackTrace();
		final ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setHttpStatus(HttpStatus.NOT_FOUND);
		errorResponse.setException(exception.getClass().getSimpleName());
		errorResponse.setMessage(exception.getMessage());
		errorResponse.setResponseTime(LocalDateTime.now());
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(CategoryConstraintViolationException.class)
	public ResponseEntity<ErrorResponse> handleCategoryConstraintViolation(
			final CategoryConstraintViolationException exception) {
		exception.printStackTrace();
		final ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		errorResponse.setException(exception.getClass().getSimpleName());
		errorResponse.setMessage(exception.getMessage());
		errorResponse.setResponseTime(LocalDateTime.now());
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(QuestionConstraintViolationException.class)
	public ResponseEntity<ErrorResponse> handleQuestionConstraintViolation(
			final QuestionConstraintViolationException exception) {
		exception.printStackTrace();
		final ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		errorResponse.setException(exception.getClass().getSimpleName());
		errorResponse.setMessage(exception.getMessage());
		errorResponse.setResponseTime(LocalDateTime.now());
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(QuizConstraintViolationException.class)
	public ResponseEntity<ErrorResponse> handleQuizConstraintViolation(
			final QuizConstraintViolationException exception) {
		exception.printStackTrace();
		final ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		errorResponse.setException(exception.getClass().getSimpleName());
		errorResponse.setMessage(exception.getMessage());
		errorResponse.setResponseTime(LocalDateTime.now());
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

package com.qms.auth.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.qms.auth.dto.response.ErrorResponse;
import com.qms.auth.dto.response.FieldError;
import com.qms.auth.exception.custom.RoleNotFoundException;
import com.qms.auth.exception.custom.UserAlreadyExistException;

@RestControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(final MethodArgumentNotValidException exception) {
		final BindingResult bindingResult = exception.getBindingResult();
		final List<FieldError> fieldErrors = bindingResult.getFieldErrors().stream().map(error -> {
			final FieldError fieldError = new FieldError();
			fieldError.setErrorCode(error.getCode());
			fieldError.setField(error.getField());
			return fieldError;
		}).collect(Collectors.toList());
		final ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
		errorResponse.setException(exception.getClass().getSimpleName());
		errorResponse.setFieldErrors(fieldErrors);
		errorResponse.setMessage(exception.getMessage());
		errorResponse.setResponseTime(LocalDateTime.now());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Throwable.class)
	public ResponseEntity<ErrorResponse> handleThrowable(final Throwable exception) {
		exception.printStackTrace();
		final ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		errorResponse.setException(exception.getClass().getSimpleName());
		errorResponse.setMessage(exception.getMessage());
		errorResponse.setResponseTime(LocalDateTime.now());
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(UserAlreadyExistException.class)
	public ResponseEntity<ErrorResponse> handleUserAlreadyExist(final UserAlreadyExistException exception) {
		exception.printStackTrace();
		final ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
		errorResponse.setException(exception.getClass().getSimpleName());
		errorResponse.setMessage(exception.getMessage());
		errorResponse.setResponseTime(LocalDateTime.now());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(RoleNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleRoleNotFound(final RoleNotFoundException exception) {
		exception.printStackTrace();
		final ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		errorResponse.setException(exception.getClass().getSimpleName());
		errorResponse.setMessage(exception.getMessage());
		errorResponse.setResponseTime(LocalDateTime.now());
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

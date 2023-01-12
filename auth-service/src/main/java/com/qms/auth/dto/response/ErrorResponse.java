package com.qms.auth.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class ErrorResponse extends ApiResponse {

	private static final long serialVersionUID = 7426078926181012054L;

	// private HttpStatus httpStatus;
	private String exception;
//    private String message;
	private List<FieldError> fieldErrors;
}

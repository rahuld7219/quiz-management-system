package com.qms.auth.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse extends ApiResponse {

	private static final long serialVersionUID = 7426078926181012054L;

	// private HttpStatus httpStatus;
	private String exception;
//    private String message;
	private List<FieldError> fieldErrors;
}

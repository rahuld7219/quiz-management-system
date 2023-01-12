package com.qms.auth.dto.response;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse implements Serializable {

	private static final long serialVersionUID = 8611205396763004008L;

	private LocalDateTime responseTime;
	private HttpStatus httpStatus;
	private String message;

//	private Object fielderror;
}

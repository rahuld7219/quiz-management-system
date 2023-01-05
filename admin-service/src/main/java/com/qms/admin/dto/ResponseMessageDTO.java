package com.qms.admin.dto;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseMessageDTO {
	private HttpStatus status;
	private String message;
}

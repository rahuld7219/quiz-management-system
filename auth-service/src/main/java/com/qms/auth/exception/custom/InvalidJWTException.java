package com.qms.auth.exception.custom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class InvalidJWTException extends RuntimeException {

	private static final long serialVersionUID = 7491038298135527239L;

	private final String message;
}

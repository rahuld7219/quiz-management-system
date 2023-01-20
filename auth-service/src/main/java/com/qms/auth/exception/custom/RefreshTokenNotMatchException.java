package com.qms.auth.exception.custom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RefreshTokenNotMatchException extends RuntimeException {

	private static final long serialVersionUID = 3673381676581943948L;

	private final String message;
}

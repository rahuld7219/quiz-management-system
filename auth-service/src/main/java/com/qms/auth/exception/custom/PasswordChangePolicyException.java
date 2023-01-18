package com.qms.auth.exception.custom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PasswordChangePolicyException extends RuntimeException {

	private static final long serialVersionUID = -802409212477427247L;
	private final String message;
}

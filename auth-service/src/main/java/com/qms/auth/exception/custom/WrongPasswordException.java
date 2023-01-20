package com.qms.auth.exception.custom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class WrongPasswordException extends RuntimeException {

	private static final long serialVersionUID = 7893295453548074224L;

	private final String message;
}

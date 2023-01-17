package com.qms.auth.exception.custom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserAlreadyExistException extends RuntimeException {

	private static final long serialVersionUID = 361282144457657750L;

	private final String message;
}

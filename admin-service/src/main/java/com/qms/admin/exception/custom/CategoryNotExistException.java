package com.qms.admin.exception.custom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CategoryNotExistException extends RuntimeException {

	private static final long serialVersionUID = -7157341170270348664L;
	private final String message;
}

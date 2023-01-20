package com.qms.common.exception.custom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class QuizNotExistException extends RuntimeException {

	private static final long serialVersionUID = 3669733767425278316L;
	private final String message;
}

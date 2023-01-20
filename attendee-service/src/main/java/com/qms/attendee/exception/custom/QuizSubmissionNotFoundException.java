package com.qms.attendee.exception.custom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class QuizSubmissionNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -7157341170270348664L;
	private final String message;
}

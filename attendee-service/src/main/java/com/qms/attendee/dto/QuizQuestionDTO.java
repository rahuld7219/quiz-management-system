package com.qms.attendee.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class QuizQuestionDTO implements Serializable {

	private static final long serialVersionUID = 2233698973418997954L;

	private Long questionId;

	private String questionDetail;

	private String optionA;

	private String optionB;

	private String optionC;

	private String optionD;
}

package com.qms.admin.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class QuestionDTO {
	
	private String questionDetail;

	private String optionA;

	private String optionB;

	private String optionC;

	private String optionD;

	private String rightOption;

	private int marks;
}

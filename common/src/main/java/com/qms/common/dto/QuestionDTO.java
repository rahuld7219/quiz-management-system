package com.qms.common.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class QuestionDTO implements Serializable {

	private static final long serialVersionUID = 5399440466321941776L;

	private Long questionId;
	private String questionDetail;
	private String optionA;
	private String optionB;
	private String optionC;
	private String optionD;
	private String rightOption;
	private Integer marks;
}

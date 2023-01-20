package com.qms.admin.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class QuestionRequest {

	@NotBlank(message = "Question detail cannot be blank.")
	private String questionDetail;
	@NotBlank(message = "OptionA cannot be blank.")
	private String optionA;
	@NotBlank(message = "OptionB cannot be blank.")
	private String optionB;
	@NotBlank(message = "OptionC cannot be blank.")
	private String optionC;
	@NotBlank(message = "OptionD cannot be blank.")
	private String optionD;
	@NotBlank(message = "RightOption cannot be blank.")
	private String rightOption;
	@NotNull(message = "Marks cannot be blank.")
	private Integer marks;

}

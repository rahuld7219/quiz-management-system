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

	@NotBlank
	private String questionDetail;
	@NotBlank
	private String optionA;
	@NotBlank
	private String optionB;
	@NotBlank
	private String optionC;
	@NotBlank
	private String optionD;
	@NotBlank
	private String rightOption;
	@NotNull
	private Integer marks;

}

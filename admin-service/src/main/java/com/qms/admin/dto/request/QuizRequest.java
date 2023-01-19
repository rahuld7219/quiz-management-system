package com.qms.admin.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class QuizRequest {

	@NotBlank
	private String quizTitle;
	@NotNull
	private Long categoryId;
}

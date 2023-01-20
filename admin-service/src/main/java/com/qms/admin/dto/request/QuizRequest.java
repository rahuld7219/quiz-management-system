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

	@NotBlank(message = "Quiz title cannot be blank.")
	private String quizTitle;
	@NotNull(message = "Category id cannot be blank.")
	private Long categoryId;
}

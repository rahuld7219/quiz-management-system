package com.qms.attendee.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class QuestionAnswer implements Serializable {

	private static final long serialVersionUID = 3743554751813245798L;

	@NotNull(message = "Question id cannot be blank.")
	private Long questionId;

	@NotBlank(message = "Selected option cannot be blank.")
	@Size(max = 1, message = "Selected option can be of 1 letter (A, B, C, D)")
	private String selectedOption;
}

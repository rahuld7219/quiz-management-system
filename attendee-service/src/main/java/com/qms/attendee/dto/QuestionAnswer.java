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

	/**
	 * 
	 */
	private static final long serialVersionUID = 3743554751813245798L;
	
	@NotNull
	private Long questionId;
	
	@NotBlank
	@Size(max = 1)
	private String selectedOption;
}

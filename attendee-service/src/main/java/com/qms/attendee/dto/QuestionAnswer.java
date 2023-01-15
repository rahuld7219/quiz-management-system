package com.qms.attendee.dto;

import java.io.Serializable;

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
	
	private Long questionId;
	private String selectedOption;
}
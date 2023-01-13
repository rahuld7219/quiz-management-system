package com.qms.attendee.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class QuestionAnswer implements Serializable {

	private Long questionId;
	private String selectedOption;
}

package com.qms.attendee.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ResultDetail {

	private String questionDetail;
	private String submittedAnswer;
	private String correctAnswer;
	
}

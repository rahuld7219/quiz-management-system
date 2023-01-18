package com.qms.attendee.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ResultDetail {

	private String questionDetail;
	@JsonIgnore
	private String optionA;
	@JsonIgnore
	private String optionB;
	@JsonIgnore
	private String optionC;
	@JsonIgnore
	private String optionD;
	private String submittedAnswer;
	private String correctAnswer;

}

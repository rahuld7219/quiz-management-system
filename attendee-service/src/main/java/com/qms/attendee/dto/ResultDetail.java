package com.qms.attendee.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ResultDetail implements Serializable {

	private static final long serialVersionUID = 5723443883915308724L;

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

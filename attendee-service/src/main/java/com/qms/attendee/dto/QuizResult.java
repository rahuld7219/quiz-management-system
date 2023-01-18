package com.qms.attendee.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class QuizResult {

//	@JsonIgnore
//	private String name;
//	@JsonIgnore
//	private String email;
//	@JsonIgnore
//	private String quizTitle;
	
	@JsonIgnore
	private LocalDate examDate;
	private Long correctAnswersCount;
	private Long wrongAnswersCount;
	private Long totalScore;
	private List<ResultDetail> details; // TODO: rename it to ResponseDetail
}

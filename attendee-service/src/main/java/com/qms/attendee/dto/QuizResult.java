package com.qms.attendee.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class QuizResult implements Serializable {

	private static final long serialVersionUID = 1580996452753373165L;

	@JsonIgnore
	private LocalDate examDate;
	private Long correctAnswersCount;
	private Long wrongAnswersCount;
	private Long totalScore;
	private List<ResultDetail> details;
}

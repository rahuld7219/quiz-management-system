package com.qms.attendee.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class QuizResult {

	private Long correctAnswersCount;
	private Long wrongAnswersCount;
	private Long totalScore;
	private List<ResultDetail> details;
}

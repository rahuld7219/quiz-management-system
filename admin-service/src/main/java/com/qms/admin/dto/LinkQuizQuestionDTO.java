package com.qms.admin.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LinkQuizQuestionDTO {
	private String questionId; // TODO: link list of questions
	private String quizId;
}

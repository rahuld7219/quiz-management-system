package com.qms.admin.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LinkQuizQuestion {
	private String questionId; // TODO: link list of questions
	private String quizId;
}

package com.qms.admin.dto.request;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LinkQuizQuestionRequest implements Serializable {

	private static final long serialVersionUID = -1453833192874014372L;

	private String questionId; // TODO: link list of questions
	private String quizId;
}

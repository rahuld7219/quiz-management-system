package com.qms.admin.dto.request;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LinkQuizQuestionRequest implements Serializable {

	private static final long serialVersionUID = -1453833192874014372L;

	@NotEmpty
	@Size(min = 1, max = 10, message = "Number of question ids must be between 1 and 10 (both inclusive).")
	private List<Long> questionsIds;

	@NotNull(message = "Quiz id cannot be blank.")
	private Long quizId;
}

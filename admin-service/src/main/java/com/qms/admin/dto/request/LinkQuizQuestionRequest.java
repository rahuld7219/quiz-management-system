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
	@Size(max = 10)
	private List<Long> questionsIds;

	@NotNull
	private Long quizId;
}

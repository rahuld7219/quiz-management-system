package com.qms.attendee.dto.request;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.qms.attendee.dto.QuestionAnswer;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class QuizSubmission implements Serializable {

	private static final long serialVersionUID = -4309995216131985209L;

	@NotNull(message = "Quiz id cannot be blank.")
	private Long quizId;

	@NotEmpty(message = "answerList cannot be empty.")
	@Size(max = 100, message = "Answer list can contain maximum 100 responses.")
	private List<QuestionAnswer> answerList;
}

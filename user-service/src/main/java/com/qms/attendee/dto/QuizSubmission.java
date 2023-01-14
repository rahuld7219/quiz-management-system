package com.qms.attendee.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class QuizSubmission implements Serializable {

	private static final long serialVersionUID = -4309995216131985209L;

	private String quizId;
	private List<QuestionAnswer> answerList;
}

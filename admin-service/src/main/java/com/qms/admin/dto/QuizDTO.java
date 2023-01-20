package com.qms.admin.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class QuizDTO implements Serializable {

	private static final long serialVersionUID = -2944228706782857577L;

	private Long quizId;
	private String quizTitle;
	private Long categoryId;
}

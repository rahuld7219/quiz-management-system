package com.qms.admin.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class QuizDTO {
 
	private String title;
	private Long categoryId;
}

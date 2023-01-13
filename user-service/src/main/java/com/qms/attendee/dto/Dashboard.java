package com.qms.attendee.dto;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class Dashboard {

	private Long totalQuizCount;
	private Long attendedQuizCount;
	private List<Map<String, Object>> quizCountByCategory;
	private List<Map<String, Object>> attendedQuizCountByCategory;

}

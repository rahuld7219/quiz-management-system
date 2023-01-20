package com.qms.attendee.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class Dashboard implements Serializable {

	private static final long serialVersionUID = 1582266795055224625L;

	private Long totalQuizCount;
	private Long attendedQuizCount;
	private List<Map<String, Object>> quizCountByCategory;
	private List<Map<String, Object>> attendedQuizCountByCategory;

}

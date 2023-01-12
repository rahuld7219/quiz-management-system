package com.qms.admin.dto;

import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Dashboard {

	Long numberOfQuizzes;
	Long totalAttendees;
	List<Map<String, Object>> topFiveAttendedQuizzes;
}

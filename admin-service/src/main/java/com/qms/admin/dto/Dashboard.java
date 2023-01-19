package com.qms.admin.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Dashboard implements Serializable {

	private static final long serialVersionUID = -3657756535518191900L;
	
	Long numberOfQuizzes;
	Long totalAttendees;
	List<Map<String, Object>> topFiveAttendedQuizzes;
}

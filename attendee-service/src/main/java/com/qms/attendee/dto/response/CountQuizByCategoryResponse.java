package com.qms.attendee.dto.response;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.qms.common.dto.response.ApiResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class CountQuizByCategoryResponse extends ApiResponse {

	private static final long serialVersionUID = -3450086728838114422L;
	private Data data;

	@AllArgsConstructor
	@Getter
	@Setter
	public class Data implements Serializable {

		private static final long serialVersionUID = -2186121898805574872L;

		List<Map<String, Object>> quizCountByCategory;
	}
}

package com.qms.admin.dto.response;

import java.io.Serializable;

import com.qms.common.dto.QuestionDTO;
import com.qms.common.dto.response.ApiResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class QuestionResponse extends ApiResponse {

	private static final long serialVersionUID = -8502225026036757774L;

	private Data data;

	@AllArgsConstructor
	@Getter
	@Setter
	public class Data implements Serializable {

		private static final long serialVersionUID = -2186121898805574872L;

		private QuestionDTO question; 
	}
}

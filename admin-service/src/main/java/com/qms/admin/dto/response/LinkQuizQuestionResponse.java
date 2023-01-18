package com.qms.admin.dto.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.qms.common.dto.response.ApiResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class LinkQuizQuestionResponse extends ApiResponse {

	private static final long serialVersionUID = -4960838982395370767L;

	private Data data;

	@AllArgsConstructor
	@Getter
	@Setter
	public class Data implements Serializable {

		private static final long serialVersionUID = -2186121898805574872L;

		private List<Long> addedQuestionIds;

		public List<Long> addQuestionId(Long qId) {
			if (this.addedQuestionIds == null) {
				this.addedQuestionIds = new ArrayList<>();
			}
			this.addedQuestionIds.add(qId);
			return this.addedQuestionIds;
		}

	}
}

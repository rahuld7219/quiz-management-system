package com.qms.auth.dto.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class SignUpResponse extends ApiResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5388400158018270310L;

	private Data data;

	@AllArgsConstructor
	@Getter
	@Setter
	public class Data implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = -7581251400331230487L;

		@JsonIgnore
		private Long id;
		private String emailId;

	}
}

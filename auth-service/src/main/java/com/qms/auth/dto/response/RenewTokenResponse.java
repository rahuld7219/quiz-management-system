package com.qms.auth.dto.response;

import java.io.Serializable;

import com.qms.common.dto.response.ApiResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class RenewTokenResponse extends ApiResponse {

	private static final long serialVersionUID = 235826148828623088L;

	private Data data;

	@AllArgsConstructor
	@Getter
	@Setter
	public class Data implements Serializable {

		private static final long serialVersionUID = -5875753905217261534L;

		private String accessToken;
		private String refreshToken;

	}

}

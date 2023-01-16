package com.qms.auth.dto.response;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class LoginResponse extends ApiResponse {

	private static final long serialVersionUID = 8378512252815115711L;

	private Data data;

	@AllArgsConstructor
	@Getter
	@Setter
	public class Data implements Serializable {

		private static final long serialVersionUID = -271687717746649778L;

		private String accessToken;
		private String refreshToken;
		private String emailId;
		private List<String> roles;

	}
}

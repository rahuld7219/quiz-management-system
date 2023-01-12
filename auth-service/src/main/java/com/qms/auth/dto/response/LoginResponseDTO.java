package com.qms.auth.dto.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDTO extends ApiResponse {

	private static final long serialVersionUID = 8378512252815115711L;

	private String accessToken;
	private String refreshToken;
	private String emailId;
	private List<String> roles;

	public LoginResponseDTO(String accessToken, String refreshToken, String emailId, List<String> roles) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.emailId = emailId;
		this.roles = roles;
	}
}

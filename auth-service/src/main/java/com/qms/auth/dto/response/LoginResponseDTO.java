package com.qms.auth.dto.response;

import java.util.List;

import lombok.Data;

@Data
public class LoginResponseDTO {

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

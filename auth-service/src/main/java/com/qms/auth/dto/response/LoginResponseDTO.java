package com.qms.auth.dto.response;

import java.util.List;

import lombok.Data;

@Data
public class LoginResponseDTO {

	private String accessToken;
	private String type = "Bearer"; // TODO: Constant separate
	private String emailId;
	private List<String> roles;

	public LoginResponseDTO(String accessToken, String emailId, List<String> roles) {
		this.accessToken = accessToken;
		this.emailId = emailId;
		this.roles = roles;
	}
}

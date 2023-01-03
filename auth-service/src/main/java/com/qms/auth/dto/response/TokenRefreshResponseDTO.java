package com.qms.auth.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenRefreshResponseDTO {
	private String accessToken;
	private String refreshToken;
	private String tokenType = "Bearer";

	public TokenRefreshResponseDTO(String accessToken, String refreshToken) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}
}
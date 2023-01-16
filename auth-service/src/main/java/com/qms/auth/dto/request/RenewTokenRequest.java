package com.qms.auth.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RenewTokenRequest {
	private String refreshToken;
}

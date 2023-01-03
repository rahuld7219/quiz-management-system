package com.qms.auth.dto.request;

import lombok.Data;

@Data
public class LoginRequestDTO {

	// TODO: Do validation
	private String emailId;

	private String password;

}

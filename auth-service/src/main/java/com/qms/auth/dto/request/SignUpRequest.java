package com.qms.auth.dto.request;

import lombok.Data;

@Data
public class SignUpRequest {

	// TODO: validation
	private String firstName;
	private String lastName;
	private String emailId;
	private String password;
	private String mobileNumber;

}
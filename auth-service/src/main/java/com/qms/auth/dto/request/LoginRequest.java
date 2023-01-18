package com.qms.auth.dto.request;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginRequest implements Serializable {

	private static final long serialVersionUID = -3954775695279968684L;

	@NotBlank
	@Email
	private String emailId;

	@NotBlank
	private String password;

}

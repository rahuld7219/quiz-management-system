package com.qms.auth.dto.request;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class LoginRequest implements Serializable {

	private static final long serialVersionUID = -3954775695279968684L;

	@NotBlank(message = "Email id cannot be blank.")
	@Email
	@Size(max = 20)
	private String emailId;

	@NotBlank(message = "Password cannot be blank.")
	@Size(min = 3, max = 20, message = "Password length should be between 3 and 20 (inclusive).")
	private String password;

}

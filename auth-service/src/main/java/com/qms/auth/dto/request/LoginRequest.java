package com.qms.auth.dto.request;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class LoginRequest implements Serializable {

	private static final long serialVersionUID = -3954775695279968684L;

	@NotBlank
	@Email
	@Size(max = 20)
	private String emailId;

	@NotBlank
	@Size(min = 3, max = 20)
	private String password;

}

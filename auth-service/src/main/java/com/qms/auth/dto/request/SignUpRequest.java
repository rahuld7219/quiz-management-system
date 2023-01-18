package com.qms.auth.dto.request;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class SignUpRequest implements Serializable {

	private static final long serialVersionUID = 8598657950076627260L;

	@NotBlank
	private String firstName;

	@NotBlank
	private String lastName;

	@NotBlank
	@Email
	private String emailId;

	@NotBlank
	private String password;

	@NotBlank
	private String mobileNumber;

}

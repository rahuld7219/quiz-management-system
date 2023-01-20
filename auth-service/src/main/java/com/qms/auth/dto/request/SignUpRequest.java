package com.qms.auth.dto.request;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class SignUpRequest implements Serializable {

	private static final long serialVersionUID = 8598657950076627260L;

	@NotBlank
	@Size(min = 3, max = 20)
	private String firstName;

	@NotBlank
	@Size(min = 3, max = 20)
	private String lastName;

	@NotBlank
	@Email
	@Size(max = 20)
	private String emailId;

	@NotBlank
	@Size(min = 3, max = 20)
	private String password;

	@NotBlank
	@Size(min = 10, max = 10)
	private String mobileNumber;

}

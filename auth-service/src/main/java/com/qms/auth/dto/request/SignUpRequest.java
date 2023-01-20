package com.qms.auth.dto.request;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class SignUpRequest implements Serializable {

	private static final long serialVersionUID = 8598657950076627260L;

	@NotBlank(message = "First name cannot be blank.")
	@Size(min = 3, max = 20, message = "First name lengh should be between 3 and 20. (inclusive).")
	private String firstName;

	@NotBlank(message = "Last name cannot be blank.")
	@Size(min = 3, max = 20, message = "Last name lengh should be between 3 and 20. (inclusive).")
	private String lastName;

	@NotBlank(message = "Email id cannot be blank.")
	@Email
	@Size(max = 20, message = "Email id lengh should should not exceed 20 characters.")
	private String emailId;

	@NotBlank(message = "Password cannot be blank.")
	@Size(min = 3, max = 20, message = "Password lengh should be between 3 and 20. (inclusive).")
	private String password;

	@NotBlank(message = "Mobile number cannot be blank.")
	@Size(min = 10, max = 10, message = "Mobile number lengh should be 10 digits.")
	private String mobileNumber;

}

package com.qms.auth.dto.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordRequest implements Serializable {

	private static final long serialVersionUID = 7524663818115542142L;

	@NotBlank
	private String oldPassword;

	@NotBlank
	private String newPassword;

	@NotBlank
	private String againNewPassword;
}

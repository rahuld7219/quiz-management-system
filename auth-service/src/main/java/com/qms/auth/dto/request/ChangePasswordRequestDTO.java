package com.qms.auth.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordRequestDTO { //TODO: implements Serializable

	//TODO: do validation
	private String oldPassword;
	private String newPassword;
	private String againNewPassword;
}

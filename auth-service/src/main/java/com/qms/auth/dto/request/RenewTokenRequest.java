package com.qms.auth.dto.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RenewTokenRequest implements Serializable {

	private static final long serialVersionUID = -9143204329252659667L;
	
	@NotBlank
	private String refreshToken;
}

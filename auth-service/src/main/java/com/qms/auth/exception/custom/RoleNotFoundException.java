package com.qms.auth.exception.custom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RoleNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -3809479481917807497L;

	private final String message;
}

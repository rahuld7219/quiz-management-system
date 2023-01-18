package com.qms.common.dto.response;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FieldError implements Serializable {

	private static final long serialVersionUID = 6911714568696787321L;
	
	private String field;
	private String errorCode;

}

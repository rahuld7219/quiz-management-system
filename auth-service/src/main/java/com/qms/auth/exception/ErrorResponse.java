package com.qms.auth.exception;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class ErrorResponse {

    private HttpStatus httpStatus;
    private String exception;
    private String message;
    private List<FieldError> fieldErrors;
}

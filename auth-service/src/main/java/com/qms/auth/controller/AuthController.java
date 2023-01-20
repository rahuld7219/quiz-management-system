package com.qms.auth.controller;

import java.net.URI;
import java.time.LocalDateTime;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qms.auth.constant.AuthMessageConstant;
import com.qms.auth.constant.AuthURIConstant;
import com.qms.auth.dto.request.ChangePasswordRequest;
import com.qms.auth.dto.request.LoginRequest;
import com.qms.auth.dto.request.RenewTokenRequest;
import com.qms.auth.dto.request.SignUpRequest;
import com.qms.auth.dto.response.SignUpResponse;
import com.qms.auth.service.AuthService;
import com.qms.common.dto.response.ApiResponse;

@RestController
@RequestMapping(AuthURIConstant.BASE_AUTH_URL)
public class AuthController {

	@Autowired
	private AuthService authService;

	@PostMapping(AuthURIConstant.REGISTER_URL)
	public ResponseEntity<ApiResponse> register(@Valid @RequestBody final SignUpRequest signUpRequest) {

		SignUpResponse response = authService.register(signUpRequest);
		URI location = URI.create(
				AuthURIConstant.BASE_AUTH_URL + AuthURIConstant.REGISTER_URL + "/" + response.getData().getId());

		return ResponseEntity.created(location).body(response);
	}

	@PostMapping(AuthURIConstant.LOGIN_URL)
	public ResponseEntity<ApiResponse> login(@Valid @RequestBody final LoginRequest loginRequest) {

		return ResponseEntity.ok(authService.login(loginRequest));

	}

	@PostMapping(AuthURIConstant.REFRESH_URL)
	public ResponseEntity<ApiResponse> renewTokens(@Valid @RequestBody final RenewTokenRequest tokenRefreshRequest) {
		return ResponseEntity.ok(authService.renewTokens(tokenRefreshRequest));
	}

	@PostMapping(AuthURIConstant.CHANGE_PASSWORD_URL)
	public ResponseEntity<ApiResponse> changePassword(
			@Valid @RequestBody final ChangePasswordRequest changePasswordRequest) {
		authService.changePassword(changePasswordRequest);
		return ResponseEntity.ok(new ApiResponse().setHttpStatus(HttpStatus.OK).setResponseTime(LocalDateTime.now())
				.setMessage(AuthMessageConstant.PASSWORD_CHANGE));
	}

}

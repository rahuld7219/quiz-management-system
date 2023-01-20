package com.qms.auth.service;

import com.qms.auth.dto.request.ChangePasswordRequest;
import com.qms.auth.dto.request.LoginRequest;
import com.qms.auth.dto.request.RenewTokenRequest;
import com.qms.auth.dto.request.SignUpRequest;
import com.qms.auth.dto.response.LoginResponse;
import com.qms.auth.dto.response.RenewTokenResponse;
import com.qms.auth.dto.response.SignUpResponse;

public interface AuthService {

	SignUpResponse register(final SignUpRequest signUpRequest);

	LoginResponse login(final LoginRequest loginRequestDto);

	RenewTokenResponse renewTokens(final RenewTokenRequest tokenRefreshRequestDTO);

	void changePassword(final ChangePasswordRequest changePasswordRequest);

//	ResponseMessageDTO logout();
}

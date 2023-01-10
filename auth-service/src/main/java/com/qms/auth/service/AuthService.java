package com.qms.auth.service;

import com.qms.auth.dto.Tokens;
import com.qms.auth.dto.request.ChangePasswordRequestDTO;
import com.qms.auth.dto.request.LoginRequestDTO;
import com.qms.auth.dto.request.RenewTokenRequestDTO;
import com.qms.auth.dto.request.SignUpRequestDTO;
import com.qms.auth.dto.response.LoginResponseDTO;

// TODO: make separate service for role and user
public interface AuthService {

	Long register(final SignUpRequestDTO signUpRequestDto);

	LoginResponseDTO login(final LoginRequestDTO loginRequestDto);

	Tokens renewTokens(final RenewTokenRequestDTO tokenRefreshRequestDTO);

	void changePassword(final ChangePasswordRequestDTO changePasswordRequest);

//	ResponseMessageDTO logout();
}

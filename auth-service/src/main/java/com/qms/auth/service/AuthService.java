package com.qms.auth.service;

import com.qms.auth.dto.request.ChangePasswordRequestDTO;
import com.qms.auth.dto.request.LoginRequestDTO;
import com.qms.auth.dto.request.SignUpRequestDTO;
import com.qms.auth.dto.request.TokenRefreshRequestDTO;
import com.qms.auth.dto.response.LoginResponseDTO;
import com.qms.auth.dto.response.ResponseMessageDTO;
import com.qms.auth.dto.response.TokenRefreshResponseDTO;

public interface AuthService {

	ResponseMessageDTO register(SignUpRequestDTO signUpRequestDto);
	LoginResponseDTO login(LoginRequestDTO loginRequestDto);
	TokenRefreshResponseDTO tokenRefresh(TokenRefreshRequestDTO tokenRefreshRequestDTO);
	ResponseMessageDTO changePassword(ChangePasswordRequestDTO changePasswordRequest);
	
//	ResponseMessageDTO logout();
}

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

import com.qms.auth.constant.MessageConstant;
import com.qms.auth.constant.URIConstant;
import com.qms.auth.dto.request.ChangePasswordRequest;
import com.qms.auth.dto.request.LoginRequest;
import com.qms.auth.dto.request.RenewTokenRequest;
import com.qms.auth.dto.request.SignUpRequest;
import com.qms.auth.dto.response.ApiResponse;
import com.qms.auth.dto.response.SignUpResponse;
import com.qms.auth.service.AuthService;

//@CrossOrigin(origins = "*", maxAge = 3600) ???
@RestController
@RequestMapping(URIConstant.BASE_AUTH_URL)
public class AuthController {

	@Autowired
	private AuthService authService;

	@PostMapping(URIConstant.REGISTER_URL)
	public ResponseEntity<ApiResponse> register(@Valid @RequestBody final SignUpRequest signUpRequest) {

		SignUpResponse response = authService.register(signUpRequest);
//		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/").toUriString());
//		URI location = new URI("/api/");
		URI location = URI
				.create(URIConstant.BASE_AUTH_URL + URIConstant.REGISTER_URL + "/" + response.getData().getId());

		return ResponseEntity.created(location).body(response);
	}

	@PostMapping(URIConstant.LOGIN_URL)
	public ResponseEntity<ApiResponse> login(@Valid @RequestBody final LoginRequest loginRequest) {

		return ResponseEntity.ok(authService.login(loginRequest));

	}

	@PostMapping(URIConstant.REFRESH_URL)
	public ResponseEntity<ApiResponse> renewTokens(@Valid @RequestBody final RenewTokenRequest tokenRefreshRequest) {
		return ResponseEntity.ok(authService.renewTokens(tokenRefreshRequest));
	}

	@PostMapping(URIConstant.CHANGE_PASSWORD_URL)
	public ResponseEntity<ApiResponse> changePassword(
			@Valid @RequestBody final ChangePasswordRequest changePasswordRequest) {
		authService.changePassword(changePasswordRequest);
		return ResponseEntity.ok(new ApiResponse().setHttpStatus(HttpStatus.OK).setResponseTime(LocalDateTime.now())
				.setMessage(MessageConstant.PASSWORD_CHANGE));
	}

//	@GetMapping("/logout")
//	public ResponseEntity<?> logout() {
//		UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		return ResponseEntity.ok(new MessageResponseDTO("Logged out successfully"));
//	}

}

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

import com.qms.auth.dto.request.ChangePasswordRequest;
import com.qms.auth.dto.request.LoginRequest;
import com.qms.auth.dto.request.RenewTokenRequest;
import com.qms.auth.dto.request.SignUpRequest;
import com.qms.auth.dto.response.ApiResponse;
import com.qms.auth.dto.response.SignUpResponse;
import com.qms.auth.service.AuthService;

//@CrossOrigin(origins = "*", maxAge = 3600) ???
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	@PostMapping("/register")
	public ResponseEntity<ApiResponse> register(@Valid @RequestBody final SignUpRequest signUpRequest) {

		SignUpResponse response = authService.register(signUpRequest);
//		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/").toUriString());
//		URI location = new URI("/api/");
		URI location = URI.create("/api/v1/auth/register/" + response.getData().getId());

		return ResponseEntity.created(location).body(response);
	}

	@PostMapping("/login")
	public ResponseEntity<ApiResponse> login(@Valid @RequestBody final LoginRequest loginRequest) {

		return ResponseEntity.ok(authService.login(loginRequest));

	}

	@PostMapping("/refresh")
	public ResponseEntity<ApiResponse> renewTokens(@Valid @RequestBody final RenewTokenRequest tokenRefreshRequest) {
		return ResponseEntity.ok(authService.renewTokens(tokenRefreshRequest));
	}

	@PostMapping("/changePassword")
	public ResponseEntity<ApiResponse> changePassword(
			@Valid @RequestBody final ChangePasswordRequest changePasswordRequest) {
		authService.changePassword(changePasswordRequest);
		return ResponseEntity.ok(new ApiResponse().setHttpStatus(HttpStatus.OK).setResponseTime(LocalDateTime.now())
				.setMessage("Password change successful."));
	}

//	@GetMapping("/logout")
//	public ResponseEntity<?> logout() {
//		UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		return ResponseEntity.ok(new MessageResponseDTO("Logged out successfully"));
//	}

}

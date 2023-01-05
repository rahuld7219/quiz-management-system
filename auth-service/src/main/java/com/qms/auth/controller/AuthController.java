package com.qms.auth.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qms.auth.dto.request.ChangePasswordRequestDTO;
import com.qms.auth.dto.request.LoginRequestDTO;
import com.qms.auth.dto.request.SignUpRequestDTO;
import com.qms.auth.dto.request.TokenRefreshRequestDTO;
import com.qms.auth.dto.response.LoginResponseDTO;
import com.qms.auth.dto.response.ResponseMessageDTO;
import com.qms.auth.dto.response.TokenRefreshResponseDTO;
import com.qms.auth.service.AuthService;

//@CrossOrigin(origins = "*", maxAge = 3600) ???
@RestController
@RequestMapping("/api/v1/auth") // TODO: place constants/literals separately
public class AuthController {
	
	@Autowired
	private AuthService authService;

	//TODO: make a standard response for all apis
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody final SignUpRequestDTO signUpRequest) { // TODO: DO validation
		ResponseMessageDTO response = authService.register(signUpRequest);
		return ResponseEntity.status(response.getStatus()).body(response.getMessage()); // TODO: send URI also when created and rename ResponseMessageDTO or create standard response template

	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody final LoginRequestDTO loginRequest) { // TODO: DO validation
		return ResponseEntity.ok(authService.login(loginRequest));
		
	}

	@PostMapping("/refresh")
	public ResponseEntity<TokenRefreshResponseDTO> tokenRefresh(@RequestBody TokenRefreshRequestDTO tokenRefreshRequest) {
		return ResponseEntity.ok(authService.tokenRefresh(tokenRefreshRequest)); // TODO send created
	}
	
	@PostMapping("/changePassword")
	public ResponseEntity<?> changePassword(@Valid @RequestBody final ChangePasswordRequestDTO changePasswordRequest) {
		ResponseMessageDTO response = authService.changePassword(changePasswordRequest);
		return ResponseEntity.status(response.getStatus()).body(response.getMessage()); // TODO: send URI also when created and rename ResponseMessageDTO or create standard response template
	}

//	@GetMapping("/logout")
//	public ResponseEntity<?> logout() {
//		UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		return ResponseEntity.ok(new MessageResponseDTO("Logged out successfully"));
//	}

}

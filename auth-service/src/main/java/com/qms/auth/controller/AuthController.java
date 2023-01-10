package com.qms.auth.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qms.auth.dto.Tokens;
import com.qms.auth.dto.request.ChangePasswordRequestDTO;
import com.qms.auth.dto.request.LoginRequestDTO;
import com.qms.auth.dto.request.RenewTokenRequestDTO;
import com.qms.auth.dto.request.SignUpRequestDTO;
import com.qms.auth.dto.response.LoginResponseDTO;
import com.qms.auth.service.AuthService;

//@CrossOrigin(origins = "*", maxAge = 3600) ???
@RestController
@RequestMapping("/api/v1/auth") // TODO: place constants/literals separately
public class AuthController {

	@Autowired
	private AuthService authService;

	// TODO: make a standard response for all apis

	@PostMapping("/register")
	public ResponseEntity<String> register(@Valid @RequestBody final SignUpRequestDTO signUpRequest) { // TODO: DO
																										// validation
		Long id = authService.register(signUpRequest);
//		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/").toUriString());
		URI location = URI.create("/api/v1/auth/register/" + id);

		return ResponseEntity.created(location).body("User created Successfully.");
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody final LoginRequestDTO loginRequest) { // TODO: DO
																											// validation
		return ResponseEntity.ok(authService.login(loginRequest));

	}

	@PostMapping("/refresh")
	public ResponseEntity<Tokens> renewTokens(@RequestBody RenewTokenRequestDTO tokenRefreshRequest) {
		return ResponseEntity.ok(authService.renewTokens(tokenRefreshRequest));
	}

	@PostMapping("/changePassword")
	public ResponseEntity<String> changePassword(
			@Valid @RequestBody final ChangePasswordRequestDTO changePasswordRequest) {
		authService.changePassword(changePasswordRequest);
		return ResponseEntity.ok("Password changed successfully.");
	}

//	@GetMapping("/logout")
//	public ResponseEntity<?> logout() {
//		UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		return ResponseEntity.ok(new MessageResponseDTO("Logged out successfully"));
//	}

}

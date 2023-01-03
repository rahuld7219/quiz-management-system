package com.qms.auth.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qms.auth.constant.RoleName;
import com.qms.auth.dto.request.LoginRequestDTO;
import com.qms.auth.dto.request.SignUpRequestDTO;
import com.qms.auth.dto.request.TokenRefreshRequestDTO;
import com.qms.auth.dto.response.LoginResponseDTO;
import com.qms.auth.dto.response.ResponseMessageDTO;
import com.qms.auth.dto.response.TokenRefreshResponseDTO;
import com.qms.auth.model.Role;
import com.qms.auth.model.User;
import com.qms.auth.repository.RoleRepository;
import com.qms.auth.repository.UserRepository;
import com.qms.auth.service.impl.UserDetailsImpl;
import com.qms.auth.util.JwtUtils;
import com.qms.auth.util.RedisCacheUtil;

//@CrossOrigin(origins = "*", maxAge = 3600) ???
@RestController
@RequestMapping("/api/v1/auth") // TODO: place constants/literals separately
public class AuthController {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private RedisCacheUtil redisCache;

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody final SignUpRequestDTO signUpRequest) { // TODO: DO validation
		if (userRepository.existsByEmailId(signUpRequest.getEmailId())) {
			return ResponseEntity.badRequest().body(new ResponseMessageDTO("Error: Username is already taken!"));
		}

		Set<Role> roles = new HashSet<>(); // TODO: how to use design pattern(like factory if have to choose from many
											// roles, i.e., if signuprequest also have role info)
		Role userRole = roleRepository.findByRoleName(RoleName.ATTENDEE)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		roles.add(userRole);
		// TODO: use DTO <-> model mapper
		User user = new User(signUpRequest.getFirstName(), signUpRequest.getLastName(), signUpRequest.getEmailId(),
				passwordEncoder.encode(signUpRequest.getPassword()), signUpRequest.getMobileNumber(), roles);
		userRepository.save(user);

		return ResponseEntity.ok(new ResponseMessageDTO("User registered successfully!"));

	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody final LoginRequestDTO loginRequest) { // TODO: DO validation

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmailId(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
		String jwtAccess = jwtUtils.generateJwtAccessToken(user.getUsername());
		String jwtRefresh = jwtUtils.generateJwtRefreshToken(user.getUsername());

//		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		redisCache.cacheValue(user.getUsername(), jwtRefresh);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new LoginResponseDTO(jwtAccess, jwtRefresh, userDetails.getUsername(), roles));
	}

	@PostMapping("/refresh")
	public ResponseEntity<?> tokenRefresh(@RequestBody TokenRefreshRequestDTO tokenRefreshRequest) {
		String refreshToken = tokenRefreshRequest.getRefreshToken();
		if (jwtUtils.validateJwtToken(refreshToken)) {
			String username = jwtUtils.getUserNameFromJwtToken(refreshToken);
			String storedRefreshToken = redisCache.getCachedValue(username);
			if (!refreshToken.equals(storedRefreshToken) || !userRepository.existsByEmailId(username)) {
				throw new RuntimeException("Refresh token is not in database or user is not present"); // TODO:
																										// refactor,
																										// throw custom
																										// exception
			}
			String newAccessToken = jwtUtils.generateJwtAccessToken(username);
			String newRefreshToken = jwtUtils.generateJwtRefreshToken(username);
			redisCache.cacheValue(username, newRefreshToken);
			return ResponseEntity.ok(new TokenRefreshResponseDTO(newAccessToken, newRefreshToken));
		} else {
			throw new RuntimeException("Refresh token is not valid"); // TODO: refactor, throw custom exception
		}
	}

//	@GetMapping("/logout")
//	public ResponseEntity<?> logout() {
//		UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		return ResponseEntity.ok(new MessageResponseDTO("Logged out successfully"));
//	}

}

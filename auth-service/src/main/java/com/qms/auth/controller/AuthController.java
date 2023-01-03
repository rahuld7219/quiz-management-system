package com.qms.auth.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
import com.qms.auth.dto.response.LoginResponseDTO;
import com.qms.auth.dto.response.MessageResponseDTO;
import com.qms.auth.model.Role;
import com.qms.auth.model.User;
import com.qms.auth.repository.RoleRepository;
import com.qms.auth.repository.UserRepository;
import com.qms.auth.service.impl.UserDetailsImpl;
import com.qms.auth.util.JwtUtils;

//@CrossOrigin(origins = "*", maxAge = 3600) ???
@RestController
@RequestMapping("/api/v1/auth") // TODO: place constants/literals separately
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody final LoginRequestDTO loginRequest) { // TODO: DO validation

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmailId(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new LoginResponseDTO(jwt, userDetails.getUsername(), roles));
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody SignUpRequestDTO signUpRequest) { // TODO: DO validation
		if (userRepository.existsByEmailId(signUpRequest.getEmailId())) {
			return ResponseEntity.badRequest().body(new MessageResponseDTO("Error: Username is already taken!"));
		}

		// TODO: use DTO <-> model mapper
		User user = new User(signUpRequest.getFirstName(), signUpRequest.getLastName(), signUpRequest.getEmailId(),
				passwordEncoder.encode(signUpRequest.getPassword()), signUpRequest.getMobileNumber());

		Set<Role> roles = new HashSet<>(); // TODO: how to use design pattern(like factory if have to choose from many
											// roles, i.e., if signuprequest also have role info)
		Role userRole = roleRepository.findByRoleName(RoleName.ATTENDEE)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		roles.add(userRole);
		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponseDTO("User registered successfully!"));

	}
	
	@PostMapping("/refresh")
	public ResponseEntity<?> refreshToken() {
		// https://javatechonline.com/spring-boot-redis-crud-example/
		return null;
		
	}

}

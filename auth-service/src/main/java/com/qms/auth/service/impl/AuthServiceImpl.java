package com.qms.auth.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.qms.auth.constant.RoleName;
import com.qms.auth.dto.request.ChangePasswordRequestDTO;
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
import com.qms.auth.service.AuthService;
import com.qms.auth.util.JwtUtils;
import com.qms.auth.util.RedisCacheUtil;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private RedisCacheUtil redisCacheUtil;

	@Override
	public ResponseMessageDTO register(SignUpRequestDTO signUpRequest) {
		if (userRepository.existsByEmailId(signUpRequest.getEmailId())) {
			return new ResponseMessageDTO(HttpStatus.BAD_REQUEST, "Error: Username is already taken!");
		}

		Set<Role> roles = new HashSet<>(); // TODO: how to use design pattern(like factory if have to choose from many
											// roles, i.e., if signuprequest also have role info)

		Role userRole = roleRepository.findByRoleName(RoleName.ATTENDEE)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found.")); // TODO: create custom formatted
																						// exception
		roles.add(userRole); // TODO: put this adding Role entity class (see hibernate CD doc)

		// TODO: use DTO <-> model mapper
		User user = new User(signUpRequest.getFirstName(), signUpRequest.getLastName(), signUpRequest.getEmailId(),
				passwordEncoder.encode(signUpRequest.getPassword()), signUpRequest.getMobileNumber(), roles);
		userRepository.save(user);

		return new ResponseMessageDTO(HttpStatus.CREATED, "User registered successfully!");
	}

	@Override
	public LoginResponseDTO login(LoginRequestDTO loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmailId(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String jwtAccess = jwtUtils.generateAccessToken(userDetails);
		String jwtRefresh = jwtUtils.generateRefreshToken(userDetails);

		redisCacheUtil.cacheValue(userDetails.getUsername(), jwtRefresh);

		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return new LoginResponseDTO(jwtAccess, jwtRefresh, userDetails.getUsername(), roles);
	}

	@Override
	public TokenRefreshResponseDTO tokenRefresh(TokenRefreshRequestDTO tokenRefreshRequest) {
		String refreshToken = tokenRefreshRequest.getRefreshToken();
		if (jwtUtils.isTokenValid(refreshToken)) {
			String username = jwtUtils.extractUsername(refreshToken);
			String storedRefreshToken = redisCacheUtil.getCachedValue(username);
			if (Boolean.FALSE.equals(userRepository.existsByEmailId(username))
					|| !refreshToken.equals(storedRefreshToken)) {
				throw new RuntimeException("Refresh token is not in database or user is not present"); // TODO: throw
																										// custom
																										// exception
			}

			// TODO: check here the security context on debugging
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

			String newAccessToken = jwtUtils.generateAccessToken(userDetails);
			String newRefreshToken = jwtUtils.generateRefreshToken(userDetails);
			redisCacheUtil.cacheValue(username, newRefreshToken);
			return new TokenRefreshResponseDTO(newAccessToken, newRefreshToken);
		} else {
			throw new RuntimeException("Refresh token is not valid"); // TODO: throw custom exception inside the
																		// isTokenValid
		}
	}

	@Override
	public ResponseMessageDTO changePassword(ChangePasswordRequestDTO changePasswordRequest) {
		if (changePasswordRequest.getOldPassword().equalsIgnoreCase(changePasswordRequest.getNewPassword())) {
			throw new RuntimeException("New password cannot be similar to old password.");
		}
		if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getAgainNewPassword())) {
			throw new RuntimeException("Re-entered new password do not match."); // TODO: use custom Exception
		}

		String email = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
				.getUsername();
		User user = userRepository.findByEmailId(email).get(); // TODO: use isPresent()

		if (!passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())) {
			throw new RuntimeException("Wrong password provided."); // TODO: use custom Exception
		}
		user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
		userRepository.save(user);
		return new ResponseMessageDTO(HttpStatus.OK, "Password change successful."); // TODO: use custom Exception
	}

}

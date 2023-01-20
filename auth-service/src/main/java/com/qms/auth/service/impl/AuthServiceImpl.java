package com.qms.auth.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.qms.auth.constant.AuthMessageConstant;
import com.qms.auth.dto.Tokens;
import com.qms.auth.dto.request.ChangePasswordRequest;
import com.qms.auth.dto.request.LoginRequest;
import com.qms.auth.dto.request.RenewTokenRequest;
import com.qms.auth.dto.request.SignUpRequest;
import com.qms.auth.dto.response.LoginResponse;
import com.qms.auth.dto.response.RenewTokenResponse;
import com.qms.auth.dto.response.SignUpResponse;
import com.qms.auth.exception.custom.PasswordChangePolicyException;
import com.qms.auth.exception.custom.RefreshTokenNotMatchException;
import com.qms.auth.exception.custom.RoleNotFoundException;
import com.qms.auth.exception.custom.UserAlreadyExistException;
import com.qms.auth.exception.custom.WrongPasswordException;
import com.qms.auth.service.AuthService;
import com.qms.auth.util.AuthRedisCacheUtil;
import com.qms.auth.util.JwtUtils;
import com.qms.common.constant.RoleName;
import com.qms.common.model.Role;
import com.qms.common.model.User;
import com.qms.common.repository.RoleRepository;
import com.qms.common.repository.UserRepository;

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
	private AuthRedisCacheUtil redisCacheUtil;

	@Override
	@Transactional
	public SignUpResponse register(final SignUpRequest signUpRequest) {
		if (Boolean.TRUE.equals(userRepository.existsByEmailId(signUpRequest.getEmailId()))) {
			throw new UserAlreadyExistException(AuthMessageConstant.USER_ALREADY_EXIST);
		}

		Role userRole = roleRepository.findByRoleName(RoleName.ATTENDEE)
				.orElseThrow(() -> new RoleNotFoundException(AuthMessageConstant.USER_ROLE_NOT_FOUND));

		User user = new User();
		user.addRole(userRole);

		userRepository.save(mapToModel(user, signUpRequest));

		SignUpResponse response = new SignUpResponse();
		response.setData(response.new Data(user.getId(), user.getEmailId())).setHttpStatus(HttpStatus.CREATED)
				.setResponseTime(LocalDateTime.now()).setMessage(AuthMessageConstant.USER_CREATED);
		return response;
	}

	@Override
	public LoginResponse login(final LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmailId(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		final Tokens tokens = generateTokens(userDetails);

		redisCacheUtil.cacheValue(userDetails.getUsername(), tokens.getRefreshToken());

		List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());

		LoginResponse response = new LoginResponse();
		response.setData(
				response.new Data(tokens.getAccessToken(), tokens.getRefreshToken(), userDetails.getUsername(), roles))
				.setHttpStatus(HttpStatus.OK).setResponseTime(LocalDateTime.now())
				.setMessage(AuthMessageConstant.LOGIN_SUCCESS);
		return response;
	}

	@Override
	@Transactional
	public RenewTokenResponse renewTokens(final RenewTokenRequest tokenRefreshRequest) {

		final String refreshToken = tokenRefreshRequest.getRefreshToken();
		jwtUtils.isTokenValid(refreshToken);
		final String username = jwtUtils.extractUsername(refreshToken);
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

		final String storedRefreshToken = redisCacheUtil.getCachedValue(username);

		if (!doesTokenMatches(refreshToken, storedRefreshToken)) {
			throw new RefreshTokenNotMatchException(AuthMessageConstant.REFRESH_TOKEN_NOT_MATCH);
		}

		final Tokens newTokens = generateTokens(userDetails);
		redisCacheUtil.cacheValue(username, newTokens.getRefreshToken());
		RenewTokenResponse response = new RenewTokenResponse();
		response.setData(response.new Data(newTokens.getAccessToken(), newTokens.getRefreshToken()))
				.setHttpStatus(HttpStatus.OK).setResponseTime(LocalDateTime.now())
				.setMessage(AuthMessageConstant.REFRESH_SUCCESS);
		return response;
	}

	@Override
	@Transactional
	public void changePassword(ChangePasswordRequest changePasswordRequest) {
		if (changePasswordRequest.getOldPassword().equalsIgnoreCase(changePasswordRequest.getNewPassword())) {
			throw new PasswordChangePolicyException(AuthMessageConstant.NEW_PASSWORD_SIMILAR_TO_OLD);
		}
		if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getAgainNewPassword())) {
			throw new PasswordChangePolicyException(AuthMessageConstant.RE_ENTERED_PASSWORD_NOT_MATCH);
		}

		String email = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
				.getUsername();

		User user = userRepository.findByEmailId(email)
				.orElseThrow(() -> new UsernameNotFoundException(AuthMessageConstant.USER_NOT_FOUND));

		if (!passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())) {
			throw new WrongPasswordException(AuthMessageConstant.WRONG_PASSWORD);
		}
		user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
		userRepository.save(user);
	}

	private User mapToModel(User user, SignUpRequest signUpRequest) {
		return user.setFirstName(signUpRequest.getFirstName()).setLastName(signUpRequest.getLastName())
				.setEmailId(signUpRequest.getEmailId()).setMobileNumber(signUpRequest.getMobileNumber())
				.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
	}

	private Tokens generateTokens(UserDetails userDetails) {
		return new Tokens(jwtUtils.generateAccessToken(userDetails), jwtUtils.generateRefreshToken(userDetails));
	}

	private boolean doesTokenMatches(String refreshToken, String storedRefreshToken) {
		return refreshToken.equals(storedRefreshToken);
	}
}

package com.qms.auth.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.qms.auth.constant.RoleName;
import com.qms.auth.dto.Tokens;
import com.qms.auth.dto.request.ChangePasswordRequestDTO;
import com.qms.auth.dto.request.LoginRequestDTO;
import com.qms.auth.dto.request.RenewTokenRequestDTO;
import com.qms.auth.dto.request.SignUpRequestDTO;
import com.qms.auth.dto.response.LoginResponseDTO;
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
	public Long register(final SignUpRequestDTO signUpRequest) {
		if (userRepository.existsByEmailId(signUpRequest.getEmailId())) {
			throw new RuntimeException("Error: Username is already taken!"); // BAD request, throw custom exception
		}

		// TODO: how to use design pattern(like factory if have to choose from many
		// roles, i.e., if signuprequest also have role info)

		Role userRole = roleRepository.findByRoleName(RoleName.ATTENDEE)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found.")); // TODO: create custom formatted
																						// exception
		// TODO: use DTO <-> model mapper

		User user = new User();
		user.addRole(userRole);

		return userRepository.save(mapToModel(user, signUpRequest)).getId();
	}

	private User mapToModel(User user, SignUpRequestDTO signUpRequest) {
		return user.setFirstName(signUpRequest.getFirstName()).setLastName(signUpRequest.getLastName())
				.setMobileNumber(signUpRequest.getMobileNumber())
				.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
	}

	@Override
	public LoginResponseDTO login(final LoginRequestDTO loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmailId(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		final Tokens tokens = generateTokens(userDetails);

		redisCacheUtil.cacheValue(userDetails.getUsername(), tokens.getRefreshToken());

		List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());

		return new LoginResponseDTO(tokens.getAccessToken(), tokens.getRefreshToken(), userDetails.getUsername(),
				roles);
	}

	private Tokens generateTokens(UserDetails userDetails) {
		return new Tokens(jwtUtils.generateAccessToken(userDetails), jwtUtils.generateRefreshToken(userDetails));
	}

	@Override
	public Tokens renewTokens(final RenewTokenRequestDTO tokenRefreshRequest) {
		String refreshToken = tokenRefreshRequest.getRefreshToken();
		if (jwtUtils.isTokenValid(refreshToken)) {
			String username = jwtUtils.extractUsername(refreshToken);
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			// TODO: whether to use ?? -> use User user =
			// userRepository.findByEmailId(email).get(); // TODO: use isPresent()
			String storedRefreshToken = redisCacheUtil.getCachedValue(username);

			if (!doesTokenMatches(refreshToken, storedRefreshToken)) {
				throw new RuntimeException("Refresh token not exists."); // TODO: custom
			}

			// TODO: check here the security context on debugging

			final Tokens newTokens = generateTokens(userDetails);
			redisCacheUtil.cacheValue(username, newTokens.getRefreshToken());
			return newTokens;
		} else {
			throw new RuntimeException("Refresh token is not valid"); // TODO: throw custom exception inside the
																		// isTokenValid
		}
	}

	private boolean doesTokenMatches(String refreshToken, String storedRefreshToken) {
		return refreshToken.equals(storedRefreshToken);
	}

	@Override
	public void changePassword(ChangePasswordRequestDTO changePasswordRequest) {
		if (changePasswordRequest.getOldPassword().equalsIgnoreCase(changePasswordRequest.getNewPassword())) {
			throw new RuntimeException("New password cannot be similar to old password.");
		}
		if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getAgainNewPassword())) {
			throw new RuntimeException("Re-entered new password do not match."); // TODO: use custom Exception
		}

		String email = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
				.getUsername();
		Optional<User> userOpt = userRepository.findByEmailId(email);
		// TODO: whether to use instead ?? ->
		// this.userDetailsService.loadUserByUsername(email)
		if (!userOpt.isPresent()) {
			throw new RuntimeException("User not exists."); // TODO: custom
		}

		if (!passwordEncoder.matches(changePasswordRequest.getOldPassword(), userOpt.get().getPassword())) {
			throw new RuntimeException("Wrong password provided."); // TODO: use custom Exception
		}
		userOpt.get().setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
		userRepository.save(userOpt.get());
	}

}

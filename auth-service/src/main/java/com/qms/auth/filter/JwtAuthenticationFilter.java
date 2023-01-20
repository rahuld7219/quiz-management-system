package com.qms.auth.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qms.auth.util.JwtUtils;
import com.qms.common.dto.response.ErrorResponse;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtUtils jwtUtils;
	private final UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
			@NonNull FilterChain filterChain) throws ServletException, IOException {
		try {
			final String jwt = parseForJwt(request);

			if (jwt == null) { // in case some API not requires jwt, so we don't throw error here, we let it
								// pass for next thing
				filterChain.doFilter(request, response);
				return;
			}

			// NOTE: Refresh token not come to this point as we send refresh token in body
			// to renew the token

			// TODO: if storing accessToken then also check if the emailId of the
			// token have the same access token as stored in redis cache
			if (jwtUtils.isTokenValid(jwt)) {
				final String userEmail = this.jwtUtils.extractUsername(jwt);
				UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
						null, userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		} catch (Exception exception) {
			log.error("Cannot set user authentication: {}", exception);
			log.error("Error logging in: {}", exception.getMessage());
//			response.setHeader("error", exception.getMessage());
//			response.setStatus(HttpStatus.FORBIDDEN.value());
//			// response.sendError(FORBIDDEN.value());
//			Map<String, String> error = new HashMap<>();
//			error.put("error_message", exception.getMessage());
//			response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
//			new ObjectMapper().writeValue(response.getOutputStream(), error);

			response.setHeader("error", exception.getMessage());
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
			final ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setHttpStatus(HttpStatus.UNAUTHORIZED);
			errorResponse.setException(exception.getClass().getSimpleName());
			errorResponse.setMessage(exception.getMessage());
//			errorResponse.setResponseTime(LocalDateTime.now());
			new ObjectMapper().writeValue(response.getOutputStream(), errorResponse);
		}

		filterChain.doFilter(request, response);
	}

	private String parseForJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");

		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.substring(7);
		}

		return null;
	}

}

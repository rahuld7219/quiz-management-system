//package com.qms.auth.filter;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.util.MimeTypeUtils;
//import org.springframework.util.StringUtils;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.qms.auth.service.impl.UserDetailsServiceImpl;
//import com.qms.auth.util.JwtUtils;
//
//import lombok.extern.slf4j.Slf4j;
//
///**
// * TODO: explain purpose of this class
// * 
// * @author rahul
// *
// */
//@Slf4j
//public class CustomAuthorizationFilter extends OncePerRequestFilter {
//	@Autowired
//	private JwtUtils jwtUtils;
//
//	@Autowired
//	private UserDetailsServiceImpl userDetailsService;
//
//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//			throws ServletException, IOException {
//		if (request.getServletPath().equals("/api/v1/auth/login")
//				|| request.getServletPath().equals("/api/v1/auth/register")
//				|| request.getServletPath().equals("/api/v1/auth/refresh")) {
//			filterChain.doFilter(request, response);
//		} else {
//			try {
//				String jwt = parseJwt(request);
//				if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
//					String username = jwtUtils.getUserNameFromJwtToken(jwt);
//
//					UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//							userDetails, null, userDetails.getAuthorities());
//					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // TODO:
//																											// explain
//					SecurityContextHolder.getContext().setAuthentication(authentication);
//				} else {
//					throw new RuntimeException("Authentication token is not valid.");
//				}
//				// TODO: throw custom standard response error
//			} catch (Exception exception) {
//				log.error("Cannot set user authentication: {}", exception);
//				log.error("Error logging in: {}", exception.getMessage());
//				response.setHeader("error", exception.getMessage());
//				response.setStatus(HttpStatus.FORBIDDEN.value());
//				// response.sendError(FORBIDDEN.value());
//				Map<String, String> error = new HashMap<>();
//				error.put("error_message", exception.getMessage());
//				response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
//				new ObjectMapper().writeValue(response.getOutputStream(), error);
//			}
//
//			filterChain.doFilter(request, response);
//		}
//	}
//
//	private String parseJwt(HttpServletRequest request) {
//		String headerAuth = request.getHeader("Authorization");
//
//		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
//			return headerAuth.substring(7, headerAuth.length());
//		}
//
//		return null;
//	}
//}
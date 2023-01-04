package com.qms.auth.util;

import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.qms.auth.model.Role;
import com.qms.auth.model.User;
import com.qms.auth.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtUtils {

	@Value("${qms.app.jwtSecret}")
	private String jwtSecret;

	@Value("${qms.app.jwtAccessExpirationTime}")
	private int jwtAccessExpiration;

	@Value("${qms.app.jwtRefreshExpirationTime}")
	private int jwtRefreshExpiration;

	@Autowired
	private UserRepository userRepository;

	public String generateJwtAccessToken(String username) {
		
//		SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
//		String secretString = Encoders.BASE64.encode(key.getEncoded());
		
		Claims claims = Jwts.claims();
		populate(claims, username, jwtAccessExpiration);
		User user = userRepository.findByEmailId(username).orElseThrow(() -> new RuntimeException("User not exist.")); // TODO: throw custom exception
		return Jwts.builder()
				.setClaims(claims)
				.claim("role", user.getRoles().stream().map(Role::getRoleName).collect(Collectors.toList()))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}

	private void populate(Claims claims, String username, int expirationTime) {
		claims
		.setSubject(username)
		.setIssuedAt(new Date())
		.setExpiration(new Date((new Date()).getTime() + expirationTime));
	}

	public String generateJwtRefreshToken(String username) {
		Claims claims = Jwts.claims();
		populate(claims, username, jwtRefreshExpiration);
		return Jwts.builder()
				.setClaims(claims)
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}

	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

	// TODO: Throw a common Invalid Token Exception
	public boolean validateJwtToken(String token) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
			return true;
		} catch (SignatureException e) {
			log.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			log.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			log.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			log.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			log.error("JWT claims string is empty: {}", e.getMessage());
		}

		return false;
	}
}

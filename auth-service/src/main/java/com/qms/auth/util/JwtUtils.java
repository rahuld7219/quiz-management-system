package com.qms.auth.util;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.qms.auth.constant.AuthMessageConstant;
import com.qms.auth.exception.custom.InvalidJWTException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtUtils {

	@Value("${qms.app.jwtSecret}")
	private String jwtSecret;

	@Value("${qms.app.jwtAccessExpirationTime}")
	private int jwtAccessExpiration;

	@Value("${qms.app.jwtRefreshExpirationTime}")
	private int jwtRefreshExpiration;

	/**
	 * 
	 * @param userDetails
	 * @return
	 */
	public String generateAccessToken(UserDetails userDetails) {
		Map<String, Object> extraClaim = new HashMap<>();
		extraClaim.put(AuthMessageConstant.ROLES, userDetails.getAuthorities());
		return generateToken(extraClaim, userDetails.getUsername(), jwtAccessExpiration);
	}

	/**
	 * 
	 * @param userDetails
	 * @return
	 */
	public String generateRefreshToken(UserDetails userDetails) {
		return generateToken(new HashMap<>(), userDetails.getUsername(), jwtRefreshExpiration);
	}

	/**
	 * 
	 * @param extraClaims
	 * @param userDetails
	 * @return
	 */
	public String generateToken(Map<String, Object> extraClaims, String username, int expirationTime) {
		return Jwts.builder().setClaims(extraClaims).setSubject(username)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expirationTime)).signWith(getSignKey()).compact();
	}

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public boolean isTokenValid(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			log.error("Invalid JWT: {}", e.getMessage());
			throw new InvalidJWTException(AuthMessageConstant.INVALID_JWT + e.getMessage());
		}

//		catch (SignatureException e) {
//			log.error("Invalid JWT signature: {}", e.getMessage());
//		} 
//			catch (MalformedJwtException e) {
//			log.error("Invalid JWT token: {}", e.getMessage());
//		} catch (ExpiredJwtException e) {
//			log.error("JWT token is expired: {}", e.getMessage());
//		} catch (UnsupportedJwtException e) {
//			log.error("JWT token is unsupported: {}", e.getMessage());
//		} catch (IllegalArgumentException e) {
//			log.error("JWT claims string is empty: {}", e.getMessage());
//		}

	}

	private Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

//	private Date extractExpiration(String token) {
//		return extractClaim(token, Claims::getExpiration);
//	}

//	private boolean isTokenExpired(String token) {
//		return extractExpiration(token).before(new Date());
//	}
}

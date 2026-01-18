package com.realestatemanagement.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.realestatemanagement.entity.User;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration-ms}")
	private long expirationMs;

	private Key key() {
		// HS256 requires 256-bit key (32+ chars recommended)
		return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
	}

	/**
	 * Issue a token from Spring Security principal (recommended). Subject = email
	 * (userDetails.getUsername()).
	 */
	public String generateToken(UserDetails userDetails) {
		return generateTokenFromEmail(userDetails.getUsername());
	}

	/**
	 * Issue a token directly from your JPA entity. Subject = user.getEmail().
	 */
	public String generateToken(User user) {
		return generateTokenFromEmail(user.getEmail());
	}

	/**
	 * Small helper to issue a token given an email.
	 */
	public String generateTokenFromEmail(String email) {
		Date now = new Date();
		Date exp = new Date(now.getTime() + expirationMs);

		return Jwts.builder().setSubject(email) // <— store email as subject
				.setIssuedAt(now).setExpiration(exp).signWith(key(), SignatureAlgorithm.HS256).compact();
	}

	/**
	 * Validate signature + expiration.
	 */
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			return false;
		}
	}

	/**
	 * Extract email (subject) from token.
	 */
	public String getEmailFromToken(String token) {
		return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody().getSubject();
	}
}

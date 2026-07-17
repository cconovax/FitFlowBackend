package com.conovax.fitflow.infrastructure.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtUtils {

	private final SecretKey key;
	private final long jwtExpirationMs;
	private static final String AUTHORITIES_CLAIM = "authorities";
	private static final String GYM_ID_CLAIM = "gymId";
	private static final String USER_GYM_ID_CLAIM = "userGymId";

	public JwtUtils(
			@Value("${jwt.secret}") String jwtSecret,
			@Value("${jwt.expiration}") long jwtExpirationMs
	) {
		this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
		this.jwtExpirationMs = jwtExpirationMs;
	}

	public String generateJwtToken(Authentication authentication) {
		return generateJwtToken(authentication, null, null);
	}

	public String generateJwtToken(Authentication authentication, Long gymId, Long userGymId) {
		String username = authentication.getName();
		String authorities = authentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));

		Date now = new Date();
		Date expiry = new Date(now.getTime() + jwtExpirationMs);

		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(now)
				.setExpiration(expiry)
				.claim(AUTHORITIES_CLAIM, authorities)
				.claim(GYM_ID_CLAIM, gymId)
				.claim(USER_GYM_ID_CLAIM, userGymId)
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
	}

	public String getUserNameFromJwtToken(String token) {
		return parseClaims(token).getSubject();
	}

	public List<GrantedAuthority> getAuthoritiesFromJwtToken(String token) {
		Claims claims = parseClaims(token);

		Object raw = claims.get(AUTHORITIES_CLAIM);
		if (raw == null) {
			return List.of();
		}

		String csv = String.valueOf(raw);
		if (csv.isBlank()) {
			return List.of();
		}

		return Arrays.stream(csv.split(","))
				.map(String::trim)
				.filter(s -> !s.isBlank())
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
	}

	public Long getGymIdFromJwtToken(String token) {
		return getLongClaim(token, GYM_ID_CLAIM);
	}

	public Long getUserGymIdFromJwtToken(String token) {
		return getLongClaim(token, USER_GYM_ID_CLAIM);
	}

	public boolean validateJwtToken(String authToken) {
		try {
			parseClaims(authToken);
			return true;
		} catch (SecurityException e) {
			log.warn("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			log.warn("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			log.warn("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			log.warn("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			log.warn("JWT claims string is empty: {}", e.getMessage());
		}
		return false;
	}

	private Claims parseClaims(String token) {
		return Jwts.parser()
				.verifyWith(key)
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}

	private Long getLongClaim(String token, String claimName) {
		Object raw = parseClaims(token).get(claimName);
		if (raw instanceof Number number) {
			return number.longValue();
		}
		if (raw instanceof String value && !value.isBlank()) {
			return Long.parseLong(value);
		}
		return null;
	}
}

package com.example.users.config;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil {
	public static final String TOKEN_HEADER = "Authorization";
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final long EXPIRATION = 86400000L; // 1 day (millisecond)
	public static final long EXPIRATION_REMEMBER = 604800000L; // 7 days
	private static final String ISSUER = "FSD";
	private static final String ROLE = "FSDRole";
	@Value("${jwt.secret}")
	private String secret;

	// retrieve username from jwt token
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	// retrieve expiration date from jwt token
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getTokenBody(token);
		return claimsResolver.apply(claims);
	}

	// for retrieving any information from token we will need the secret key
	private Claims getTokenBody(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	// check if the token has expired
	public Boolean isTokenExpired(String token) {
		return getExpirationDateFromToken(token).before(new Date());
	}

	public Set<String> getUserRole(String token) {
		List<GrantedAuthority> userAuthorities = (List<GrantedAuthority>) getTokenBody(token).get(ROLE);
		return AuthorityUtils.authorityListToSet(userAuthorities);
	}

	public String generateToken(UserDetails userDetails, boolean isRemember) {
		Map<String, Object> claims = new HashMap<>();
		claims.put(ROLE, userDetails.getAuthorities());
		long expiration = isRemember ? EXPIRATION_REMEMBER : EXPIRATION;
		return doGenerateToken(claims, userDetails.getUsername(), expiration);
	}

	public String generateToken(Authentication authentication, boolean isRemember) {
		Map<String, Object> claims = new HashMap<>();
		claims.put(ROLE, authentication.getAuthorities());
		long expiration = isRemember ? EXPIRATION_REMEMBER : EXPIRATION;
		return doGenerateToken(claims, authentication.getName(), expiration);
	}

	private String doGenerateToken(Map<String, Object> claims, String subject, long expiration) {
		return Jwts.builder().signWith(SignatureAlgorithm.HS512, secret).setClaims(claims).setIssuer(ISSUER)
				.setSubject(subject).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + expiration)).compact();
	}

	// validate token
	public Boolean validateToken(String token, UserDetails userDetails) {
		User user = (User) userDetails;
		final String username = getUsernameFromToken(token);
		return (username.equals(user.getUsername()) && !isTokenExpired(token));
	}

}

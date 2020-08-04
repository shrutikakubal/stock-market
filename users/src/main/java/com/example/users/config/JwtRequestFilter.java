package com.example.users.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	JwtTokenUtil jwtTokenUtil;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		String requestTokenHeader = request.getHeader(JwtTokenUtil.TOKEN_HEADER);

		String username = null;
		String jwtToken = null;
		// JWT Token is in the form "Bearer token". Remove Bearer word and get
		// only the Token
		if (requestTokenHeader != null && requestTokenHeader.startsWith(JwtTokenUtil.TOKEN_PREFIX)) {
			jwtToken = requestTokenHeader.substring(7);
			
		} else {
			logger.warn("JWT Token does not begin with Bearer");
			jwtToken = request.getParameter("jwtToken");
			
			if(jwtToken==null) {
				chain.doFilter(request, response);
				return;
			}
		}
		
		try {
			username = jwtTokenUtil.getUsernameFromToken(jwtToken);
			// Once we get the token validate it.
			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

				UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

				if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {

					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					usernamePasswordAuthenticationToken
							.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
			}
		} catch (IllegalArgumentException e) {
			logger.error("Unable to get JWT Token");
		} catch (ExpiredJwtException e) {
			logger.error("JWT Token has expired");
		}
		chain.doFilter(request, response);
	}

}

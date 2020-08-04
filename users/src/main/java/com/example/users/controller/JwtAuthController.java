package com.example.users.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.users.config.JwtTokenUtil;
import com.example.users.model.JwtRequest;
import com.example.users.model.JwtResponse;
import com.example.users.model.UserDtls;

@RestController
@CrossOrigin
@RequestMapping("/authenticate")
public class JwtAuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService userDetailsService;

	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	public ResponseEntity<JwtResponse> signIn(@RequestBody JwtRequest authenticationRequest)
			throws Exception {

		SecurityContextHolder.getContext().setAuthentication(
				authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword()));

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails, false);
		UserDtls userDtls = new UserDtls();
		userDtls.setUsername(userDetails.getUsername());
		Set<GrantedAuthority> authorities = (Set<GrantedAuthority>) userDetails.getAuthorities();
		userDtls.setRole(authorities.toArray()[0].toString());

		return ResponseEntity.ok(new JwtResponse(token, userDtls));
	}
	
	@RequestMapping(value="/admin", method=RequestMethod.GET)
	  public ResponseEntity<String> admin() throws Exception {
	    return ResponseEntity.ok().body("Admin authenticated");
	  }
	
	@RequestMapping(value="/authenticated", method=RequestMethod.GET)
	  public ResponseEntity<String> userAuth() throws Exception {
	    return ResponseEntity.ok().body("user authenticated");
	  }

	private Authentication authenticate(String username, String password) throws Exception {
		try {
			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}

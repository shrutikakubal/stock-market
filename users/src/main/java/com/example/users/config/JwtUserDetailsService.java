package com.example.users.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.users.entity.UserEntity;
import com.example.users.service.UserService;

@Service
public class JwtUserDetailsService implements UserDetailsService{
	
	@Autowired
	UserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		UserEntity userEntity = userService.getUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("No user with username "+username));
		String password = userEntity.getPassword();
	    String role = userEntity.getRole();
	    Boolean userDisabled = false;
	    if(!userEntity.getConfirmed().equalsIgnoreCase("Y"))
	    	userDisabled=true;
	    return User.withUsername(username).password(password).disabled(userDisabled).roles(role).build();
	}
	

}

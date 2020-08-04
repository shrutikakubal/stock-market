package com.example.users.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.users.entity.UserEntity;
import com.example.users.model.UserDtls;
import com.example.users.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	public UserController(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userService = userService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public void signUp(@RequestBody UserDtls userDtls) throws Exception {
		UserEntity userEntity = new ModelMapper().map(userDtls, UserEntity.class);
		if(!userService.getUserByUsername(userDtls.getUsername()).isPresent()) {
		userEntity.setPassword(bCryptPasswordEncoder.encode(userEntity.getPassword()));
		userService.insertUpdateUser(userEntity);}
		else {
			throw new Exception("user already exists with username "+userDtls.getUsername());
		}
	}

	@RequestMapping(value = "/profile", method = RequestMethod.POST)
	public void updateProfile(@RequestBody UserDtls userDtls) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentUsername = authentication.getName();
		if (userDtls.getUsername().equalsIgnoreCase(currentUsername)) {
			UserEntity userEntity = userService.getUserByUsername(currentUsername).get();
			new ModelMapper().map(userDtls, userEntity);
			if (!(userDtls.getPassword() == null || userDtls.getPassword().equalsIgnoreCase(""))) {
				userEntity.setPassword(bCryptPasswordEncoder.encode(userDtls.getPassword()));
			}
			userService.insertUpdateUser(userEntity);
		}

	}

	@RequestMapping(value = "/confirm/{username}", method = RequestMethod.GET)
	public void confirmUser(@PathVariable("username") String username) {
		userService.setUserConfirmed(username, "Y");
	}


}

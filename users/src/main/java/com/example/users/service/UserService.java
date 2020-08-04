package com.example.users.service;

import java.util.Optional;

import com.example.users.entity.UserEntity;

public interface UserService {
	
	Optional<UserEntity> getUserByUsername(String username);
	Optional<UserEntity> setUserConfirmed(String username, String confirmed);
	Optional<UserEntity> getFirstByConfirmed(String confirmed);
	Iterable<UserEntity> getAllUsers();
	Optional<UserEntity> insertUpdateUser(UserEntity userEntity);
}

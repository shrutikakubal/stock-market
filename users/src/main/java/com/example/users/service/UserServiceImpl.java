package com.example.users.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.example.users.dao.UserDao;
import com.example.users.entity.UserEntity;

@Service
@EnableTransactionManagement
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;

	@Override
	@Transactional
	public Optional<UserEntity> getUserByUsername(String username) {

		return userDao.findByUsername(username);
	}

	@Override
	@Transactional
	public Optional<UserEntity> setUserConfirmed(String username, String confirmed) {

		return userDao.setUserConfirmed(username, confirmed);
	}

	@Override
	@Transactional
	public Optional<UserEntity> getFirstByConfirmed(String confirmed) {

		return userDao.findFirstByConfirmed(confirmed);
	}

	@Override
	@Transactional
	public Iterable<UserEntity> getAllUsers() {

		return userDao.findAll();
	}

	@Override
	@Transactional
	public Optional<UserEntity> insertUpdateUser(UserEntity userEntity) {

		return Optional.of(userDao.save(userEntity));
	}

}

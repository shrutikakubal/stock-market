package com.example.users.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.users.entity.UserEntity;

@Repository
public interface UserDao extends JpaRepository<UserEntity, Integer> {
	
	Optional<UserEntity> findByUsername(String username);
	Optional<UserEntity> findFirstByConfirmed(String confirmed);
	
	@Transactional
	@Modifying()
	@Query("update UserEntity u set u.confirmed=?2 where u.username=?1")
	Optional<UserEntity> setUserConfirmed(String username, String confirmed);
}

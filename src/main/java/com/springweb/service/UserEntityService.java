package com.springweb.service;

import java.util.Optional;

import com.springweb.model.UserEntity;

public interface UserEntityService {
	public Optional<UserEntity> findByUsername(String username);
	
	public Optional<UserEntity> findByEmail(String email);
	
	public void createNewUserEntity(UserEntity userEntity);
}

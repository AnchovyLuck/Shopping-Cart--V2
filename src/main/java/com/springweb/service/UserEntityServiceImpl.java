package com.springweb.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springweb.model.UserEntity;
import com.springweb.repository.UserEntityRepository;

@Service
public class UserEntityServiceImpl implements UserEntityService {
	@Autowired
	private UserEntityRepository userEntityRepository;
	
	@Autowired
	private PasswordEncoder pwEncoder;
	
	@Override
	public Optional<UserEntity> findByUsername(String username) {
		return userEntityRepository.findByUsername(username);
	}

	@Override
	public Optional<UserEntity> findByEmail(String email) {
		return userEntityRepository.findByEmail(email);
	}

	@Override
	public void createNewUserEntity(UserEntity userEntity) {
		userEntity.setPassword(pwEncoder.encode(userEntity.getPassword()));
		userEntityRepository.save(userEntity);
	}

}

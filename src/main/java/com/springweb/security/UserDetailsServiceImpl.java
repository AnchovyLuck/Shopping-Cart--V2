package com.springweb.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springweb.model.UserEntity;
import com.springweb.repository.UserEntityRepository;

import jakarta.transaction.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserEntityRepository userEntityRepository;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = userEntityRepository.findByUsername(username)
								.orElseThrow(() -> new UsernameNotFoundException("Email " + username + "not found"));
		return new User(userEntity.getUsername(), userEntity.getPassword(), getAuthorities(userEntity));
	}
	
	private static Collection<? extends GrantedAuthority> getAuthorities(UserEntity userEntity) {
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		userEntity.getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role.getRole()));
		});
		return authorities;
	}
}

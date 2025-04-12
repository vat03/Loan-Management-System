package com.aurionpro.lms.security;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.aurionpro.lms.entity.User;
import com.aurionpro.lms.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsernameAndIsDeletedFalse(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

		return new CustomUserDetails(user.getId(), user.getUsername(), user.getPassword(),
				Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getRoleName())));
	}
}
package com.qms.auth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.qms.auth.model.User;
import com.qms.auth.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmailId(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with email Id: " + username)); // TODO:
																												// put
																												// msg
																												// constant
		return UserDetailsImpl.build(user);
	}
}

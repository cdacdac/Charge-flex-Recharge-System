package com.Charge_Flex.services.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Charge_Flex.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetailsService userDetailsService() {
		return new UserDetailsService() {
			
			@Override
			public UserDetails loadUserByUsername(String username) {
				return userRepository.findFirstByEmail(username)
						.orElseThrow(() ->new UsernameNotFoundException("User not found !"));
			}

		};
	}
}

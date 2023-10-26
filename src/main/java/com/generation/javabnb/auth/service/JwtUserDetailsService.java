package com.generation.javabnb.auth.service;


import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.generation.javabnb.auth.model.UserInDb;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{
		if(repo.findById(username).isPresent())
		{
			UserInDb user= repo.findById(username).get();
			return new User(user.getUsername(), user.getPassword(),
					new ArrayList<>());
		}
		throw new UsernameNotFoundException("User not found with username: " + username);
	}

}
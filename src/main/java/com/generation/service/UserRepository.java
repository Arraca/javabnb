package com.generation.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
public class UserRepository 
{
	
	private Map<String,User> users = new HashMap<String, User>();
	
	
	public UserRepository()
	{
		users.put("stefano", new User("stefano", "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",
				new ArrayList<>()));
		users.put("irene", new User("irene", "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",
				new ArrayList<>()));
	}
	
	User getUserByUsername(String username)
	{
		if(users.containsKey(username))
		{
			User old = users.get(username);
			return new User(old.getUsername(),old.getPassword(),old.getAuthorities());
		}
		
		return null;
	}
}

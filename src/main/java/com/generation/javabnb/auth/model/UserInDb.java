package com.generation.javabnb.auth.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="user")
@Getter
@Setter
public class UserInDb 
{
	@Id
	private String username;
	
	private String password;
	
	public boolean isUsernameValid()
	{
		if(username==null || username.isBlank())
			return false;
		String regex = "^[a-zA-Z0-9_!#$%&amp;'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

		Pattern pattern = Pattern.compile(regex);
		
		Matcher matcher = pattern.matcher(username);
		return matcher.matches();
		
	}

	
}

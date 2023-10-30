package com.generation.javabnb.model.dto.user;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.generation.javabnb.model.entities.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class GenericUserDTO 
{
	protected Integer id;
	
	protected String username;
	protected String password;
	protected String type;
	
	protected String name;
	protected String surname;
	
	public GenericUserDTO() 
	{
		// TODO Auto-generated constructor stub
	}
	
	public GenericUserDTO(User user) 
	{
		this.id=user.getId();
		this.username=user.getUsername();
		this.password=user.getPassword();
		this.type=user.getType();
		this.name=user.getName();
		this.surname=user.getSurname();
	}
	

	
	public abstract User convertToUser();

	public boolean isEmailValid()
	{
		if(username==null || username.isBlank())
			return false;
		String regex = "^[a-zA-Z0-9_!#$%&amp;'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

		Pattern pattern = Pattern.compile(regex);
		
		Matcher matcher = pattern.matcher(username);
		return matcher.matches();
		
	}
	
	public boolean isValid()
	{
		return 	username != null 		&& isEmailValid() 		&& 
				password != null 	&& !password.isBlank() 	&&
				type != null 		&& !type.isBlank() 		&&
				name != null	 	&& !name.isBlank() 		&&
				surname != null 	&& !surname.isBlank()	;
	}



}

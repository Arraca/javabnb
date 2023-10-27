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
	private Integer id;
	
	private String email;
	private String password;
	private String type;
	
	private String name;
	private String surname;
	
	public GenericUserDTO() 
	{
		// TODO Auto-generated constructor stub
	}
	
	public GenericUserDTO(User user) 
	{
		this.id=user.getId();
		this.email=user.getEmail();
		this.password=user.getPassword();
		this.type=user.getType();
		this.name=user.getName();
		this.surname=user.getSurname();
	}
	

	
	public abstract User convertToUser();

	public boolean isEmailValid()
	{
		if(email==null || email.isBlank())
			return false;
		String regex = "^[a-zA-Z0-9_!#$%&amp;'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

		Pattern pattern = Pattern.compile(regex);
		
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
		
	}



}

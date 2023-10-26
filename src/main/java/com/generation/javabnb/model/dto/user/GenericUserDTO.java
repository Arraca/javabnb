package com.generation.javabnb.model.dto.user;

import java.time.LocalDate;

import com.generation.javabnb.model.entities.User;


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



}

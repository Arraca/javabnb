package com.generation.javabnb.model.dto.user;

import com.generation.javabnb.model.entities.User;

public class CustomerDTOnoList extends GenericUserDTO
{
	
	private Integer id;
	
	private String username;
	private String password;
	private String type;
	
	private String name;
	private String surname;

	public CustomerDTOnoList() {};
	
	public CustomerDTOnoList(User user)
	{
		super(user);
	}

	@Override
	public User convertToUser() 
	{
		User res = new User();
		res.setId(id);
		res.setUsername(username);
		res.setPassword(password);
		res.setType(type);
		res.setName(name);
		res.setSurname(surname);

		return res;
	}
	
}

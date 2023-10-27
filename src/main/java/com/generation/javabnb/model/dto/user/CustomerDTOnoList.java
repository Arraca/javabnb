package com.generation.javabnb.model.dto.user;

import java.util.List;

import com.generation.javabnb.model.dto.roombooking.RoomBookingDTO;
import com.generation.javabnb.model.entities.User;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomerDTOnoList extends GenericUserDTO
{
	
	private Integer id;
	
	private String email;
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
		res.setEmail(email);
		res.setPassword(password);
		res.setType(type);
		res.setName(name);
		res.setSurname(surname);

		return res;
	}
	
}

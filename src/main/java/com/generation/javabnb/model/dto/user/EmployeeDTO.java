package com.generation.javabnb.model.dto.user;

import java.time.LocalDate;
import java.util.List;

import com.generation.javabnb.model.dto.roombooking.RoomBookingDTO;
import com.generation.javabnb.model.entities.User;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmployeeDTO extends GenericUserDTO 
{

	private Integer id;
	
	private String username;
	private String password;
	private String type;
	
	private String name;
	private String surname;
	private LocalDate dob;
	private String img_url;

	
	public EmployeeDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public EmployeeDTO(User user)
	{
		super(user);
		this.dob=user.getDob();
		this.img_url=user.getImg_url();

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
		res.setDob(dob);
		res.setImg_url(img_url);
		return res;
	}

}

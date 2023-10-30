package com.generation.javabnb.model.entities;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User 
{
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Id
	private String username;
	private String password;
	private String type;
	
	private String name;
	private String surname;
	private LocalDate dob;
	private String img_url;
	
	@OneToMany(mappedBy = "customer")
	private List<RoomBooking> bookings;


	
//	public User() {
//		// TODO Auto-generated constructor stub
//	}
//	
//	public User(Integer id, String email, String password, String type, String name, String surname) 
//	{
//		this.id = id;
//		this.email = email;
//		this.password = password;
//		this.type = type;
//		this.name = name;
//		this.surname = surname;
//	}
//	
//	public User(Integer id, String email, String password, String type, String name, String surname, LocalDate dob, String img_url) 
//	{
//		this.id = id;
//		this.email = email;
//		this.password = password;
//		this.type = type;
//		this.name = name;
//		this.surname = surname;
//		this.dob = dob;
//		this.img_url = img_url;
//	}


}

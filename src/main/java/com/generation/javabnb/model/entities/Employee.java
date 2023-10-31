package com.generation.javabnb.model.entities;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Employee 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;
	private String surname;
	private LocalDate dob;
	private String img_url;
	private String username;
	
	//COSTRUTTORI
//	public Employee() {}
//	
//	public Employee(Integer id, String email, String password, String type, String name, String surname, LocalDate dob, String img_url) 
//	{
//		super(id, email, password, type, name, surname, dob, img_url);
//	}

	
}

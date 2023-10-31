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
public class Customer 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
		
	private String name;
	private String surname;
	private LocalDate dob;
	private String username;
	
	@OneToMany(mappedBy = "customer")
	private List<RoomBooking> bookings;


	

}

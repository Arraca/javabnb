package com.generation.javabnb.model.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Room 
{
	@Id
<<<<<<< HEAD
	@GeneratedValue(strategy=GenerationType.IDENTITY)
=======
	@GeneratedValue(strategy = GenerationType.IDENTITY)
>>>>>>> 91c9363ac771d4636fe9748f6403db350bcf8902
	private Integer id;
	private String name;
	private Integer capacity;
	private Double base_price;
	private String notes;
	private String img_url;
	@OneToMany(mappedBy = "room")
	private List<RoomBooking> bookings;
}

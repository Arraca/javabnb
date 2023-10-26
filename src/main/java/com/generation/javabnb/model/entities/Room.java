package com.generation.javabnb.model.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Room 
{
	private Integer id;
	private String name;
	private Integer capacity;
	private Double base_price;
	private String notes;
	private String img_url;
	@OneToMany(mappedBy = "room")
	private List<RoomBooking> bookings;
}

package com.generation.javabnb.model.entities;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class RoomBooking
{
	private Integer id;
	private LocalDate checkIn;
	private LocalDate checkOut;
	private Double modPrice;
	private Double totalPrice;
	private String email;
	private Boolean saved;
	private Season season;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "room_id")
	private Room room;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "email_user")
	private User customer;
}

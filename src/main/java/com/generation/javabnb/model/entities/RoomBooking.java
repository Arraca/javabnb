package com.generation.javabnb.model.entities;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class RoomBooking
{
	@Id
<<<<<<< HEAD
	@GeneratedValue(strategy=GenerationType.IDENTITY)
=======
	@GeneratedValue(strategy = GenerationType.IDENTITY)
>>>>>>> 91c9363ac771d4636fe9748f6403db350bcf8902
	private Integer id;
	private LocalDate checkIn;
	private LocalDate checkOut;
	private Double modPrice;
	private Double totalPrice;
//	private String email;
	private Boolean saved;
	//private Season season;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "room_id")
	private Room room;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "email_user")
	private User customer;
}

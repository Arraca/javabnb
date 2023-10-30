package com.generation.javabnb.model.dto.user;

import java.util.ArrayList;
import java.util.List;

import com.generation.javabnb.model.dto.roombooking.RoomBookingDTO;
import com.generation.javabnb.model.entities.RoomBooking;
import com.generation.javabnb.model.entities.User;

import javassist.expr.NewArray;

public class CustomerDTO extends GenericUserDTO 
{
	private Integer id;
	
	private String username;
	private String password;
	private String type;
	
	private String name;
	private String surname;
	
	private List<RoomBookingDTO> bookings;

	public CustomerDTO() {};
	
	public CustomerDTO(User user)
	{
		super(user);
		this.bookings = user.getBookings().stream().map(booking -> new RoomBookingDTO(booking)).toList();
		
//		List<RoomBookingDTO> listaBookings = new ArrayList<RoomBookingDTO>();
//		for(RoomBooking roomB : user.getBookings())
//			listaBookings.add(new RoomBookingDTO(roomB));
//		this.bookings = listaBookings;
//
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
		res.setBookings(this.bookings.stream().map(bookingDTO -> bookingDTO.convertToRoomBooking()).toList());

//		List<RoomBooking> listaBookings = new ArrayList<RoomBooking>();
//		for(RoomBookingDTO dto : bookings)
//			listaBookings.add(dto.convertToRoomBooking());
//		res.setBookings(listaBookings);
		return res;
	}

}

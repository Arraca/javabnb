package com.generation.javabnb.model.dto.user;

import java.util.ArrayList;
import java.util.List;

import com.generation.javabnb.model.dto.roombooking.RoomBookingDTO;
import com.generation.javabnb.model.entities.RoomBooking;
import com.generation.javabnb.model.entities.User;

import javassist.expr.NewArray;
import lombok.Getter;
import lombok.Setter;

<<<<<<< HEAD
@Setter
@Getter
=======
@Getter
@Setter
>>>>>>> 91c9363ac771d4636fe9748f6403db350bcf8902
public class CustomerDTO extends GenericUserDTO 
{
	
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
		res.setBookings(this.bookings.stream().map(bookingDTO -> bookingDTO.convertToRoomBooking()).toList());

//		List<RoomBooking> listaBookings = new ArrayList<RoomBooking>();
//		for(RoomBookingDTO dto : bookings)
//			listaBookings.add(dto.convertToRoomBooking());
//		res.setBookings(listaBookings);
		return res;
	}

}

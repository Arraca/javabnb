package com.generation.javabnb.model.dto.customer;

import java.util.ArrayList;
import java.util.List;

import com.generation.javabnb.model.dto.roombooking.RoomBookingDTO;
import com.generation.javabnb.model.dto.roombooking.RoomBookingDTOnoCustomer;
import com.generation.javabnb.model.entities.RoomBooking;
import com.generation.javabnb.model.entities.Customer;

import javassist.expr.NewArray;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter

public class CustomerDTO extends GenericUserDTO 

{
	
	private List<RoomBookingDTOnoCustomer> bookings;

	public CustomerDTO() {};
	
	public CustomerDTO(Customer customer)
	{
		super(customer);
		this.bookings = customer.getBookings().stream().map(booking -> new RoomBookingDTOnoCustomer(booking)).toList();
		
//		List<RoomBookingDTO> listaBookings = new ArrayList<RoomBookingDTO>();
//		for(RoomBooking roomB : user.getBookings())
//			listaBookings.add(new RoomBookingDTO(roomB));
//		this.bookings = listaBookings;
//
	}

	@Override
	public Customer convertToCustomer() 
	{
		Customer res = new Customer();
		res.setId(id);
		res.setName(name);
		res.setSurname(surname);
		res.setDob(dob);
		res.setUsername(username);
		res.setBookings(this.bookings.stream().map(bookingDTO -> bookingDTO.convertToRoomBooking()).toList());

//		List<RoomBooking> listaBookings = new ArrayList<RoomBooking>();
//		for(RoomBookingDTO dto : bookings)
//			listaBookings.add(dto.convertToRoomBooking());
//		res.setBookings(listaBookings);
		return res;
	}

}

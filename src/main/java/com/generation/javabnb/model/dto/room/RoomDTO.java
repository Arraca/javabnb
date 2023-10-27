package com.generation.javabnb.model.dto.room;

import java.util.List;

import com.generation.javabnb.model.dto.roombooking.RoomBookingDTO;
import com.generation.javabnb.model.entities.Room;
import com.generation.javabnb.model.entities.RoomBooking;

public class RoomDTO extends GenericRoomDTO 
{

	private Integer id;
	private String name;
	private Integer capacity;
	private Double base_price;
	private String notes;
	private String img_url;
	private List<RoomBookingDTO> bookings;

	

	public RoomDTO(Room room) 
	{
		super(room);
		this.bookings = room.getBookings().stream().map(booking -> new RoomBookingDTO(booking)).toList();
	}



	@Override
	public Room convertToRoom() 
	{
		Room res = new Room();
		res.setId(id);
		res.setName(name);
		res.setCapacity(capacity);
		res.setBase_price(base_price);
		res.setNotes(notes);
		res.setImg_url(img_url);
		res.setBookings(bookings.stream().map(bookingDTO -> bookingDTO.convertToRoomBooking()).toList());
		return res;
	}

}

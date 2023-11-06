package com.generation.javabnb.model.dto.room;

import java.util.List;

import com.generation.javabnb.model.dto.roombooking.RoomBookingDTO;
import com.generation.javabnb.model.dto.roomimages.RoomImageDTO;
import com.generation.javabnb.model.entities.Room;
import com.generation.javabnb.model.entities.RoomBooking;
import com.generation.javabnb.model.entities.RoomImage;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class RoomDTO extends GenericRoomDTO 
{

	private List<RoomBookingDTO> bookings;
//	private List<RoomImageDTO> images;

	public RoomDTO(){};

	public RoomDTO(Room room) 
	{
		super(room);
		this.bookings = room.getBookings().stream().map(booking -> new RoomBookingDTO(booking)).toList();
//		this.images = room.getImages().stream().map(image -> new RoomImageDTO(image)).toList();
	}



	@Override
	public Room convertToRoom() 
	{
		Room res = new Room();
		res.setId(super.getId());
		res.setName(super.getName());
		res.setCapacity(super.getCapacity());
		res.setBase_price(super.getBase_price());
		res.setNotes(super.getNotes());
		res.setImg_url(super.getImg_url());
		res.setBookings(bookings.stream().map(bookingDTO -> bookingDTO.convertToRoomBooking()).toList());
//		res.setImages(images.stream().map(imageDTO -> imageDTO.convertToRoomImage()).toList());
		return res;
	}

}

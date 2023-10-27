package com.generation.javabnb.model.dto.room;

import java.util.List;

import com.generation.javabnb.model.dto.roombooking.RoomBookingDTO;
import com.generation.javabnb.model.entities.Room;

public class RoomDTOnoList extends GenericRoomDTO
{
	private Integer id;
	private String name;
	private Integer capacity;
	private Double base_price;
	private String notes;
	private String img_url;

	

	public RoomDTOnoList(Room room) 
	{
		super(room);
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
		return res;
	}

}

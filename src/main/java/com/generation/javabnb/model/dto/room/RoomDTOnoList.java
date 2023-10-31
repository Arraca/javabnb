package com.generation.javabnb.model.dto.room;

import java.util.List;

import com.generation.javabnb.model.dto.roombooking.RoomBookingDTO;
import com.generation.javabnb.model.entities.Room;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class RoomDTOnoList extends GenericRoomDTO
{

	

	public RoomDTOnoList(Room room) 
	{
		super(room);
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
		return res;
	}

}

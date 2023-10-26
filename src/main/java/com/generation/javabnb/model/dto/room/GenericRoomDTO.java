package com.generation.javabnb.model.dto.room;

import java.util.List;

import javax.persistence.OneToMany;

import com.generation.javabnb.model.entities.Room;
import com.generation.javabnb.model.entities.RoomBooking;

public abstract class GenericRoomDTO 
{
	private Integer id;
	private String name;
	private Integer capacity;
	private Double base_price;
	private String notes;
	private String img_url;
	

	public GenericRoomDTO () {};
	
	public GenericRoomDTO (Room room)
	{
		this.id = room.getId();
		this.name = room.getName();
		this.capacity = room.getCapacity();
		this.base_price = room.getBase_price();
		this.notes = room.getNotes();
		this.img_url = room.getImg_url();
	};
	
	public abstract Room convertToRoom();

}

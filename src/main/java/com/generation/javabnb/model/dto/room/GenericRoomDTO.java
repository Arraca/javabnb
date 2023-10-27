package com.generation.javabnb.model.dto.room;

import java.util.List;

import javax.persistence.OneToMany;

import com.generation.javabnb.model.entities.Room;
import com.generation.javabnb.model.entities.RoomBooking;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
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
	
	/**
	 * Il metodo controlla che una room sia valida. 
	 * Una Room è valida quando:
	 * - 
	 *
	 * @return
	 */
	public boolean isValid()
	{
		return 	name != null 		&& !name.isBlank() 		&&
				capacity != null 	&& capacity > 0 		&&
				base_price != null 	&& base_price > 0 		&&
				img_url != null 	&& !img_url.isBlank();
	}

}

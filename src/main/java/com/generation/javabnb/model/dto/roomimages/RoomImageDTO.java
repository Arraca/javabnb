package com.generation.javabnb.model.dto.roomimages;

import com.generation.javabnb.model.dto.room.RoomDTO;
import com.generation.javabnb.model.entities.RoomImage;

public class RoomImageDTO 
{
	private Integer id;
	private String img_url;
	private RoomDTO room;
	
	
	public RoomImageDTO() {};
	
	public RoomImageDTO(RoomImage roomImage)
	{
		id=roomImage.getId();
		img_url= roomImage.getImg_url();
		room = new RoomDTO(roomImage.getRoomto());
	}
	
	public RoomImage convertToRoomImage ()
	{
		RoomImage res = new RoomImage();
		res.setId(id);
		res.setImg_url(img_url);
		res.setRoomto(room.convertToRoom());
		
		return res;
	}
	
	
}

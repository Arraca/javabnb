package com.generation.javabnb.model.dto.roombooking;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.generation.javabnb.model.dto.room.RoomDTOnoList;
import com.generation.javabnb.model.dto.user.CustomerDTOnoList;
import com.generation.javabnb.model.entities.Room;
import com.generation.javabnb.model.entities.RoomBooking;
import com.generation.javabnb.model.entities.Season;
import com.generation.javabnb.model.entities.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomBookingDTO  
{
	
	private Integer id;
	private LocalDate checkIn;
	private LocalDate checkOut;
	private Double modPrice;
	private Double totalPrice;
//	private String email;
	private Boolean saved;
	private Season season;

	private RoomDTOnoList room;
	private CustomerDTOnoList customer;

	public RoomBookingDTO(RoomBooking roomBooking)
	{
		this.id=roomBooking.getId();
		this.checkIn=roomBooking.getCheckIn();
		this.checkOut=roomBooking.getCheckOut();
		this.modPrice=roomBooking.getModPrice();
		this.totalPrice=roomBooking.getTotalPrice();
//		this.email = roomBooking.getEmail();
		this.saved = roomBooking.getSaved();
//		this.season = roomBooking.getSeason();
		this.room = new RoomDTOnoList(roomBooking.getRoom());
		this.customer = new CustomerDTOnoList(roomBooking.getCustomer());
	}

	
	public RoomBooking convertToRoomBooking() 
	{
		RoomBooking res = new RoomBooking();
		res.setId(id);
		res.setCheckIn(checkIn);
		res.setCheckOut(checkOut);
		res.setModPrice(modPrice);
		res.setTotalPrice(totalPrice);
//		res.setEmail(email);
		res.setSaved(saved);
//		res.setSeason(season);
		res.setRoom(room.convertToRoom());
		res.setCustomer(customer.convertToUser());
		return res;
	}
	
    public boolean DateIsValid(LocalDate begin,LocalDate end)
    {
        try 
        {
            if (end.isAfter(begin))
                return true;
            else 
                return false;
        }
        catch (DateTimeParseException e) 
        {
            return false; 
        }
    }
    
//    private LocalDate dateConverter(String dateString)
//    {
//		
//	}

	
	public boolean isValid()
	{
		return 	checkIn!=null 					&& checkOut !=null 		&&
				DateIsValid(checkIn, checkOut) 	&& 
				modPrice!=null 		&&
				modPrice > 0 					&& totalPrice !=null 	&&
				totalPrice > 0 					&& room != null 		&& 
				customer != null;
	}
}

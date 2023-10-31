package com.generation.javabnb.model.dto.roombooking;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;

import com.generation.javabnb.model.dto.room.RoomDTOnoList;
import com.generation.javabnb.model.entities.RoomBooking;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomBookingDTOnoCustomer 
{
	private Integer id;
	private LocalDate checkIn;
	private LocalDate checkOut;
	private Double totalPrice;
	private Boolean saved;

	private RoomDTOnoList room;
	
	public RoomBookingDTOnoCustomer() {};

	public RoomBookingDTOnoCustomer(RoomBooking roomBooking)
	{
		this.id=roomBooking.getId();
		this.checkIn=roomBooking.getCheckIn();
		this.checkOut=roomBooking.getCheckOut();
		this.totalPrice=roomBooking.getTotalPrice();
		this.saved = roomBooking.getSaved();
		this.room = new RoomDTOnoList(roomBooking.getRoom());
	}

	
	public RoomBooking convertToRoomBooking() 
	{
		RoomBooking res = new RoomBooking();
		res.setId(id);
		res.setCheckIn(checkIn);
		res.setCheckOut(checkOut);
		res.setTotalPrice(totalPrice);
 		res.setSaved(saved);
		res.setRoom(room.convertToRoom());
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
    
	public Double ottieniPrice() 
	{
		Period period = checkIn.until(checkOut);
		Integer days = period.getDays();
		return days*room.getBase_price();
	} 

    
//    private LocalDate dateConverter(String dateString)
//    {
//		
//	} 

	
	public boolean isValid()
	{
		return 	checkIn!=null 					&& 	checkOut !=null 		&&
				DateIsValid(checkIn, checkOut) 	
				;
	}
}

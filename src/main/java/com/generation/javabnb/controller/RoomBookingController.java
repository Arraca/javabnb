package com.generation.javabnb.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.generation.javabnb.exception.InvalidEntityException;
import com.generation.javabnb.model.entities.Room;
import com.generation.javabnb.model.entities.RoomBooking;
import com.generation.javabnb.model.entities.User;
import com.generation.javabnb.model.dto.roombooking.RoomBookingDTO;
import com.generation.javabnb.model.repositories.RoomBookingRepository;
import com.generation.javabnb.model.repositories.RoomRepository;
import com.generation.javabnb.model.repositories.UserRepository;

@RestController
@CrossOrigin
public class RoomBookingController 
{
	@Autowired
	RoomBookingRepository rBrepo;
	
	@Autowired
	RoomRepository rRepo;
	
	@Autowired
	UserRepository uRepo;
	
	//---------------------------------------------------------GET ALL----------------------------------------------------------------------
	
	@GetMapping("/roombookings")
	public List<RoomBookingDTO> getAll()
	{
		return rBrepo.findAll().stream().map(roomB -> new RoomBookingDTO(roomB)).toList();
	}

	//----------------------------------------------------------GET ONE-----------------------------------------------------------------
	@GetMapping("/roombookings/{id}")
	public RoomBookingDTO getById(@PathVariable Integer id)
	{
		if(rBrepo.findById(id).isEmpty())
			throw new NoSuchElementException("Prenotazione non trovata");

		return new RoomBookingDTO(rBrepo.findById(id).get());
	}
	
	//--------------------------------------------------------INSERT ONE-------------------------------------------------------------------
	@PostMapping("/roombookings/{idRoom}/room/{emailUser}/user")
	public RoomBookingDTO insert(@RequestBody RoomBookingDTO roombookingDTO, @PathVariable Integer idRoom, @PathVariable String emailUser)
	{
		RoomBooking toInsert = roombookingDTO.convertToRoomBooking();
		
		if(!roombookingDTO.isValid())
			throw new InvalidEntityException("Dati prenotazione non validi");
		if(rRepo.findById(idRoom).isEmpty())
			throw new NoSuchElementException("Non esistono stanze con ID "+ idRoom);
		if(uRepo.findById(emailUser).isEmpty())
			throw new NoSuchElementException("Non esistono clienti con email "+ emailUser);
		
		Room roomToInsert = rRepo.findById(idRoom).get();
		User userToInsert = uRepo.findById(emailUser).get();
		
		toInsert.setCustomer(userToInsert);
		toInsert.setRoom(roomToInsert);
		
		userToInsert.getBookings().add(toInsert);
		roomToInsert.getBookings().add(toInsert);
		
		uRepo.save(userToInsert);
		
		rRepo.save(roomToInsert);
		
		return new RoomBookingDTO(rBrepo.save(toInsert));
	}
	
	//------------------------------------------------------------UPDATE ONE-----------------------------------------------------------------
	@PutMapping("/roombookings/{id}")
	public RoomBookingDTO update(@PathVariable int id, @RequestBody RoomBookingDTO roombookingDTO)
	{
		if(rBrepo.findById(id).isEmpty())
			throw new NoSuchElementException("Prenotazione da modificare non trovata");
		
		RoomBooking old = rBrepo.findById(id).get(); 
		
		RoomBooking toUpdate = roombookingDTO.convertToRoomBooking();
		
		toUpdate.setRoom(old.getRoom());
		toUpdate.setCustomer(old.getCustomer());
		toUpdate.setId(id);
		
		return new RoomBookingDTO(rBrepo.save(toUpdate));		
		
	}
	
	//-------------------------------------------------------------DELETE ONE-------------------------------------------------------------------
	@DeleteMapping("/roombookings/{id}")
	public void delete(@PathVariable int id)
	{
		if(rBrepo.findById(id).isEmpty())
			throw new NoSuchElementException("Prenotazione da eliminare non trovata");
		
		rBrepo.deleteById(id);
	}
}

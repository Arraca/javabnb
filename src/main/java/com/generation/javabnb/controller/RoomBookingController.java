package com.generation.javabnb.controller;

import java.time.LocalDate;
import java.time.Period;
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
import com.generation.javabnb.model.dto.customer.CustomerDTOnoList;
import com.generation.javabnb.model.dto.room.RoomDTOnoList;
import com.generation.javabnb.model.dto.roombooking.RoomBookingDTO;
import com.generation.javabnb.model.dto.roombooking.RoomBookingDTOnoCustomer;
import com.generation.javabnb.model.entities.Customer;
import com.generation.javabnb.model.entities.Room;
import com.generation.javabnb.model.entities.RoomBooking;
import com.generation.javabnb.model.entities.Season;
import com.generation.javabnb.model.repositories.CustomerRepository;
import com.generation.javabnb.model.repositories.RoomBookingRepository;
import com.generation.javabnb.model.repositories.RoomRepository;
import com.generation.javabnb.model.repositories.SeasonRepository;

@RestController
@CrossOrigin
public class RoomBookingController 
{
	@Autowired
	RoomBookingRepository rBrepo;
	
	@Autowired
	RoomRepository rRepo;
	
	@Autowired
	CustomerRepository cRepo;
	
	@Autowired
	SeasonRepository sRepo;
	
	//---------------------------------------------------------GET ALL----------------------------------------------------------------------
	
	@GetMapping("/roombookings")
	public List<RoomBookingDTO> getAll()
	{
		return rBrepo.findAll().stream().map(roomB -> new RoomBookingDTO(roomB)).toList();
	}
	
	@GetMapping("/roombookings/nolist")
    public List<RoomBookingDTOnoCustomer> getAllNoList()
    {
        return rBrepo.findAll().stream().map(room-> new RoomBookingDTOnoCustomer(room)).toList();
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
	@PostMapping("/roombookings/{id_room}/room/{id_customer}/customer")
	public RoomBookingDTO insert(@RequestBody RoomBookingDTO roombookingDTO, @PathVariable Integer id_room, @PathVariable Integer id_customer)
	{
		
		if(!roombookingDTO.isValid())
			throw new InvalidEntityException("Dati prenotazione non validi");
		if(rRepo.findById(id_room).isEmpty())
			throw new NoSuchElementException("Non esistono stanze con ID "+ id_room);
		if(cRepo.findById(id_customer).isEmpty())
			throw new NoSuchElementException("Non esistono clienti con ID "+ id_customer);
		
		Room roomToInsert = rRepo.findById(id_room).get();
		Customer userToInsert = cRepo.findById(id_customer).get();
		
		roombookingDTO.setCustomer(new CustomerDTOnoList(userToInsert));
		roombookingDTO.setRoom(new RoomDTOnoList(roomToInsert));
		
		roombookingDTO.setTotalPrice(roombookingDTO.ottieniPrice());
		
		if(getSeasonPrice(roombookingDTO.getCheckIn(), roombookingDTO.getCheckOut(), roombookingDTO)>0)
			roombookingDTO.setTotalPrice(roombookingDTO.ottieniPrice()+getSeasonPrice(roombookingDTO.getCheckIn(), roombookingDTO.getCheckOut(), roombookingDTO));
		
		RoomBooking toInsert = roombookingDTO.convertToRoomBooking();
		toInsert.setRoom(roomToInsert);
		toInsert.setCustomer(userToInsert);
		toInsert = rBrepo.save(toInsert);
		
		
		return new RoomBookingDTO(toInsert);
	}
	
	private Double getSeasonPrice(LocalDate checkIn, LocalDate checkOut, RoomBookingDTO toBook) 
	{
		Double res = 0.0;
		for(Season season : sRepo.findAll())
			if(intersectionV2(checkIn, checkOut, season.getBegin(), season.getEnd())>0)
				res+=intersectionV2(checkIn, checkOut, season.getBegin(), season.getEnd())*toBook.getRoom().getBase_price()*season.getPercent();
		
		return res;
	} 

	
	private Integer intersection(LocalDate firstBegin, LocalDate firstEnd, LocalDate secondBegin, LocalDate secondEnd) 
	{
		Integer res = 0;
		
	 	int meseInizioPeriodo1 =firstBegin.getMonthValue();
        int giornoInizioPeriodo1 = firstBegin.getDayOfMonth();
        int meseFinePeriodo1 = firstEnd.getMonthValue();
        int giornoFinePeriodo1 = firstEnd.getDayOfMonth();

        int meseInizioPeriodo2 = secondBegin.getMonthValue();
        int giornoInizioPeriodo2 = secondBegin.getDayOfMonth();
        int meseFinePeriodo2 = secondEnd.getMonthValue();
        int giornoFinePeriodo2 = secondEnd.getDayOfMonth();

        // Calcola il mese e il giorno di inizio dell'intersezione
        int meseInizioIntersezione = Math.max(meseInizioPeriodo1, meseInizioPeriodo2);
        int giornoInizioIntersezione = Math.max(giornoInizioPeriodo1, giornoInizioPeriodo2);

        // Calcola il mese e il giorno di fine dell'intersezione
        int meseFineIntersezione = Math.min(meseFinePeriodo1, meseFinePeriodo2);
        int giornoFineIntersezione = Math.min(giornoFinePeriodo1, giornoFinePeriodo2);

        // Calcola il numero di giorni di intersezione
        int giorniIntersezione = giornoFineIntersezione - giornoInizioIntersezione;

        if (meseInizioIntersezione==meseFineIntersezione && giorniIntersezione >= 0) {
            res+=giorniIntersezione;
        } 
        		
		return res;
	}
	
	private Integer intersectionV2(LocalDate firstBegin, LocalDate firstEnd, LocalDate secondBegin, LocalDate secondEnd) 
	{
		LocalDate periodBegin = firstBegin.isAfter(secondBegin)? firstBegin : secondBegin;
		LocalDate periodEnd = firstEnd.isBefore(secondEnd)? firstEnd : secondEnd;
		
		Period period = periodBegin.until(periodEnd);
		Integer days = period.getDays()+1;
		return days;
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

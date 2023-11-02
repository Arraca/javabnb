package com.generation.javabnb.controller;

import java.util.ArrayList;
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
import com.generation.javabnb.model.dto.customer.CustomerDTO;
import com.generation.javabnb.model.dto.customer.CustomerDTOnoList;
import com.generation.javabnb.model.dto.employee.EmployeeDTO;
import com.generation.javabnb.model.entities.RoomBooking;
import com.generation.javabnb.model.entities.Customer;
import com.generation.javabnb.model.repositories.CustomerRepository;
import com.generation.javabnb.model.repositories.RoomBookingRepository;

@RestController
@CrossOrigin
public class CustomerController 
{
	@Autowired
	CustomerRepository cRepo;
	
	@Autowired
	RoomBookingRepository rbRepo;
	
	//------------------------------------------------GET ALL CUSTOMERS------------------------------------------------------
	/**
	 * Restituisce una lista di customers completa di lista delle prenotazioni.
	 * Il metodo fallisce quando :
	 * - il backend non riesce a comunicare con il DB.
	 * @return
	 */
	@GetMapping("/customers")
	public List<CustomerDTO> findAllCustomers()
	{
		return cRepo.findAll().stream().map(customer -> new CustomerDTO(customer)).toList();
	}
	
	/**
	 * Restituisce una lista di customers completa di lista delle prenotazioni.
	 * Il metodo fallisce quando :
	 * - il backend non riesce a comunicare con il DB.
	 * @return
	 */
	@GetMapping("/customers/full")
	public List<CustomerDTO> findAllCustomersFull()
	{
		return cRepo.findAll().stream().map(customer -> new CustomerDTO(customer)).toList();
	}
	
	/**
	 * Restituisce una lista di customers completa di lista delle prenotazioni.
	 * Il metodo fallisce quando :
	 * - il backend non riesce a comunicare con il DB.
	 * @return
	 */
	@GetMapping("/customers/nolist")
	public List<CustomerDTOnoList> findAllCustomersNoList()
	{
		return cRepo.findAll().stream().map(customer -> new CustomerDTOnoList(customer)).toList();
	}

	//---------------------------------------------GET CUSTOMER BY ID-----------------------------------------------------------
	/**
	 * Metodo che legge un Customer completo di lista dal DB in funzione dell'ID.
	 * Parametri: ID che arriva come Integer.
	 * Il metodo fallisce quando:
	 * - il parametro è mancante
	 * - l'email passata come parametro non corrisponde a nessun User in DB
	 * 
	 * @param email
	 * @return
	 */
	@GetMapping("/customers/{id}")
	public CustomerDTO findCustomerByEmail (@PathVariable Integer id)
	{
		if(cRepo.findById(id).isEmpty())
			throw new NoSuchElementException("Non ci sono users con id "+id+" nel DB");
		
		return new CustomerDTO(cRepo.findById(id).get());
	}
	
	/**
	 * Metodo che legge un Customer completo di lista dal DB in funzione dell'ID.
	 * Parametri: ID che arriva come Integer.
	 * Il metodo fallisce quando:
	 * - il parametro è mancante
	 * - l'email passata come parametro non corrisponde a nessun User in DB
	 * 
	 * @param email
	 * @return
	 */
	@GetMapping("/customers/{id}/full")
	public CustomerDTO findCustomerByEmailFull (@PathVariable Integer id)
	{
		if(cRepo.findById(id).isEmpty())
			throw new NoSuchElementException("Non ci sono users con id "+id+" nel DB");
		
		return new CustomerDTO(cRepo.findById(id).get());
	}
	
	/**
	 * Metodo che legge un Customer senza lista dal DB in funzione dell'ID.
	 * Parametri: ID che arriva come Integer.
	 * Il metodo fallisce quando:
	 * - il parametro è mancante
	 * - l'email passata come parametro non corrisponde a nessun User in DB
	 * 
	 * @param email
	 * @return
	 */
	@GetMapping("/customers/{id}/nolist")
	public CustomerDTOnoList findCustomerByEmailNoList (@PathVariable Integer id)
	{
		if(cRepo.findById(id).isEmpty())
			throw new NoSuchElementException("Non ci sono users con id "+id+" nel DB");
		
		return new CustomerDTOnoList(cRepo.findById(id).get());
	}

	//---------------------------------------------GET CUSTOMER BY USERNAME--------------------------------------------
	/**
	 * Metodo che legge un Customer completo di lista dal DB in funzione dell'ID.
	 * Parametri: ID che arriva come Integer.
	 * Il metodo fallisce quando:
	 * - il parametro è mancante
	 * - l'email passata come parametro non corrisponde a nessun Customer in DB
	 * 
	 * @param email
	 * @return
	 */
	@GetMapping("/customers/{username}/username")
	public CustomerDTO findCustomerById (@PathVariable String username)
	{
		if(cRepo.findByUsername(username).isEmpty())
			throw new NoSuchElementException("Non ci sono users con email "+username+" nel DB");
		
		return new CustomerDTO(cRepo.findByUsername(username).get(0));
	}
	
	/**
	 * Metodo che legge un Customer completo di lista dal DB in funzione dell'ID.
	 * Parametri: ID che arriva come Integer.
	 * Il metodo fallisce quando:
	 * - il parametro è mancante
	 * - l'email passata come parametro non corrisponde a nessun Customer in DB
	 * 
	 * @param email
	 * @return
	 */
	@GetMapping("/customers/{username}/username/full")
	public CustomerDTO findFullCustomerById (@PathVariable String username)
	{
		if(cRepo.findByUsername(username).isEmpty())
			throw new NoSuchElementException("Non ci sono users con email "+username+" nel DB");
		
		return new CustomerDTO(cRepo.findByUsername(username).get(0));
	}
	
	@GetMapping("/customers/{username}/username/nolist")
	public CustomerDTOnoList findNoListCustomerById (@PathVariable String username)
	{
		if(cRepo.findByUsername(username).isEmpty())
			throw new NoSuchElementException("Non ci sono users con email "+username+" nel DB");
		
		return new CustomerDTOnoList(cRepo.findByUsername(username).get(0));
	}

	

	//-------------------------------------------------INSERT CUSTOMER------------------------------------------------------------

	/**
	 * Metodo per l'inserimento di un nuovo customer.
	 * Il metodo fallisce quando :
	 * - il body che arriva non è valido.
	 * @param toInsert
	 * @return
	 */
	@PostMapping("/customers")
	public CustomerDTOnoList insertCustomer(@RequestBody CustomerDTOnoList toInsert)
	{
		if(!toInsert.isValid())
			throw new InvalidEntityException("I dati inseriti non sono validi. Inserirli nel modo corretto!");
		
		//--------------------------------NEL REGISTER------------------------------------------------
		
//		if(!toInsert.isEmailValid())
//			throw new InvalidEntityException("La mail non è valida. Il formato deve essere \"user@example.com\"!");

		
		Customer res = toInsert.convertToCustomer();
		res.setBookings(new ArrayList<RoomBooking>());
		
		return new CustomerDTOnoList(cRepo.save(res));

	}
	
	//---------------------------------------------------UPDATE CUSTOMER-----------------------------------------------------------
	
//	@PutMapping("/users/customers/{email}")
//	public CustomerDTO updateCustomer(@RequestBody CustomerDTO toUpdate)
//	{
//
//	}
	
	@PutMapping("/customers/{id}")
	  public CustomerDTO updateOne(@PathVariable Integer id, @RequestBody CustomerDTO dto) 
	{
	      if(cRepo.findById(id).isEmpty())
	          throw new NoSuchElementException("Non ci sono users con email "+id+" nel DB");
	      if(!dto.isValid())
	          throw new InvalidEntityException("Il cliente che vuoi inserire non è valido");

	      Customer old = cRepo.findById(id).get();
	      Customer newCustomer = dto.convertToCustomer();
	      newCustomer.setBookings(new ArrayList<RoomBooking>());
	      
	      if(old.getBookings().size()>0)
	    	  for(RoomBooking rb : old.getBookings())
	    	  {
	    		  rb.setCustomer(newCustomer);
	    		  newCustomer.getBookings().add(rb);
	    	  }
	    		  
	
	      return new CustomerDTO(cRepo.save(newCustomer));
	}
	
	
	//------------------------------------------------------DELETE CUSTOMER------------------------------------------------------------
	
	@DeleteMapping("customers/{id}")
    public void deleteOne(@PathVariable Integer id)
    {
        if(cRepo.findById(id).isEmpty())
            throw new NoSuchElementException("Non ci sono users con email "+id+" nel DB.");

        cRepo.deleteById(id);
    }
	
	
	
	
	
	
	
	
}

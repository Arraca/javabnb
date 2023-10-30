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
import com.generation.javabnb.model.dto.user.CustomerDTO;
import com.generation.javabnb.model.dto.user.CustomerDTOnoList;
import com.generation.javabnb.model.dto.user.EmployeeDTO;
import com.generation.javabnb.model.entities.RoomBooking;
import com.generation.javabnb.model.entities.User;
import com.generation.javabnb.model.repositories.RoomBookingRepository;

@RestController
@CrossOrigin
public class UserController 
{
	@Autowired
	com.generation.javabnb.model.repositories.UserRepository uRepo;
	
	@Autowired
	RoomBookingRepository rbRepo;
	
	//------------------------------------------------GET ALL CUSTOMERS------------------------------------------------------
	/**
	 * Restituisce una lista di customers completa di lista delle prenotazioni.
	 * Il metodo fallisce quando :
	 * - il backend non riesce a comunicare con il DB.
	 * @return
	 */
	@GetMapping("/users/customers")
	public List<CustomerDTO> findAllCustomers()
	{
		return uRepo.findAll().stream().filter(user -> user.getType().equalsIgnoreCase("Customer")).map(customer -> new CustomerDTO(customer)).toList();
	}
	
	/**
	 * Restituisce una lista di customers completa di lista delle prenotazioni.
	 * Il metodo fallisce quando :
	 * - il backend non riesce a comunicare con il DB.
	 * @return
	 */
	@GetMapping("/users/customers/full")
	public List<CustomerDTO> findAllCustomersFull()
	{
		return uRepo.findAll().stream().filter(user -> user.getType().equalsIgnoreCase("Customer")).map(customer -> new CustomerDTO(customer)).toList();
	}
	
	/**
	 * Restituisce una lista di customers completa di lista delle prenotazioni.
	 * Il metodo fallisce quando :
	 * - il backend non riesce a comunicare con il DB.
	 * @return
	 */
	@GetMapping("/users/customers/nolist")
	public List<CustomerDTOnoList> findAllCustomersNoList()
	{
		return uRepo.findAll().stream().filter(user -> user.getType().equalsIgnoreCase("Customer")).map(customer -> new CustomerDTOnoList(customer)).toList();
	}

	//----------------------------------------------GET ALL EMPLOYEES---------------------------------------------------------
	
	/**
	 * Restituisce una lista di employees.
	 * Il metodo fallisce quando :
	 * - il backend non riesce a comunicare con il DB.
	 * @return
	 */
	@GetMapping("/users/employees")
	public List<EmployeeDTO> findAllEmployees()
	{
		return uRepo.findAll().stream().filter(user -> user.getType().equalsIgnoreCase("Employee")).map(employee -> new EmployeeDTO(employee)).toList();
	}
	
	//---------------------------------------------GET CUSTOMER BY EMAIL-----------------------------------------------------------
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
	@GetMapping("/users/customers/{email}")
	public CustomerDTO findCustomerByEmail (@PathVariable String email)
	{
		if(uRepo.findById(email).isEmpty())
			throw new NoSuchElementException("Non ci sono users con id "+email+" nel DB");
		if(!uRepo.findById(email).get().getType().equalsIgnoreCase("customer"))
			throw new NoSuchElementException("La mail inserita non corrisponde ad un customer ma ad un employee!");
		
		return new CustomerDTO(uRepo.findById(email).get());
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
	@GetMapping("/users/customers/{email}/full")
	public CustomerDTO findCustomerByEmailFull (@PathVariable String email)
	{
		if(uRepo.findById(email).isEmpty())
			throw new NoSuchElementException("Non ci sono users con id "+email+" nel DB");
		
		return new CustomerDTO(uRepo.findById(email).get());
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
	@GetMapping("/users/customers/{email}/nolist")
	public CustomerDTOnoList findCustomerByEmailNoList (@PathVariable String email)
	{
		if(uRepo.findById(email).isEmpty())
			throw new NoSuchElementException("Non ci sono users con id "+email+" nel DB");
		
		return new CustomerDTOnoList(uRepo.findById(email).get());
	}

	//---------------------------------------------GET CUSTOMER BY NUMERIC ID--------------------------------------------
//	/**
//	 * Metodo che legge un Customer completo di lista dal DB in funzione dell'ID.
//	 * Parametri: ID che arriva come Integer.
//	 * Il metodo fallisce quando:
//	 * - il parametro è mancante
//	 * - l'email passata come parametro non corrisponde a nessun Customer in DB
//	 * 
//	 * @param email
//	 * @return
//	 */
//	@GetMapping("/users/customers/{id}")
//	public CustomerDTO findCustomerById (@PathVariable Integer id)
//	{
//		if(uRepo.findByNumericId(id)isEmpty())
//			throw new NoSuchElementException("Non ci sono users con id "+email+" nel DB");
//		
//		return new CustomerDTO(uRepo.findById(email).get());
//	}
//	
//	/**
//	 * Metodo che legge un Customer completo di lista dal DB in funzione dell'ID.
//	 * Parametri: ID che arriva come Integer.
//	 * Il metodo fallisce quando:
//	 * - il parametro è mancante
//	 * - l'email passata come parametro non corrisponde a nessun Customer in DB
//	 * 
//	 * @param email
//	 * @return
//	 */
//	@GetMapping("/users/customers/{email}/full")
//	public CustomerDTO findCustomerByIdFull (@PathVariable String email)
//	{
//		if(uRepo.findById(email).isEmpty())
//			throw new NoSuchElementException("Non ci sono users con id "+email+" nel DB");
//		
//		return new CustomerDTO(uRepo.findById(email).get());
//	}
//	
//	/**
//	 * Metodo che legge un Customer senza lista dal DB in funzione dell'ID.
//	 * Parametri: ID che arriva come Integer.
//	 * Il metodo fallisce quando:
//	 * - il parametro è mancante
//	 * - l'email passata come parametro non corrisponde a nessun Customer in DB
//	 * 
//	 * @param email
//	 * @return
//	 */
//	@GetMapping("/users/customers/{email}/nolist")
//	public CustomerDTOnoList findCustomerByIdNoList (@PathVariable String email)
//	{
//		if(uRepo.findById(email).isEmpty())
//			throw new NoSuchElementException("Non ci sono users con id "+email+" nel DB");
//		
//		return new CustomerDTOnoList(uRepo.findById(email).get());
//	}

	//----------------------------------------------------GET EMPLOYEE BY EMAIL---------------------------------------------------------
	
	/**
	 * Metodo che legge un Employee dal DB in funzione dell'email.
	 * Parametri: email che arriva come String.
	 * Il metodo fallisce quando:
	 * - il parametro è mancante
	 * - l'email passata come parametro non corrisponde a nessun User in DB
	 * @param email
	 * @return
	 */
	@GetMapping("/users/employees/{email}")
	public EmployeeDTO findEmployeeByEmail (@PathVariable String email)
	{
		if(uRepo.findById(email).isEmpty())
			throw new NoSuchElementException("Non ci sono users con id "+email+" nel DB");
		if(!uRepo.findById(email).get().getType().equalsIgnoreCase("employee"))
			throw new NoSuchElementException("La mail inserita non corrisponde ad un employee ma ad un customer!");

		
		return new EmployeeDTO(uRepo.findById(email).get());

	}
	
	//-------------------------------------------------INSERT CUSTOMER------------------------------------------------------------

	/**
	 * Metodo per l'inserimento di un nuovo customer.
	 * Il metodo fallisce quando :
	 * - il body che arriva non è valido.
	 * @param toInsert
	 * @return
	 */
	@PostMapping("/users/customers")
	public CustomerDTOnoList insertCustomer(@RequestBody CustomerDTOnoList toInsert)
	{
		if(!toInsert.isValid())
			throw new InvalidEntityException("I dati inseriti non sono validi. Inserirli nel modo corretto!");
		if(!toInsert.isEmailValid())
			throw new InvalidEntityException("La mail non è valida. Il formato deve essere \"user@example.com\"!");

		
		User res = toInsert.convertToUser();
		res.setType("Customer");
		res.setBookings(new ArrayList<RoomBooking>());
		
		return new CustomerDTOnoList(uRepo.save(res));

	}
	
	//---------------------------------------------------UPDATE CUSTOMER-----------------------------------------------------------
	
//	@PutMapping("/users/customers/{email}")
//	public CustomerDTO updateCustomer(@RequestBody CustomerDTO toUpdate)
//	{
//
//	}
	
	@PutMapping("/users/customers/{email}")
	  public CustomerDTO updateOne(@PathVariable String email, @RequestBody CustomerDTO dto) 
	{
	      if(uRepo.findById(email).isEmpty())
	          throw new NoSuchElementException("Non ci sono users con email "+email+" nel DB");
	      if(!dto.isValid())
	          throw new InvalidEntityException("Il cliente che vuoi inserire non è valido");
	      if(!uRepo.findById(email).get().getType().equalsIgnoreCase("customer"))
	    	  throw new InvalidEntityException("Non posso modificare ciò che non è un customer.");	    	  

	      User old = uRepo.findById(email).get();
	      User u = dto.convertToUser();
	      u.setUsername(email);
	      u.setBookings(new ArrayList<RoomBooking>());
	      u.setType(old.getType());
	      
	      if(old.getBookings().size()>0)
	    	  for(RoomBooking rb : old.getBookings())
	    	  {
	    		  rb.setCustomer(u);
	    		  u.getBookings().add(rb);
	    	  }
	    		  
	
	      return new CustomerDTO(uRepo.save(u));
	}
	
	
	@DeleteMapping("users/customers/{email}")
    public void deleteOne(@PathVariable String email)
    {
        if(uRepo.findById(email).isEmpty())
            throw new NoSuchElementException("Non ci sono users con email "+email+" nel DB.");

        uRepo.deleteById(email);
    }
	
	
	
	
	
	
	
	
}

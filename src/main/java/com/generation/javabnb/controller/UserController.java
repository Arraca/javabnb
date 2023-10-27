package com.generation.javabnb.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.generation.javabnb.model.dto.user.CustomerDTO;
import com.generation.javabnb.model.dto.user.CustomerDTOnoList;
import com.generation.javabnb.model.dto.user.EmployeeDTO;
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
		return uRepo.findAll().stream().filter(user -> user.getType().equalsIgnoreCase("Customer")).map(customer -> new EmployeeDTO(customer)).toList();
	}
	
	//---------------------------------------------GET CUSTOMER BY ID-----------------------------------------------------------
	
	@GetMapping("/users/customers/{email}")
	public CustomerDTO findCustomerById (@PathVariable String email)
	{
		if(uRepo.findById(email).isEmpty())
			throw new NoSuchElementException("Non ci sono users con id "+email+" nel DB");
		
		return new CustomerDTO(uRepo.findById(email).get());
	}
	
}

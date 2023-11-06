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
import com.generation.javabnb.model.dto.customer.CustomerDTO;
import com.generation.javabnb.model.dto.employee.EmployeeDTO;
import com.generation.javabnb.model.entities.Employee;
import com.generation.javabnb.model.repositories.EmployeeRepository;

@CrossOrigin
@RestController
public class EmployeeController 
{
	@Autowired
	EmployeeRepository eRepo;
	
	//-----------------------------GET ALL------------------------------------
	
	@GetMapping("/employees")
	public List<EmployeeDTO> findAll()
	{
		return eRepo.findAll().stream().map(employee -> new EmployeeDTO(employee)).toList();
	}
	
	//------------------------------GET ONE BY ID-----------------------------
	@GetMapping("/employees/{id}")
	public EmployeeDTO findById(@PathVariable Integer id)
	{
		if(eRepo.findById(id).isEmpty())
			throw new NoSuchElementException("Non ci sono users con id "+id+" nel DB");

		
		return new EmployeeDTO(eRepo.findById(id).get());

	}
	
	//-------------------------------GET ONE BY USERNAME----------------------
	@GetMapping("/employees/{username}/username")
	public EmployeeDTO findByUsername(@PathVariable String username)
	{
		if(eRepo.findByUsername(username).isEmpty())
			throw new NoSuchElementException("Non ci sono users con username "+username+" nel DB");

		
		return new EmployeeDTO(eRepo.findByUsername(username).get(0));

	}
	
	//-------------------------------POST--------------------------------------
	@PostMapping("/employees")
	public EmployeeDTO insertEmployee(@RequestBody EmployeeDTO toInsert)
	{
		if(!toInsert.isValid())
			throw new InvalidEntityException("L'Impiegato che si vuole inserire non è valido! compilare correttamente tutti i campi.");
		
		return new EmployeeDTO(eRepo.save(toInsert.convertToEmployee()));
	}
	
	//----------------------------UPDATE--------------------------------------
	@PutMapping("/employees/{id}")
	public EmployeeDTO updateEmployee(@RequestBody EmployeeDTO toUpdate, @PathVariable Integer id)
	{
		if(eRepo.findById(id).isEmpty())
	          throw new NoSuchElementException("Non ci sono impiegati con id "+id+" nel DB");
	    if(!toUpdate.isValid())
	          throw new InvalidEntityException("L'Impiegato che si vuole inserire non è valido! compilare correttamente tutti i campi.");

	    Employee newEmployee = toUpdate.convertToEmployee();
	    newEmployee.setId(id);
	    
	    return new EmployeeDTO(eRepo.save(newEmployee));

	}
	
	//------------------------------DELETE-------------------------------------
	
	@DeleteMapping("employees/{id}")
    public void deleteOne(@PathVariable Integer id)
    {
        if(eRepo.findById(id).isEmpty())
            throw new NoSuchElementException("Non ci sono employee con id "+id+" nel DB.");

        eRepo.deleteById(id);
    }
}

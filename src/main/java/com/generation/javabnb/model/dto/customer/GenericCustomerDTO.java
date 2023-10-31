package com.generation.javabnb.model.dto.customer;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.generation.javabnb.model.entities.Customer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class GenericCustomerDTO 
{
	protected Integer id;
	
	
	protected String name;
	protected String surname;
	protected LocalDate dob;
	protected String username;
	
	public GenericCustomerDTO() 
	{
		// TODO Auto-generated constructor stub
	}
	
	public GenericCustomerDTO(Customer customer) 
	{
		this.id=customer.getId();
		this.name=customer.getName();
		this.surname=customer.getSurname();
		this.dob = customer.getDob();
		this.username = customer.getUsername();
	}
	

	
	public abstract Customer convertToCustomer();

	public boolean isValid()
	{
		return 	name != null	 	&& !name.isBlank() 		&&
				surname != null 	&& !surname.isBlank()	&&
				username != null 	&& !username.isBlank()	&&
				dob!=null ;
	}



}

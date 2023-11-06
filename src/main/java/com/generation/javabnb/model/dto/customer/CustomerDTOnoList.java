package com.generation.javabnb.model.dto.customer;

import com.generation.javabnb.model.entities.Customer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class CustomerDTOnoList extends GenericCustomerDTO
{
	

	public CustomerDTOnoList() {};
	
	public CustomerDTOnoList(Customer customer)
	{
		super(customer);
	}

	@Override
	public Customer convertToCustomer() 
	{
		Customer res = new Customer();
		res.setId(id);
		res.setName(name);
		res.setSurname(surname);
		res.setDob(dob);
		res.setUsername(username);

		return res;
	}
	
}

package com.generation.javabnb.model.dto.employee;

import java.time.LocalDate;

import com.generation.javabnb.model.dto.customer.GenericCustomerDTO;
import com.generation.javabnb.model.entities.Customer;
import com.generation.javabnb.model.entities.Employee;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeDTO  
{

	private Integer id;
		
	private String name;
	private String surname;
	private LocalDate dob;
	private String img_url;
	private String username;

	
	public EmployeeDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public EmployeeDTO(Employee employee)
	{
		this.id=employee.getId();
		this.name=employee.getName();
		this.surname=employee.getSurname();
		this.dob=employee.getDob();
		this.img_url=employee.getImg_url();
		this.username = employee.getUsername();

	}


	
	public Employee convertToEmployee() 
	{
		Employee res = new Employee();
		res.setId(id);
		res.setName(name);
		res.setSurname(surname);
		res.setDob(dob);
		res.setImg_url(img_url);
		res.setUsername(username);
		return res;
	}
	
	public boolean isValid()
	{
		return 	name != null	 	&& !name.isBlank() 		&&
				surname != null 	&& !surname.isBlank()	&&
				img_url != null 	&& !img_url.isBlank()	&&
				dob!=null ;
	}


}

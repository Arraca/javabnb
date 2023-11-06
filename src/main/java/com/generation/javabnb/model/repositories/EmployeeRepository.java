package com.generation.javabnb.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.javabnb.model.entities.Employee;
import java.util.List;


public interface EmployeeRepository extends JpaRepository<Employee, Integer> 
{
	List<Employee> findByUsername(String username);
}

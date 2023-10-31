package com.generation.javabnb.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.javabnb.model.entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> 
{

}

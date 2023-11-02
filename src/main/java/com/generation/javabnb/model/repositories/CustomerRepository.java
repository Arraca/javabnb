package com.generation.javabnb.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.javabnb.model.entities.Customer;
import java.util.List;


public interface CustomerRepository extends JpaRepository<Customer, Integer> 
{
 List<Customer> findByUsername(String username);
}

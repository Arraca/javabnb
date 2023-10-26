package com.generation.javabnb.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.javabnb.model.entities.User;

public interface UserRepository extends JpaRepository<User, String> 
{
	
}

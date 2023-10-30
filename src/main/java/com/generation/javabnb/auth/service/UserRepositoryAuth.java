package com.generation.javabnb.auth.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.javabnb.auth.model.UserInDb;

public interface UserRepositoryAuth extends JpaRepository<UserInDb, String>
{

}

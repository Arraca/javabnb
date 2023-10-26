package com.generation.javabnb.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.javabnb.model.entities.RoomBooking;

public interface RoomBookingRepository extends JpaRepository<RoomBooking, Integer> 
{

}

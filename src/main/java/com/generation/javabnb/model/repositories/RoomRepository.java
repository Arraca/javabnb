package com.generation.javabnb.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.javabnb.model.entities.Room;

public interface RoomRepository extends JpaRepository<Room, Integer> 
{

}

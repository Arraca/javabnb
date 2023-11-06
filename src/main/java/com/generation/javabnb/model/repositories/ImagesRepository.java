package com.generation.javabnb.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.javabnb.model.entities.RoomImage;

public interface ImagesRepository extends JpaRepository<RoomImage, Integer> 
{

}

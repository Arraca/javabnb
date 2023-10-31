package com.generation.javabnb.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.javabnb.model.entities.Season;
import java.util.List;



public interface SeasonRepository extends JpaRepository<Season,Integer>
{
	List<Season> findByName(String name);
}

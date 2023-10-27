package com.generation.javabnb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.javabnb.model.dto.room.RoomDTO;
import com.generation.javabnb.model.dto.room.RoomDTOnoList;
import com.generation.javabnb.model.repositories.RoomRepository;

import javassist.expr.NewArray;

@RestController
@CrossOrigin
public class RoomController 
{

	@Autowired
	RoomRepository rRepo;
	
	//----------------------------------------------------GET ALL-----------------------------------------------------------
	/**
	 * Metodo per leggere tutte le stanze in DB complete della lista di prenotazioni.
	 * Il metodo fallisce quando:
	 * - la comunicazione con il DB non è andata a buon fine per ragioni di configurazione
	 * @return
	 */
	@GetMapping("/rooms")
	public List<RoomDTO> getAll()
	{
		return rRepo.findAll().stream().map(room -> new RoomDTO(room)).toList();
	}
	
	/**
	 * Metodo per leggere tutte le stanze in DB complete della lista di prenotazioni.
	 * Il metodo fallisce quando:
	 * - la comunicazione con il DB non è andata a buon fine per ragioni di configurazione
	 * @return
	 */
	@GetMapping("/rooms/full")
	public List<RoomDTO> getAllFull()
	{
		return rRepo.findAll().stream().map(room -> new RoomDTO(room)).toList();
	}
	
	/**
	 * Metodo per leggere tutte le stanze in DB senza lista di prenotazioni.
	 * Il metodo fallisce quando:
	 * - la comunicazione con il DB non è andata a buon fine per ragioni di configurazione
	 * @return
	 */
	@GetMapping("/rooms/nolist")
	public List<RoomDTOnoList> getAllNoList()
	{
		return rRepo.findAll().stream().map(room -> new RoomDTOnoList(room)).toList();
	}
	
	//--------------------------------------------------GET ONE BY ID-------------------------------------------------------

	

}

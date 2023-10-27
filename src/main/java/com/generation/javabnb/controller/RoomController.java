package com.generation.javabnb.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.generation.javabnb.exception.InvalidEntityException;
import com.generation.javabnb.model.dto.room.RoomDTO;
import com.generation.javabnb.model.dto.room.RoomDTOnoList;
import com.generation.javabnb.model.repositories.RoomRepository;



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

	/**
	 * Metodo che restituisce una room completa di lista in base all'id. 
	 * Parametro in ingresso : Integer id.
	 * Il metodo fallisce quando:
	 * - Il parametro id non è numerico
	 * - Il parametro id è assente
	 * - Non ci sono Room con quell'id nel DB.
	 * @param id
	 * @return
	 */
	@GetMapping("/rooms/{id}")
	public RoomDTO getOneById(@PathVariable Integer id)
	{
		if(rRepo.findById(id).isEmpty())
			throw new NoSuchElementException("Non ci sono stanze con id "+id+" nel DB.");
		return new RoomDTO(rRepo.findById(id).get());
	}
	
	/**
	 * Metodo che restituisce una room completa di lista in base all'id. 
	 * Parametro in ingresso : Integer id.
	 * Il metodo fallisce quando:
	 * - Il parametro id non è numerico
	 * - Il parametro id è assente
	 * - Non ci sono Room con quell'id nel DB.
	 * @param id
	 * @return
	 */
	@GetMapping("/rooms/{id}/full")
	public RoomDTO getOneByIdFull(@PathVariable Integer id)
	{
		if(rRepo.findById(id).isEmpty())
			throw new NoSuchElementException("Non ci sono stanze con id "+id+" nel DB.");
		return new RoomDTO(rRepo.findById(id).get());
	}
	/**
	 * Metodo che restituisce una room senza lista in base all'id. 
	 * Parametro in ingresso : Integer id.
	 * Il metodo fallisce quando:
	 * - Il parametro id non è numerico
	 * - Il parametro id è assente
	 * - Non ci sono Room con quell'id nel DB.
	 * @param id
	 * @return
	 */
	@GetMapping("/rooms/{id}/nolist")
	public RoomDTOnoList getOneByIdNoList(@PathVariable Integer id)
	{
		if(rRepo.findById(id).isEmpty())
			throw new NoSuchElementException("Non ci sono stanze con id "+id+" nel DB.");
		return new RoomDTOnoList(rRepo.findById(id).get());
	}

	//--------------------------------------------POST------------------------------------------------
	/**
	 * Il metodo inserisce una nuova room nel DB.
	 * 
	 * @param toSave
	 * @return
	 */
	@PostMapping("rooms")
	public RoomDTOnoList insertRoom(@RequestBody RoomDTOnoList toSave)
	{
		if(!toSave.isValid())
			throw new InvalidEntityException("La camera che si desidera inserire non è valida");
		
		return new RoomDTOnoList(rRepo.save(toSave.convertToRoom())); 
	}
	
	//------------------------------------------PUT---------------------------------------------------
	
	/**
	 * Il metodo consente di modificare una stanza presente nel DB.
	 * Parametri : un body che arriva come RoomDTO e un ID che arriva come integer.
	 * Il metodo fallisce quando:
	 * - il parametro ID è mancante
	 * - il parametro ID non è numerico
	 * - l'ID non corrisponde a nessun prodotto nel DB
	 * - il DTO che arriva come body non è valido
	 * @param toInsert
	 * @param id
	 * @return
	 */
	@PutMapping("rooms/{id}")
	public RoomDTO updateRoom(@RequestBody RoomDTO toInsert, @PathVariable Integer id)
	{
		if(!rRepo.findById(id).isEmpty())
			throw new NoSuchElementException("Non ci sono stanze con id "+id+" nel DB, non posso eseguire la modifica!");
		if(!toInsert.isValid())
			throw new InvalidEntityException("La camera che si desidera inserire non è valida");
		
		toInsert.setId(id);
		
		return new RoomDTO(rRepo.save(toInsert.convertToRoom()));

	}
	
	//-----------------------------------------DELETE----------------------------------------------------
	/**
	 * Il metodo permette la cancellazione di una room nel DB.
	 * Parametri: ID che arriva come Integer.
	 * Il metodo fallisce quando:
	 * - L'ID non è numerico
	 * - l'ID è assente.
	 * - Non viene trovata una Room nel DB con quell'ID
	 * @param id
	 */
	@DeleteMapping("rooms/{id}")
	public void deleteRoom(@PathVariable Integer id)
	{
		if(!rRepo.findById(id).isEmpty())
			throw new NoSuchElementException("Non ci sono stanze con id "+id+" nel DB, non posso eseguire la modifica!");

		rRepo.deleteById(id);
	}
	
	
	
	
	

}

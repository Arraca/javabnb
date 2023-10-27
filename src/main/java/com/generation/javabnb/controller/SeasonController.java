package com.generation.javabnb.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.generation.javabnb.model.entities.Season;
import com.generation.javabnb.model.entities.dto.season.SeasonDTO;
import com.generation.javabnb.model.repositories.SeasonRepository;


@RestController
@CrossOrigin
public class SeasonController 
{

	@Autowired 
	SeasonRepository sRepo;
	
	/*Metodo che legge tutte le season dal bd
	 *questo metodo fallisce quando ci soon
	 * 
	 */
	@GetMapping("/season")
	public List<SeasonDTO> getAll()
	{
		return sRepo.findAll().stream().map(season->new SeasonDTO(season)).toList();
		
	}
	
	/*
	 * Questo metodo legge una singola season usando come parametro l'id
	 * Fallisce quando:
	 * -Il parametro passato al metodo non è valido(Stringa),
	 * -Il parametro non è presente
	 * -Il parametro ha sintassi corretta ma non è presente sul db.
	 * 
	 */
	@GetMapping("/season/{id}")
	public SeasonDTO getOneById(@PathVariable Integer id)
	{
		Optional<Season> res = sRepo.findById(id);
		if(res.isEmpty())
			throw new NoSuchElementException("Non è presente una season nel database con id:"+id+".");
		
		return new SeasonDTO (res.get());
	}
	
	/*Questo metodo salva una nuova season nel db
	 * Fallisce quando:
	 * -La season è valida(Season.isValid==false)
	 * -Il JSON che viene restituito non è corretto
	 * -
	 * 
	 */
	@PostMapping("/season")
	public SeasonDTO InsertNewSeson(@RequestBody SeasonDTO toSave)
	{
		if(!toSave.DateIsValid(toSave.getBegin(), toSave.getEnd()))
			throw new NoSuchElementException("La Data della season che vuoi inserire è sbagliata");
		
		if(!toSave.isValid())
			throw new NoSuchElementException("La season che vuoi inserire non è valida, controlla di nuovo i dati");
		
		
		return new SeasonDTO(sRepo.save(toSave.convertToSeason()));
		
	}
	
	
	/*Questo metodo modifica una season già presente nel db con un determinato id.
	 * Fallisce quando:
	 *-Il parametro passato al metodo non è valido(Stringa),
	 *-Il parametro non è presente
	 *-Il parametro ha sintassi corretta ma non è presente sul db.
	 *-La season è valida(Season.isValid==false)
	 *-Il JSON che viene restituito non è corretto
	 */
	@PutMapping("/season/{id}")
	public SeasonDTO modifyOne(@RequestBody SeasonDTO toModify, @PathVariable Integer id)
	{
		if(sRepo.findById(id).isEmpty())
			throw new NoSuchElementException("Impossibile trovare la season con id" +id+" che vuoi modificare.");
		
		if(!toModify.isValid())
			throw new NoSuchElementException("La season che vuoi modificate non è valida, controlla di nuovo i dati");
		
		toModify.setId(id);
		
		return new SeasonDTO(sRepo.save(toModify.convertToSeason()));
	}
	
	/*
	 * Questo metodo cancella una season con un determinato id dal db
	 * Fallisce quando:
	 * -Il parametro passato al metodo non è valido(Stringa),
	 * -Il parametro non è presente
	 * -Il parametro ha sintassi corretta ma non è presente sul db.
	 */
	@DeleteMapping("season/{id}")
	public void delete(@PathVariable Integer id)
	{
		if(sRepo.findById(id).isEmpty())
			throw new NoSuchElementException("Impossibile trovare la season con id" +id+" che vuoi eliminare.");
		
		sRepo.deleteById(id);
	}
}

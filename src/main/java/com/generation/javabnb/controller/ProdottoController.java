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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.javabnb.exception.InvalidEntityException;
import com.generation.javabnb.model.entities.Prodotto;
import com.generation.javabnb.model.entities.dto.season.ProdottoDTO;
import com.generation.javabnb.model.entities.dto.season.ProdottoDTOnoList;
import com.generation.javabnb.model.repositories.ClienteRepository;
import com.generation.javabnb.model.repositories.OrdineRepository;
import com.generation.javabnb.model.repositories.ProdottoRepository;

@RestController
@CrossOrigin
@RequestMapping("/api/")
public class ProdottoController 
{
	@Autowired
	ProdottoRepository pRepo;
	@Autowired
	OrdineRepository oRepo;
	@Autowired
	ClienteRepository cRepo;
	
	//-------------------------------------------------------------------GET ALL-----------------------------------------------------------------------------------------------------------
	
	/**
	 * Restituisce la lista di prodotti completa di lista.
	 * @return
	 */
	private List<ProdottoDTO> getAllProdotti()
	{
		return pRepo.findAll().stream().map(prodotto -> new ProdottoDTO(prodotto)).toList();
	}
	/**
	 * Con mappatura di default completa di lista.
	 * @return
	 */
	@GetMapping("prodotti")
	public List<ProdottoDTO> getAll()
	{
		return getAllProdotti();
	}
	/**
	 * Con mappatura /full è completa di lista.
	 * @return
	 */
	@GetMapping("prodotti/full")
	public List<ProdottoDTO> getAllFull()
	{
		return getAllProdotti();
	}
	/**
	 * Con mappatura /nolist è senza lista.
	 * @return
	 */
	@GetMapping("prodotti/nolist")
	public List<ProdottoDTOnoList> getAllNoList()
	{
		return pRepo.findAll().stream().map(prodotto -> new ProdottoDTOnoList(prodotto)).toList();
	}
	
	//-------------------------------------------------------------------------GET ONE BY ID------------------------------------------------------------------------------------------------------
	/**
	 * Controlla che ci siano prodotti con questo id. Se è presente lo restituisce.
	 * @param id
	 * @return
	 */
	private ProdottoDTO getOneProdotto(Integer id)
	{
		if(pRepo.findById(id).isEmpty())
			throw new NoSuchElementException("Non ci sono prodotti con id "+id+" nel database.");
		
		return new ProdottoDTO(pRepo.findById(id).get());
	}
	/**
	 * Di default restituisce il prodotto completo di lista.
	 * @param id
	 * @return
	 */
	@GetMapping("prodotti/{id}")
	public ProdottoDTO getOneById(@PathVariable Integer id)
	{
		return getOneProdotto(id);
	}
	/**
	 * Restituisce il prodotto completo di lista.
	 * @param id
	 * @return
	 */
	@GetMapping("prodotti/{id}/full")
	public ProdottoDTO getOneByIdFull(@PathVariable Integer id)
	{
		return getOneProdotto(id);
	}
	/**
	 * Restituisce il prodotto senza lista.
	 * @param id
	 * @return
	 */
	@GetMapping("prodotti/{id}/nolist")
	public ProdottoDTOnoList getOneByIdNoList(@PathVariable Integer id)
	{
		if(pRepo.findById(id).isEmpty())
			throw new NoSuchElementException("Non ci sono prodotti con id "+id+" nel database.");
		
		return new ProdottoDTOnoList(pRepo.findById(id).get());

	}


	//-----------------------------------------------------------------------------POST------------------------------------------------------------------------------------------------
	/**
	 * Controlla la validità del prodotto. Se valido lo aggiunge al database, altrimenti propaga nuova eccezione di invalidità. 
	 * Infine restituisce il dto se è andato tutto a buon fine.
	 * @param dto
	 * @return
	 */
	@PostMapping("prodotti")
	public ProdottoDTOnoList saveOne(@RequestBody ProdottoDTOnoList dto)
	{
		if(!dto.isValid())
			throw new InvalidEntityException("Il prodotto che si desidera inserire non valido! Compilare correttamente tutti i campi.");
		
		
		return new ProdottoDTOnoList(pRepo.save(dto.convertToProdotto()));
	}
	
	//---------------------------------------------------------------------------PUT-----------------------------------------------------------------------------------------------------
	/**
	 * Controlla che nel DB sia presente un prodotto con questo id.
	 * Controlla che il Body per modificare il prodotto sia valido.
	 * Se sono rispettate queste condizioni modifica un prodotto nel database.
	 * @param dto
	 * @param id
	 * @return
	 */
	@PutMapping("prodotti/{id}")
	public ProdottoDTOnoList updateOne(@RequestBody ProdottoDTOnoList dto, @PathVariable Integer id)
	{
		if(pRepo.findById(id).isEmpty())
			throw new NoSuchElementException("Non ci sono prodotti con id "+id+" nel database. Non poso effettuare la modifica.");
		if(!dto.isValid())
			throw new InvalidEntityException("Il prodotto che si desidera inserire non valido! Compilare correttamente tutti i campi per eseguire la modifica.");
		
		dto.setId(id);
		return new ProdottoDTOnoList(pRepo.save(dto.convertToProdotto()));
	}
	
	//-----------------------------------------------------------------------DELETE----------------------------------------------------------------------------------------------------------
	/**
	 * Controlla che nel DB sia presente un prodotto con questo id.
	 * Se è presente effettua la cancellazione.
	 * @param id
	 */
	@DeleteMapping("prodotti/{id}")
	public void deleteOne(@PathVariable Integer id)
	{
		if(pRepo.findById(id).isEmpty())
			throw new NoSuchElementException("Non ci sono prodotti con id "+id+" nel database. Non poso effettuare la disattivazione.");
		if(pRepo.findById(id).get().getAttivo()==false)
			throw new RuntimeException("Il prodotto che si desidera disattivare e gia inattivo! Cretino!");
		
		Prodotto res = pRepo.findById(id).get();
		res.setAttivo(false);
		pRepo.save(res);
	}

	
}

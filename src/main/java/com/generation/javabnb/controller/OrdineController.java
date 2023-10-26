package com.generation.javabnb.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
import com.generation.javabnb.model.entities.Ordine;
import com.generation.javabnb.model.entities.Prodotto;
import com.generation.javabnb.model.entities.dto.ordine.OrdineDTO;
import com.generation.javabnb.model.entities.dto.ordine.OrdineDTOnoList;
import com.generation.javabnb.model.repositories.ClienteRepository;
import com.generation.javabnb.model.repositories.OrdineRepository;
import com.generation.javabnb.model.repositories.ProdottoRepository;

@RestController
@CrossOrigin
@RequestMapping("/api/")
public class OrdineController
{
	@Autowired 
	OrdineRepository oRepo;
	@Autowired
	ClienteRepository cRepo;
	@Autowired
	ProdottoRepository pRepo;
	
	//---------------------------------------------------------------------------------GET ALL------------------------------------------------------------------
	/**
	 * Restituisce la lista di ordini completa di liste. Viene utilizzato nei metodi findAll() con mappatura di default e /full. 
	 * @return
	 */
	private List<OrdineDTO> findAllProdottiFull()
	{
		return oRepo.findAll().stream().map(ordine -> new OrdineDTO(ordine)).toList();
	}
	
	/**
	 * Con mappatura di default restituisce la lista di ordini completi di lista di prodotti.
	 * @return
	 */
	@GetMapping("ordini")
	public List<OrdineDTO> findAll()
	{
		return findAllProdottiFull();
		
	}
	
	/**
	 * Con mappatura /full restituisce la lista di ordini completi di lista di prodotti.
	 * @return
	 */
	@GetMapping("ordini/full")
	public List<OrdineDTO> findAllFull()
	{
		return findAllProdottiFull();
		
	}
	
	/**
	 * Con mappatura /nolist restituisce la lista di ordini senza la lista di prodotti.
	 * @return
	 */
	@GetMapping("ordini/nolist")
	public List<OrdineDTOnoList> findAllNoList()
	{
		return oRepo.findAll().stream().map(ordine -> new OrdineDTOnoList(ordine)).toList();
		
	}

	//----------------------------------------------------------------------------------GET ONE BY ID----------------------------------------------------------------------------------------------------------
	/**
	 * Metodo di supporto per la mappatura di default e la mappatura /full di findbyId().
	 * @param id
	 * @return
	 */
	private OrdineDTO findOneOrdineById(Integer id) 
	{
		if(oRepo.findById(id).isEmpty())
			throw new NoSuchElementException("Non ci sono ordini con id "+id+" nel DB.");
		return new OrdineDTO(oRepo.findById(id).get());
	}
	
	/**
	 * Mappatura di default. Restituisce un ordine DTO completa della lista di prodotti.
	 * @param id
	 * @return
	 */
	@GetMapping("ordini/{id}")
	public OrdineDTO findOneById(@PathVariable Integer id)
	{
		return findOneOrdineById(id);
	}
	
	/**
	 * Mappatura /full. Restituisce un ordine DTO completa della lista di prodotti.
	 * @param id
	 * @return
	 */
	@GetMapping("ordini/{id}/full")
	public OrdineDTO findOneByIdFull(@PathVariable Integer id)
	{
		return findOneOrdineById(id);
	}
	
	/**
	 * Mappatura /nolist. Restituisce un ordineDTO senza lista di prodotti.
	 * @param id
	 * @return
	 */
	@GetMapping("ordini/{id}/nolist")
	public OrdineDTOnoList findOneByIdNoList(@PathVariable Integer id)
	{
		if(oRepo.findById(id).isEmpty())
			throw new NoSuchElementException("Non ci sono ordini con id "+id+" nel DB.");
		return new OrdineDTOnoList(oRepo.findById(id).get());

	}

	//------------------------------------------------------------------------------------POST-----------------------------------------------------------------------------------------------------------------
	
	/**
	 * Inserimento semplice di un nuovo ordine.
	 * @param dto
	 * @return
	 */
	@PostMapping("ordini")
	public OrdineDTOnoList saveOne(@RequestBody OrdineDTOnoList dto)
	{
		if(!dto.isValid())
			throw new InvalidEntityException("L'ordine che si desidera inserire non valido! Compilare correttamente tutti i campi.");
		if(cRepo.findById(dto.getId_cliente()).isEmpty())
			throw new NoSuchElementException("Il cliente con id "+dto.getId_cliente()+" a cui si vuole attribuire il nuovo ordine non presente nel database.");
		Ordine toSave = dto.convertToOrdine();
		toSave.setCliente(cRepo.findById(dto.getId_cliente()).get());
		return new OrdineDTOnoList(oRepo.save(toSave));
	}
	
	/**
	 * Trasforma il carrello corrente in un Ordine (impostando la data di acquisto)
	 * e lo salvo sul DB. dopodiché creo un nuovo carrello e lo assegno al cliente 
	 * che ha appena acquistato (setto spedito sennò dà errore, e sarà un carrello perchè
	 * data acquisto è null).
	 * @param id
	 * @return
	 */
	@PostMapping("ordini/{id}")
	public OrdineDTO acquista(@PathVariable Integer id)
	{
		Ordine daAcquistare = oRepo.findById(id).get();
		daAcquistare.setData_acquisto(LocalDateTime.now());
		oRepo.save(daAcquistare);
		Ordine nuovoCarrello = new Ordine();
		nuovoCarrello.setSpedito(false);
		nuovoCarrello.setData_acquisto(null);
		nuovoCarrello.setCliente(daAcquistare.getCliente());
		nuovoCarrello.setCosto_spedizione(0.00);
		nuovoCarrello.setProdotti(new ArrayList<Prodotto>());
		nuovoCarrello = oRepo.save(nuovoCarrello);
		
		return new OrdineDTO(nuovoCarrello);
	}
	
	/**
	 * Controlla che ci siano nel DB un ordine e un prodotto con id passati come @PathVariable.
	 * Nel caso siano presenti aggiunge il prodotto alla lista di prodotti dell'ordine, e l'ordine alla lista 
	 * di ordini del prodotto.
	 * Infine restituisce l'OrdineDTO completo di lista se l'inserimento è andato a buon fine.
	 * @param idOrdine
	 * @param idProdotto
	 * @return
	 */
	@PostMapping("ordini/{idOrdine}/prodotti/{idProdotto}")
	public void addOneProdottoToOrdine(@PathVariable Integer idOrdine, @PathVariable Integer idProdotto)
	{
		if(oRepo.findById(idOrdine).isEmpty())
			throw new NoSuchElementException("Non ci sono ordini con id "+idOrdine+" nel DB. Non posso eseguire l'operazione.");
		if(pRepo.findById(idProdotto).isEmpty())
			throw new NoSuchElementException("Non ci sono prodotti con id "+idProdotto+" nel DB. Non posso eseguire l'operazione");
		Ordine toSave = oRepo.findById(idOrdine).get();
		toSave.getProdotti().add(pRepo.findById(idProdotto).get());//Aggiunge prodotto alla lista di questo ordine.
//		pRepo.findById(idProdotto).get().getOrdini().add(oRepo.findById(idOrdine).get());//Aggiunge ordine alla lista di questo prodotto.
		oRepo.save(toSave);
		//restituisce l'ordine completo di lista.
		//return new OrdineDTO(oRepo.findById(idOrdine).get());
	}
	

	//----------------------------------------------------------------------------------PUT---------------------------------------------------------------------------------------------------------------------
	/**
	 * Controlla che nel DB ci sia un ordine con l'id passato come @PathVariable.
	 * Controlla la validità del dto che sarà il corpo della modifica.
	 * Infine, se è andato tutto a buon fine, imposta l'id dell'ordine e lo salva, in questo modo avviene la modifica.
	 * @param dto
	 * @param id
	 * @return
	 */
	@PutMapping("ordini/{id}")
	public OrdineDTOnoList updateOne(@RequestBody OrdineDTOnoList dto, @PathVariable Integer id)
	{
		if(oRepo.findById(id).isEmpty())
			throw new NoSuchElementException("Non ci sono ordini con id "+id+" nel DB. Non posso eseguire la modifica.");
		if(cRepo.findById(dto.getId_cliente()).isEmpty())
			throw new NoSuchElementException("Non ci sono clienti con id "+dto.getId_cliente()+" nel DB. Non posso eseguire il salvataggio.");
		if(!dto.isValid())
			throw new InvalidEntityException("L'ordine che si desidera inserire non valido! Compilare correttamente tutti i campi richiesti.");
		
		dto.setId(id);
		Ordine toSave = dto.convertToOrdine();
		toSave.setCliente(cRepo.findById(dto.getId_cliente()).get());
		toSave.setProdotti(new ArrayList<Prodotto>());
		for(Prodotto prodotto : oRepo.findById(id).get().getProdotti())
			toSave.getProdotti().add(prodotto);
		return new OrdineDTOnoList(oRepo.save(toSave));
	}
	
	//--------------------------------------------------------------------------------------------DELETE------------------------------------------------------------------------------------------------------
	/**
	 * Controlla se è presente l'ordine con questo id nel DB.
	 * Se è presente lo rimuove.
	 * @param id
	 */
	@DeleteMapping("ordini/{id}")
	public void deleteOne(@PathVariable Integer id)
	{
		if(oRepo.findById(id).isEmpty())
			throw new NoSuchElementException("Non ci sono ordini con id "+id+" nel DB. Non posso eseguire la cancellazione.");
		oRepo.deleteById(id);
	}
	
	/**
	 * Controlla che siano presenti un ordine e un prodotto con gli id passati come @pathVariable nel database.
	 * Controlla che nella lista di prodotti dell'ordine sia presente il prodotto con quell'id.
	 * Controlla che nella lista di ordini del prodotto sia presente un ordine con quell'id.
	 * Infine, se è tutto corretto, rimuove il prodotto dalla lista che appartiene all'ordine e viceversa.
	 * Senza ritorno. 
	 * @param idOrdine
	 * @param idProdotto
	 */
	@DeleteMapping("ordini/{idOrdine}/prodotti/{idProdotto}")
	public void removeProdottoFromOrdine(@PathVariable Integer idOrdine, @PathVariable Integer idProdotto)
	{
		if(oRepo.findById(idOrdine).isEmpty())
			throw new NoSuchElementException("Non ci sono ordini con id "+idOrdine+" nel DB. Non posso eseguire l'operazione.");
		if(pRepo.findById(idProdotto).isEmpty())
			throw new NoSuchElementException("non ci sono prodotti con id "+idProdotto+" nel DB. Non posso eseguire l'operazione.");
		if(!oRepo.findById(idOrdine).get().getProdotti().contains(pRepo.findById(idProdotto).get()))
			throw new NoSuchElementException("L'ordine con id "+idOrdine+" non contiene il prodotto con id "+idProdotto+". Non posso eseguire l'operazione.");
		if(!pRepo.findById(idProdotto).get().getOrdini().contains(oRepo.findById(idOrdine).get()))
			throw new NoSuchElementException("Il prodotto con id "+idProdotto+" non contiene l'ordine con id "+idOrdine+". Non posso eseguire l'operazione.");
		Ordine toSave = oRepo.findById(idOrdine).get();
		toSave.getProdotti().remove(pRepo.findById(idProdotto).get());
//		pRepo.findById(idProdotto).get().getOrdini().remove(oRepo.findById(idOrdine).get());
		oRepo.save(toSave);
	}

	
}

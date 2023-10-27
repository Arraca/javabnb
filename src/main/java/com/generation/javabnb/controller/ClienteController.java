package com.generation.javabnb.controller;

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
import com.generation.javabnb.model.entities.Cliente;
import com.generation.javabnb.model.entities.Ordine;
import com.generation.javabnb.model.entities.dto.ordine.OrdineDTO;
import com.generation.javabnb.model.entities.dto.user.ClienteDTO;
import com.generation.javabnb.model.entities.dto.user.ClienteDTOnoList;
import com.generation.javabnb.model.repositories.ClienteRepository;
import com.generation.javabnb.model.repositories.OrdineRepository;



@RestController
@CrossOrigin
@RequestMapping("/api/")
public class ClienteController 
{
	@Autowired
	ClienteRepository cRepo;
	
	@Autowired
	OrdineRepository oRepo;
	//--------------------------------------------------------------------------------------------------GET ALL------------------------------------------------------------------
	@GetMapping("clienti")
	public List<ClienteDTO> getAll()
	{
		return getAllClienti();
	}
	
	private List<ClienteDTO> getAllClienti()
	{
		return cRepo.findAll().stream().map(cliente -> new ClienteDTO(cliente)).toList();
	}
	
	@GetMapping("clienti/full")
	public List<ClienteDTO> getAllFull()
	{
		return getAllClienti();
	}
	
	@GetMapping("clienti/nolist")
	public List<ClienteDTOnoList> getAllNoList()
	{
		return cRepo.findAll().stream().map(cliente -> new ClienteDTOnoList(cliente)).toList();
	}
	
	//-------------------------------------------------------------------------------GET ONE BY ID--------------------------------------------------------------------------------
	
	@GetMapping("clienti/{id}")
	public ClienteDTO getOne(@PathVariable Integer id)
	{
		if(cRepo.findById(id).isEmpty())
			throw new NoSuchElementException("Nel database non esiste con id: "+id);
		
		return new ClienteDTO(cRepo.findById(id).get());
	}
	
	@GetMapping("clienti/{id}/full")
	public ClienteDTO getOneFull(@PathVariable Integer id)
	{
		if(cRepo.findById(id).isEmpty())
			throw new NoSuchElementException("Nel database non esiste con id: "+id);
		
		return new ClienteDTO(cRepo.findById(id).get());
	}

	@GetMapping("clienti/{id}/nolist")
	public ClienteDTOnoList getOneNoList(@PathVariable Integer id)
	{
		if(cRepo.findById(id).isEmpty())
			throw new NoSuchElementException("Nel database non esiste con id: "+id);
		
		return new ClienteDTOnoList(cRepo.findById(id).get());
	}
	
	@GetMapping("clienti/{idCliente}/carrello")
	public OrdineDTO getCarrello(@PathVariable Integer idCliente)
	{
		List<Ordine> ordini = oRepo.findAll();
		
		//Prendo, si spera, l'unico ordine non aquistato, il carrello
		Ordine carrello = ordini.stream().filter(o -> o.getData_acquisto()==null).findFirst().get();
		
		return new OrdineDTO(carrello);
	}
	
	//-----------------------------------------------------------------------------GET ONE BY USERNAME-----------------------------------------------------------------------------
	
	@GetMapping("clienti/{username}/username")
	public ClienteDTO getOneByUsername(@PathVariable String username)
	{
		if(cRepo.findByUsername(username)==null)
			throw new NoSuchElementException("Nel database non esiste con username: "+username);
		
		return new ClienteDTO(cRepo.findByUsername(username));
	}
	
	@GetMapping("clienti/{username}/username/full")
	public ClienteDTO getOneByUsernameFull(@PathVariable String username)
	{
		if(cRepo.findByUsername(username)==null)
			throw new NoSuchElementException("Nel database non esiste con username: "+username);

		return new ClienteDTO(cRepo.findByUsername(username));
	}

	@GetMapping("clienti/{username}/username/nolist")
	public ClienteDTOnoList getOneByUsernameNoList(@PathVariable String username)
	{
		if(cRepo.findByUsername(username)==null)
			throw new NoSuchElementException("Nel database non esiste con username: "+username);

		return new ClienteDTOnoList(cRepo.findByUsername(username));
	}

	//-------------------------------------------------------------------------------POST-------------------------------------------------------------------------------------------------
	
	@PostMapping("clienti")
	public ClienteDTOnoList saveOne(@RequestBody ClienteDTOnoList dto)
	{
		Cliente toInsert =dto.convertToCliente();
		
		if(!dto.isValid())
			throw new InvalidEntityException("Cliente che si desidera inserire non valido, compilare correttamente tutti i campi!");
		if(!dto.isUsernameValid())
			throw new InvalidEntityException("L'username non nel formato coretto, rispettare il formato 'user@domain.com'");
		
		toInsert = cRepo.save(toInsert);
		Ordine carrello = new Ordine();
		carrello.setCliente(toInsert);
		carrello.setSpedito(false);
		
		oRepo.save(carrello);
		ClienteDTOnoList res = new ClienteDTOnoList(toInsert);
		
		
		return res;
	}
	
		//Aggiungere al front end un sistema di login molto semplice
		//casellina dove inserire nome utente
		//pulsante per chiamare questo metodo
		//impostate l'utente nello stato (probabilmente in App)
		//fate in modo che il componente carrello mostri il carrello di quel cliente e che le aggiunte siano fatte su di esso
		//aggiungete il pulsante acquista al carrello che chiama il metodo acquista nell OrdineController e reimposta il carrello
		//con quello che riceve nella response
		//Nel caso qualcuno finisse prima delle 18, aggiungete anche una sezione con gli ordini vecchi	
	@PostMapping("clienti/{username}")
	public ClienteDTO mockLogin(@PathVariable String username)
	{
		
		return  new ClienteDTO(cRepo.findByUsername(username));
	}
	
	//-----------------------------------------------------------------------------PUT----------------------------------------------------------------------------------------------------
	
	@PutMapping("clienti/{id}")
	public ClienteDTOnoList updateOne(@RequestBody ClienteDTOnoList dto, @PathVariable Integer id)
	{
		if(cRepo.findById(id).isEmpty())
			throw new NoSuchElementException("Non ci sono clienti con questo id: "+id+" non posso modificarlo.");
		if(!dto.isValid())
			throw new InvalidEntityException("Cliente che si desidera inserire non valido, compilare correttamente tutti i campi per la modifica!");
		if(!dto.isUsernameValid())
			throw new InvalidEntityException("Username non nel formato corretto, rispettare il formato 'user@domain.com'");
		dto.setId(id);
		return new ClienteDTOnoList(cRepo.save(dto.convertToCliente()));

	}
	
	//---------------------------------------------------------------------------DELETE----------------------------------------------------------------------------------------------------
	
	@DeleteMapping("clienti/{id}")
	public void deleteOne(@PathVariable Integer id)
	{
		if(cRepo.findById(id).isEmpty())
			throw new NoSuchElementException("Non ci sono clienti con questo id: "+id+" non posso cancellarlo.");
		if(id==1)
			throw new RuntimeException("L'id 1 non corrisponde ad un cliente. Non può essere cancellato.");
		cRepo.deleteById(id);
	}
	
	@Deprecated
	@DeleteMapping("clientiv2/{id}")
	public void deleteOneV2(@PathVariable Integer id)
	{
		Cliente strawman = cRepo.findById(1).get();
		Cliente toDelete = cRepo.findById(id).get();
		List<Ordine> ordiniRes = new ArrayList<Ordine>();
		toDelete.getOrdini().stream().map(ordine -> ordiniRes.add(ordine));
		
		if(cRepo.findById(id).isEmpty())
			throw new NoSuchElementException("Non ci sono clienti con questo id: "+id+" non posso cancellarlo.");
		if(cRepo.findById(id).get().getOrdini().size()>0)// SE CI SONO ORDINI NEL CLIENTE CHE SI VUOLE ELIMINARE
			for(Ordine ordine : ordiniRes)//PER OGNI ORDINE NELLA LISTA
			{
				toDelete.getOrdini().remove(ordine);
				strawman.getOrdini().add(ordine);	//AGGIUNGO L'ORDINE ALLA LISTA DI ORDINI DI STRAWMAN
				
			}
				
		cRepo.save(toDelete);
		cRepo.save(strawman);
		cRepo.deleteById(id);//QUINDI CANCELLO IL CLIENTE
	}


}

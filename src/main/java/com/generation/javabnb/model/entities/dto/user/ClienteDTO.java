package com.generation.javabnb.model.entities.dto.cliente;

import java.util.List;

import com.generation.javabnb.model.entities.Cliente;
import com.generation.javabnb.model.entities.Ordine;
import com.generation.javabnb.model.entities.dto.ordine.OrdineDTO;

import lombok.Getter;
import lombok.Setter;
/**
 * Convertire i dati in formato dto per avere tutte le prorpietà di tipo primitivo
 * Comprese le liste per averne la disponibilità
 * @author enrub
 *
 */
@Getter
@Setter
//AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
public class ClienteDTO extends GenericClienteDTO 
{
	private List<OrdineDTO>ordini;
	
	public ClienteDTO() {}
	
	public ClienteDTO(Cliente cliente) 
	{
		super(cliente);
		ordini = cliente.getOrdini().stream().map(ordine -> new OrdineDTO(ordine)).toList();
	}

	@Override
	public Cliente convertToCliente() 
	{
		Cliente res = new Cliente();
		res.setId(id);
		res.setUsername(username);
		res.setNome(nome);
		res.setCognome(cognome);
		res.setIndirizzo(indirizzo);
		res.setOrdini(ordini.stream().map
				(
					ordine -> 
						{
							Ordine ord = ordine.convertToOrdine();
							ord.setCliente(res);
							return ord;
						}
				).toList());
		
		return res;
	}
}

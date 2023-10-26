package com.generation.javabnb.model.entities.dto.cliente;

import com.generation.javabnb.model.entities.Cliente;
public class ClienteDTOnoList extends GenericClienteDTO
{
	
	public ClienteDTOnoList() {}
	
	public ClienteDTOnoList(Cliente cliente) 
	{
		super(cliente);		
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
		
		return res;
	}

}

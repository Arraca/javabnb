package com.generation.javabnb.model.entities.dto.cliente;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.generation.javabnb.model.entities.Cliente;

import lombok.Getter;
import lombok.Setter;
/**
 * Convertire i dati in formato dto per avere tutte le prorpietà di tipo primitivo
 * @author enrub
 *
 */
@Getter
@Setter
public abstract class GenericClienteDTO
{
	protected Integer id;
	protected String username, nome, cognome, indirizzo;
	
	
	public GenericClienteDTO() {};
	
	public GenericClienteDTO(Cliente cliente)
	{
		id = cliente.getId();
		username = cliente.getUsername();
		nome = cliente.getNome();
		cognome = cliente.getCognome();
		indirizzo = cliente.getIndirizzo();
	}
	
	public abstract Cliente convertToCliente();
	
	public boolean isValid()
	{
		return 	username!=null 		&& !username.isBlank() 	&&
				nome!=null 			&& !nome.isBlank() 		&&
				cognome!=null		&& !cognome.isBlank() 	&&
				indirizzo!=null 	&& !indirizzo.isBlank() ;
	}
	
	public boolean isUsernameValid()
	{
		if(username==null || username.isBlank())
			return false;
		String regex = "^[a-zA-Z0-9_!#$%&amp;'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

		Pattern pattern = Pattern.compile(regex);
		
		Matcher matcher = pattern.matcher(username);
		return matcher.matches();
		
	}
}

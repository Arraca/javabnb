package com.generation.javabnb.model.entities.dto.prodotto;

import com.generation.javabnb.model.entities.Prodotto;

import lombok.Getter;
import lombok.Setter;
/**
 * Convertire i dati in formato dto per avere tutte le prorpietà di tipo primitivo
 * @author enrub
 *
 */
@Getter
@Setter
public abstract class GenericProdottoDTO 
{
	protected Integer id;
	protected String nome, tipologia, descrizione, url_foto;
	protected Double prezzo;
	protected Boolean attivo;
	
	public GenericProdottoDTO() {}
	
	public GenericProdottoDTO(Prodotto prodotto) 
	{
		id=prodotto.getId();
		nome=prodotto.getNome();
		tipologia=prodotto.getTipologia();
		descrizione=prodotto.getDescrizione();
		url_foto=prodotto.getUrl_foto();
		prezzo=prodotto.getPrezzo();
		attivo=prodotto.getAttivo();
	}
	
	public abstract Prodotto convertToProdotto();
	
	public boolean isValid()
	{
		return 	nome!=null 			&& !nome.isBlank() 			&&
				tipologia!=null 	&& !tipologia.isBlank() 	&&
				descrizione!=null 	&& !descrizione.isBlank() 	&&
				url_foto!=null 		&& !url_foto.isBlank() 		&&
				prezzo!=null  		&& 	prezzo>=0				&&
				attivo!=null;
	}

}

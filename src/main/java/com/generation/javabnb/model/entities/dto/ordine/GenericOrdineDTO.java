/**
 * 
 */
package com.generation.javabnb.model.entities.dto.ordine;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.generation.javabnb.model.entities.Ordine;
import com.generation.javabnb.model.entities.Prodotto;
import com.generation.javabnb.model.entities.dto.prodotto.ProdottoDTOnoList;

import lombok.Getter;
import lombok.Setter;

/**
 * Convertire i dati in formato dto per avere tutte le prorpietà di tipo primitivo
 * @author enrub
 *
 */
@Getter
@Setter
public abstract class GenericOrdineDTO
{
	protected Integer id;
	protected LocalDateTime data_acquisto;
	protected Boolean spedito;
	protected Double costo_spedizione, prezzo_totale, prezzo_base;
	protected Integer days;
	protected Map<Integer, Integer> frequenze;

	public GenericOrdineDTO() {}
	
	public GenericOrdineDTO(Ordine ordine)
	{
		id=ordine.getId();
		data_acquisto=ordine.getData_acquisto();
		spedito=ordine.getSpedito();
		costo_spedizione=ordine.getSpedizioneCosto();
		prezzo_base=ordine.getPrezzo();
		prezzo_totale=ordine.getPrezzoTotale();
		frequenze=ordine.getQuantitaV2();
		days=ordine.getDays();
	}
	
//	 ObjectMapper objectMapper = new ObjectMapper();
//	    String jacksonData = objectMapper.writeValueAsString(data);
	
//	private String getFrequenze(Map<Prodotto, Integer> mappa) 
//	{
//		Map<ProdottoDTOnoList, Integer> res = new HashMap<ProdottoDTOnoList, Integer>();
//		for(Prodotto prodotto : mappa.keySet())
//		{
//			
//		}
//			res.put(new ProdottoDTOnoList(prodotto), mappa.get(prodotto));
//		 ObjectMapper objectMapper = new ObjectMapper();
//	    String jacksonData = "";
//	    
//		try 
//		{
//			jacksonData = objectMapper.writeValueAsString(res);
//		} 
//		catch (JsonProcessingException e) 
//		{
//			
//			e.printStackTrace();
//		}
//
//		return jacksonData;
//	}
	
	

	
	/**
	 * Riconvertire 
	 * @return
	 */
	public abstract Ordine convertToOrdine();
	
	/**
	 * Controlla che la proprietà spedito abbia un valore. Inoltre se la data di acquisto è nulla,
	 *  non è logico che la proprietà spedito abbia un valore, quindi l'oggetto in questo caso non è valido 
	 * @return
	 */
	public boolean isValid()
	{
		if(spedito == null)
			return false;
		if(data_acquisto==null && spedito == true)
			return false;
		if(costo_spedizione==null || costo_spedizione<0)
			return false;
		if(prezzo_totale!=null && prezzo_totale<0)
			return false;
		return true;
	}
	
}

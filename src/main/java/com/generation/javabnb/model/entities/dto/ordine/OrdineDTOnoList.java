package com.generation.javabnb.model.entities.dto.ordine;

import com.generation.javabnb.model.entities.Ordine;
import com.generation.javabnb.model.entities.dto.prodotto.ProdottoDTOnoList;

import lombok.Getter;
import lombok.Setter;
/**
 * Convertire i dati in formato dto per avere tutte le prorpietà di tipo primitivo
 * In questo caso senza liste, quindi serve a
 * @author enrub
 *
 */
@Getter
@Setter
public class OrdineDTOnoList extends GenericOrdineDTO 
{
	private Integer id_cliente;

	public OrdineDTOnoList() {}
	
	public OrdineDTOnoList(Ordine ordine)
	{
		super(ordine);
		id_cliente = ordine.getCliente().getId();
				
	}

	@Override
	public Ordine convertToOrdine()
	{
		Ordine ordine = new Ordine();
		ordine.setId(id);
		ordine.setData_acquisto(data_acquisto);
		ordine.setSpedito(spedito);
		ordine.setCosto_spedizione(costo_spedizione);
		return ordine;
	}

	@Override
	public boolean isValid()
	{
		return super.isValid() && id_cliente!=null;
	}

	

}

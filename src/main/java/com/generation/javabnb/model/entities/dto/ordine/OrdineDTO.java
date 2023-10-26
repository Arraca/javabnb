package com.generation.javabnb.model.entities.dto.ordine;

import java.util.List;

import com.generation.javabnb.model.entities.Cliente;
import com.generation.javabnb.model.entities.Ordine;
import com.generation.javabnb.model.entities.Prodotto;
import com.generation.javabnb.model.entities.dto.prodotto.ProdottoDTO;
import com.generation.javabnb.model.entities.dto.prodotto.ProdottoDTOnoList;

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
public class OrdineDTO extends GenericOrdineDTO 
{
	private Integer id_cliente;
	private List<ProdottoDTOnoList> prodotti;
	
	public OrdineDTO() {}
	
	public OrdineDTO(Ordine ordine)
	{
		super(ordine);
		id_cliente = ordine.getCliente().getId();
		prodotti = ordine.getProdotti().stream().map(prodotto->	new ProdottoDTOnoList(prodotto)).toList();
				
	}

	@Override
	public Ordine convertToOrdine()
	{
		Ordine ordine = new Ordine();
		ordine.setId(id);
		ordine.setData_acquisto(data_acquisto);
		ordine.setSpedito(spedito);
		ordine.setCosto_spedizione(costo_spedizione);
		ordine.setProdotti(prodotti.stream().map(prod -> prod.convertToProdotto()).toList());
		return ordine;
	}

	@Override
	public boolean isValid()
	{
		return super.isValid() && id_cliente!=null;
	}
}

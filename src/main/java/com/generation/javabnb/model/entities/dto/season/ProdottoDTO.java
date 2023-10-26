package com.generation.javabnb.model.entities.dto.prodotto;

import java.util.List;

import com.generation.javabnb.model.entities.Prodotto;
import com.generation.javabnb.model.entities.dto.ordine.OrdineDTOnoList;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ProdottoDTO extends GenericProdottoDTO
{
	private List<OrdineDTOnoList>ordini;
	
	public ProdottoDTO() {}
	
	public ProdottoDTO(Prodotto prodotto)
	{
		super(prodotto);
		ordini = prodotto.getOrdini().stream().map(ordine -> new OrdineDTOnoList(ordine)).toList();
	}

	@Override
	public Prodotto convertToProdotto() 
	{
		Prodotto prodotto = new Prodotto();
		prodotto.setId(id);
		prodotto.setNome(nome);
		prodotto.setTipologia(tipologia);
		prodotto.setDescrizione(descrizione);
		prodotto.setUrl_foto(url_foto);
		prodotto.setPrezzo(prezzo);
		prodotto.setOrdini(ordini.stream().map(ord -> ord.convertToOrdine()).toList());
		return prodotto;
	}

}

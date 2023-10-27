package com.generation.javabnb.model.entities.dto.season;

import com.generation.javabnb.model.entities.Prodotto;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ProdottoDTOnoList extends GenericProdottoDTO
{
	public ProdottoDTOnoList() {}
	
	public ProdottoDTOnoList(Prodotto prodotto)
	{
		super(prodotto);
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
		prodotto.setAttivo(attivo);
		return prodotto;
		
	}
	
	

}

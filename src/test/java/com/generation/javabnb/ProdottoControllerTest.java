//package com.generation.javazon;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import java.nio.channels.NetworkChannel;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import com.generation.javazon.model.entities.Prodotto;
//import com.generation.javazon.model.entities.dto.prodotto.ProdottoDTOnoList;
//import com.generation.javazon.model.repositories.OrdineRepository;
//import com.generation.javazon.model.repositories.ProdottoRepository;
//import com.generation.javazon.test.utils.ProdottoTestUtilities;
//import com.jayway.jsonpath.JsonPath;
//@SpringBootTest
//(
//		webEnvironment = SpringBootTest.WebEnvironment.MOCK,
//		classes = JavazonApplication.class
//)
//@AutoConfigureMockMvc
//class ProdottoControllerTest
//{
//	@Autowired
//	MockMvc mock;
//	@Autowired 
//	ProdottoRepository pRepo;
//	@Autowired 
//	OrdineRepository oRepo;
//
//
//
//	/**
//	 * L'unico caso di malfunzionamento per il metodo getAll() è una mancata connessione con il database
//	 * in caso di corretta configurazione non da errore. In caso fosse incorretta l'eccezione è gestita 
//	 * (vedi handleCannotCreateTransactionException() in GestoreEccezioni.
//	 * @throws Exception
//	 */
//	@Test
//	void testGetAll() throws Exception
//	{
//		//L'unico errore possibile è quello per la comunicazione con il database (500)
//		
//		//IN CASO VADA TUTTO BENE
//		mock.perform(MockMvcRequestBuilders.get("/api/prodotti"))
//		.andExpect(status().isOk());
//		//.andExpect(MockMvcResultMatchers.content().json(ProdottoTestUtilities.jsonGetAllFull));
//		
//		mock.perform(MockMvcRequestBuilders.get("/api/prodotti/full"))
//		.andExpect(status().isOk());
//		//.andExpect(MockMvcResultMatchers.content().json(ProdottoTestUtilities.jsonGetAllFull));
//		
//		mock.perform(MockMvcRequestBuilders.get("/api/prodotti/nolist"))
//		.andExpect(status().isOk());
//		//.andExpect(MockMvcResultMatchers.content().json(ProdottoTestUtilities.jsonGetAllNoList));
//
//	}
//	/**
//	 * Il metodo getOneById() può fallire in caso di:
//	 * - prodotto con quell'ID non presente nel database.
//	 * - parametro ID nell'URI non numerico.
//	 * - parametro ID nell'URI mancante.
//	 * @throws Exception
//	 */
//	@Test
//	void testGetOneById() throws Exception
//	{
//		//IN CASO VADA TUTTO BENE
//		mock.perform(MockMvcRequestBuilders.get("/api/prodotti/2"))
//		.andExpect(status().isOk());
//		//.andExpect(MockMvcResultMatchers.content().json(ProdottoTestUtilities.jsonGetOne2Full));
//		
//		mock.perform(MockMvcRequestBuilders.get("/api/prodotti/2/full"))
//		.andExpect(status().isOk());
//		//.andExpect(MockMvcResultMatchers.content().json(ProdottoTestUtilities.jsonGetOne2Full));
//
//		mock.perform(MockMvcRequestBuilders.get("/api/prodotti/2/nolist"))
//		.andExpect(status().isOk());
//		//.andExpect(MockMvcResultMatchers.content().json(ProdottoTestUtilities.jsonGetOne2NoList));
//		
//		//PRODOTTO CON QUELL'ID NON PRESENTE NEL DATABASE
//		mock.perform(MockMvcRequestBuilders.get("/api/prodotti/250000"))
//		.andExpect(status().isNotFound())
//		.andExpect(MockMvcResultMatchers.content().string(ProdottoTestUtilities.notPresent404));
//		
//		//ID NON NUMERICO
//		mock.perform(MockMvcRequestBuilders.get("/api/prodotti/paperino"))
//		.andExpect(status().isBadRequest())
//		.andExpect(MockMvcResultMatchers.content().string(ProdottoTestUtilities.notNumeric400));
//
//		//ID MANCANTE		
//		mock.perform(MockMvcRequestBuilders.get("/api/prodotti/ "))
//		.andExpect(status().isBadRequest())
//		.andExpect(MockMvcResultMatchers.content().string(ProdottoTestUtilities.idNotPresent400));
//
//	}
//	/**
//	 * Controlla che il metodo funzioni e i suoi possibili fallimenti.
//	 * Fallisce nel caso in cui:
//	 * - Il prodotto da inserire non sia valido
//	 * - Il jason non sia enl formato corretto
//	 * - Mappatura sbagliata (es. /api/prodotti/12)
//	 * @throws Exception
//	 */
//	@Test
//	void testInsertProdotto() throws Exception
//	{
//		//NEL CASO VADA A BUON FINE
//		ProdottoDTOnoList dtoValido = new ProdottoDTOnoList();
//		dtoValido.setNome("Lavalamp");
//		dtoValido.setTipologia("Design");
//		dtoValido.setDescrizione("Che belle le lavalamp");
//		dtoValido.setUrl_foto("sono una foto");
//		dtoValido.setPrezzo(21.50);
//		dtoValido.setAttivo(true);
//		String dtoValidoJsonizzato = ProdottoTestUtilities.asJsonString(dtoValido);
//		System.out.println(dtoValidoJsonizzato);
//	     MvcResult result = mock.perform( MockMvcRequestBuilders
//	      .post("/api/prodotti")                    //REQUEST POST
//	      .content(dtoValidoJsonizzato)            //CONTENUTO DEL BODY CORRISPONDE AL NOSTRO JSON
//	      .contentType(MediaType.APPLICATION_JSON)
//	      .accept(MediaType.APPLICATION_JSON))
//	        .andExpect(status().isOk())
//	        .andReturn();
//		//PER CANCELLARE L'ELEMENTO CREATO DURANTE IL TEST
//		Integer id = JsonPath.read(result.getResponse().getContentAsString(), "$.id");//RICAVO L'ID
//		System.out.println(result.getResponse().getContentAsString());
//		pRepo.deleteById(id);//deleteById(ID)
//		
//		
//		//NEL CASO DI UN PRODOTTO (DTO) NON VALIDO
//		ProdottoDTOnoList dtoNonValido = new ProdottoDTOnoList();
//		dtoNonValido.setNome("Lavalamp");
//		dtoNonValido.setTipologia("Design");
//		dtoNonValido.setDescrizione("Che belle le lavalamp");
//		dtoNonValido.setUrl_foto("sono una foto");
//		//dtoNonValido.setPrezzo(21.50);
//		dtoNonValido.setAttivo(true);
//		String dtoNonValidoJsonizzato = ProdottoTestUtilities.asJsonString(dtoNonValido);
//
//	     mock.perform( MockMvcRequestBuilders
//	   	      .post("/api/prodotti")                    //REQUEST POST
//	   	      .content(dtoNonValidoJsonizzato)            //CONTENUTO DEL BODY CORRISPONDE AL NOSTRO JSON
//	   	      .contentType(MediaType.APPLICATION_JSON)
//	   	      .accept(MediaType.APPLICATION_JSON))
//	   	        .andExpect(status().isNotAcceptable())
//	   	        .andExpect(MockMvcResultMatchers.content().string(ProdottoTestUtilities.prodottoNonValido));
//	     
//	    //JSON ROTTO o NON CORRETTO
//	     
//	     mock.perform( MockMvcRequestBuilders
//		   	      .post("/api/prodotti")                    //REQUEST POST
//		   	      .content("ciao")            //CONTENUTO DEL BODY CORRISPONDE AL NOSTRO JSON
//		   	      .contentType(MediaType.APPLICATION_JSON)
//		   	      .accept(MediaType.APPLICATION_JSON))
//		   	        .andExpect(status().isBadRequest())
//		   	        .andExpect(MockMvcResultMatchers.content().string(ProdottoTestUtilities.jsonRotto));
//	     
//	    
//	     //MAPPATURA SBAGLIATA 
//	     mock.perform( MockMvcRequestBuilders
//		   	      .post("/api/prodotti/12")                    //REQUEST POST
//		   	      .content(dtoValidoJsonizzato)            //CONTENUTO DEL BODY CORRISPONDE AL NOSTRO JSON
//		   	      .contentType(MediaType.APPLICATION_JSON)
//		   	      .accept(MediaType.APPLICATION_JSON))
//		   	        .andExpect(status().isBadRequest())
//		   	        .andExpect(MockMvcResultMatchers.content().string(ProdottoTestUtilities.mappaturaSbagliata));
//
//	}
//	
//	/**
//	 * Verifica il corretto funzionamento e poi ogni caso specifico di fallimento.
//	 * Il metodo fallisce quando:
//	 * - Il prodotto non è valido (parametri non corretti o mancanti).
//	 * - Il Json non è nel formato corretto.
//	 * - Il prodotto da modificare non è presente nel DB
//	 * - Il parametro ID nell'url non è numerico
//	 * - Il parametro ID nell'url è mancante
//	 * @throws Exception
//	 */
//	@Test
//	void testUpdateOne() throws Exception
//	{
//		//NEL CASO DI FUNZIONAMENTO
//		ProdottoDTOnoList dtoValido = new ProdottoDTOnoList();
//		dtoValido.setNome("The Great Gatsby by F. Scott Fitzgerald");
//		dtoValido.setTipologia("Books");
//		dtoValido.setDescrizione("A literary classic - \"The Great Gatsby\" by F. Scott Fitzgerald.");
//		dtoValido.setUrl_foto("http://picsum.photos/seed/book/200");
//		dtoValido.setPrezzo(9.99);
//		dtoValido.setAttivo(true);
//		String dtoValidoJsonizzato = ProdottoTestUtilities.asJsonString(dtoValido);
//
//	     mock.perform( MockMvcRequestBuilders
//		   	      .put("/api/prodotti/5")                    //REQUEST POST
//		   	      .content(dtoValidoJsonizzato)            //CONTENUTO DEL BODY CORRISPONDE AL NOSTRO JSON
//		   	      .contentType(MediaType.APPLICATION_JSON)
//		   	      .accept(MediaType.APPLICATION_JSON))
//		   	        .andExpect(status().isOk());
//	     
//		//NEL CASO DI PRODOTTO (DTO) NON VALIDO
//		ProdottoDTOnoList dtoNonValido = new ProdottoDTOnoList();
//		dtoNonValido.setNome("The Great Gatsby by F. Scott Fitzgerald");
//		dtoNonValido.setTipologia("Books");
//		dtoNonValido.setDescrizione("A literary classic - \"The Great Gatsby\" by F. Scott Fitzgerald.");
//		dtoNonValido.setUrl_foto("http://picsum.photos/seed/book/200");
////		dtoNonValido.setPrezzo(9.99); //SENZA PREZZO
//		dtoNonValido.setAttivo(true);
//		String dtoNonValidoJsonizzato = ProdottoTestUtilities.asJsonString(dtoNonValido);
//
//	     mock.perform( MockMvcRequestBuilders
//		   	      .put("/api/prodotti/5")                    //REQUEST POST
//		   	      .content(dtoNonValidoJsonizzato)            //CONTENUTO DEL BODY CORRISPONDE AL NOSTRO JSON
//		   	      .contentType(MediaType.APPLICATION_JSON)
//		   	      .accept(MediaType.APPLICATION_JSON))
//		   	        .andExpect(status().isNotAcceptable())
//		   	        .andExpect(MockMvcResultMatchers.content().string(ProdottoTestUtilities.prodottoNonValidoModifica));
//
//	     //DTO ROTTO
//	     
//	     mock.perform( MockMvcRequestBuilders
//		   	      .put("/api/prodotti/5")                    //REQUEST POST
//		   	      .content("ciao")            //CONTENUTO DEL BODY CORRISPONDE AL NOSTRO JSON
//		   	      .contentType(MediaType.APPLICATION_JSON)
//		   	      .accept(MediaType.APPLICATION_JSON))
//		   	        .andExpect(status().isBadRequest())
//		   	        .andExpect(MockMvcResultMatchers.content().string(ProdottoTestUtilities.jsonRotto));
//	     
//	     //PRODOTTO CON QUESTO ID NON PRESENTE NEL DB
//	     
//	     mock.perform( MockMvcRequestBuilders
//		   	      .put("/api/prodotti/250000")                    //REQUEST POST
//		   	      .content(dtoValidoJsonizzato)            //CONTENUTO DEL BODY CORRISPONDE AL NOSTRO JSON
//		   	      .contentType(MediaType.APPLICATION_JSON)
//		   	      .accept(MediaType.APPLICATION_JSON))
//		   	        .andExpect(status().isNotFound())
//		   	        .andExpect(MockMvcResultMatchers.content().string(ProdottoTestUtilities.notPresent404Modifica));
//	     
//	     //PARAMETRO ID NON NUMERICO
//	     mock.perform( MockMvcRequestBuilders
//		   	      .put("/api/prodotti/paperino")                    //REQUEST POST
//		   	      .content(dtoValidoJsonizzato)            //CONTENUTO DEL BODY CORRISPONDE AL NOSTRO JSON
//		   	      .contentType(MediaType.APPLICATION_JSON)
//		   	      .accept(MediaType.APPLICATION_JSON))
//		   	        .andExpect(status().isBadRequest())
//		   	        .andExpect(MockMvcResultMatchers.content().string(ProdottoTestUtilities.notNumeric400));
//	     
//	     //PARAMETRO MANCANTE
//	     
//	     mock.perform( MockMvcRequestBuilders
//		   	      .put("/api/prodotti/ ")                    //REQUEST POST
//		   	      .content(dtoValidoJsonizzato)            //CONTENUTO DEL BODY CORRISPONDE AL NOSTRO JSON
//		   	      .contentType(MediaType.APPLICATION_JSON)
//		   	      .accept(MediaType.APPLICATION_JSON))
//		   	        .andExpect(status().isBadRequest())
//		   	        .andExpect(MockMvcResultMatchers.content().string(ProdottoTestUtilities.idNotPresent400));
//
//	}
//	
//	/**
//	 * Controlla il corretto funzionamento del metodo e i casi di fallimento.
//	 * Il metodo fallisce quando:
//	 * - non viene trovato il prodotto con quell'id
//	 * - il parametro non è numerico
//	 * - il parametro è mancante
//	 * @throws Exception
//	 */
//	@Test
//	void testDeleteOne() throws Exception
//	{
//		//CASO DI FUNZIONAMENTO
//		Prodotto prodotto =  pRepo.findById(1).get();
//		prodotto.setAttivo(true);
//		pRepo.save(prodotto);
//		mock.perform(MockMvcRequestBuilders
//				.delete("/api/prodotti/"+1))
//		.andExpect(status().isOk());
//		
//		//CASO IN CUI SIUA GIÀ INATTIVO
//		mock.perform(MockMvcRequestBuilders
//				.delete("/api/prodotti/"+prodotto.getId()))
//		.andExpect(status().isBadRequest())
//		.andExpect(MockMvcResultMatchers.content().string("Il prodotto che si desidera disattivare e gia inattivo! Cretino!"));
//		
//		//IN CASO NON TROVA IL PRODOTTO
//		mock.perform(MockMvcRequestBuilders
//				.delete("/api/prodotti/"+10000000))
//		.andExpect(status().isNotFound())
//		.andExpect(MockMvcResultMatchers.content().string("Non ci sono prodotti con id 10000000 nel database. Non poso effettuare la disattivazione."));
//
//		
//		//CASO IL PARAMETRO ID NON È NUMERICO
//		mock.perform(MockMvcRequestBuilders
//				.delete("/api/prodotti/paperino"))
//		.andExpect(status().isBadRequest())
//		.andExpect(MockMvcResultMatchers.content().string(ProdottoTestUtilities.notNumeric400));
//
//		//CASO IL PARAMETRO ID È MACNCANTE
//		mock.perform(MockMvcRequestBuilders
//				.delete("/api/prodotti/ "))
//		.andExpect(status().isBadRequest())
//		.andExpect(MockMvcResultMatchers.content().string(ProdottoTestUtilities.idNotPresent400));
//
//
//	}
//
//}

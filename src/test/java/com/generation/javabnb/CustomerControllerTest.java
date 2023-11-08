package com.generation.javabnb;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.generation.javabnb.model.dto.customer.CustomerDTOnoList;
import com.generation.javabnb.model.entities.Customer;
import com.generation.javabnb.model.repositories.CustomerRepository;


@SpringBootTest
(
		webEnvironment = SpringBootTest.WebEnvironment.MOCK,
		classes = JavabnbApplication.class
)
@AutoConfigureMockMvc
class CustomerControllerTest 
{
	@Autowired
	MockMvc mock;
	@Autowired
	CustomerRepository cRepo;
	
	String token ="Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJnaWFjb21vQGdtYWlsLmNvbSIsImV4cCI6MTY5OTM4NDExMSwiaWF0IjoxNjk5MzY2MTExfQ.sGnHhXh1YdqlnsU5VoCfODhbd2QfEoXQ_XVkAoKabOybCzkOFqdMS5xUqYtkyfEP2zx7kBHC0oVMHicRLp9pXA";
		
	String JsonUsername="{\r\n"
			+ "    \"id\": 1,\r\n"
			+ "    \"name\": \"Emily\",\r\n"
			+ "    \"surname\": \"Green\",\r\n"
			+ "    \"dob\": \"1993-07-28\",\r\n"
			+ "    \"username\": \"emily.green@example.com\",\r\n"
			+ "    \"bookings\": [\r\n"
			+ "        {\r\n"
			+ "            \"id\": 1,\r\n"
			+ "            \"checkIn\": \"2023-10-28\",\r\n"
			+ "            \"checkOut\": \"2023-10-31\",\r\n"
			+ "            \"totalPrice\": 500.75,\r\n"
			+ "            \"saved\": true,\r\n"
			+ "            \"room\": {\r\n"
			+ "                \"id\": 1,\r\n"
			+ "                \"name\": \"Camera Standard\",\r\n"
			+ "                \"capacity\": 2,\r\n"
			+ "                \"base_price\": 120.0,\r\n"
			+ "                \"notes\": \"Vista sulla cittÃ \",\r\n"
			+ "                \"img_url\": \"https://sthotelsmalta.com/wp-content/uploads/2022/06/modern-luxury-bedroom-suite-and-bathroom-with-working-table-2048x1327.jpg\",\r\n"
			+ "                \"valid\": true\r\n"
			+ "            },\r\n"
			+ "            \"valid\": true\r\n"
			+ "        },\r\n"
			+ "        {\r\n"
			+ "            \"id\": 2,\r\n"
			+ "            \"checkIn\": \"2023-11-05\",\r\n"
			+ "            \"checkOut\": \"2023-11-10\",\r\n"
			+ "            \"totalPrice\": 600.0,\r\n"
			+ "            \"saved\": false,\r\n"
			+ "            \"room\": {\r\n"
			+ "                \"id\": 2,\r\n"
			+ "                \"name\": \"Camera Deluxe\",\r\n"
			+ "                \"capacity\": 4,\r\n"
			+ "                \"base_price\": 220.5,\r\n"
			+ "                \"notes\": \"Vista panoramica\",\r\n"
			+ "                \"img_url\": \"https://www.italianbark.com/wp-content/uploads/2018/01/hotel-room-design-trends-italianbark-interior-design-blog.jpg\",\r\n"
			+ "                \"valid\": true\r\n"
			+ "            },\r\n"
			+ "            \"valid\": true\r\n"
			+ "        }\r\n"
			+ "    ],\r\n"
			+ "    \"valid\": true\r\n"
			+ "}";
	
	
	String DatiCustomerNonValido="{\r\n"
			+ "    \"id\": 8,\r\n"
			+ "    \"name\": \"TestInsert\",\r\n"
			+ "    \"surname\": \"Customer\",\r\n"
			+ "    \"dob\": \"2023-02-06"
			+ "    \"username\": \"test.customer@example.com\",\r\n"
			+ "    \"valid\": true\r\n"
			+ "}";
	
	
	String JsonCustomerNonValido="{\r\n"
			+ "    \"name\": \"Test\",\r\n"
			+ "    \"surname\":,\r\n"
			+ "    \"dob\": \"1995-07-28\",\r\n"
			+ "    \"username\": \"test.customer@example.com\",\r\n"
			+ "    \"valid\": true\r\n"
			+ "}";
	
	String JsonCustomer2Valido="{\r\n"
			+ "    \"id\": 2,\r\n"
			+ "    \"name\": \"Jane\",\r\n"
			+ "    \"surname\": \"Doe\",\r\n"
			+ "    \"dob\": \"1988-09-22\",\r\n"
			+ "    \"username\": \"janedoe@example.com\",\r\n"
			+ "    \"valid\": true\r\n"
			+ "}";
	
	String JsonCustomer2NonValido="{\r\n"
			+ "    \"id\": 2,\r\n"
			+ "    \"name\": \"Jan"
			+ "    \"surname\": \"Doe\",\r\n"
			+ "    \"dob\": \"1988-09-22\",\r\n"
			+ "    \"username\": \"janedoe@example.com\",\r\n"
			+ "    \"valid\": true\r\n"
			+ "}";
	
	private String asJsonString(Object obj) 
    {
        try 
        {
            ObjectMapper om =  new ObjectMapper();
            om.findAndRegisterModules();
            return om.writeValueAsString(obj);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
	
	@Test
	void testGetAllClienti() throws Exception 
	{
		
		//REQUEST CORRETTE
		mock.perform(MockMvcRequestBuilders.get("/customers").header("Authorization", token))
		.andExpect(status().isOk());
		
		mock.perform(MockMvcRequestBuilders.get("/customers/full").header("Authorization", token))
		.andExpect(status().isOk());
		
		mock.perform(MockMvcRequestBuilders.get("/customers/nolist").header("Authorization", token))
		.andExpect(status().isOk());
		
	}
	
	@Test
	void testGetOneById() throws Exception
	{
		//IN CASO VADANO BENE
		mock.perform(MockMvcRequestBuilders.get("/customers/3").header("Authorization", token))
		.andExpect(status().isOk());
		//.andExpect(MockMvcResultMatchers.content().json(ClienteControllerUtilities.cliente3FullJson));
		
		//IN CASO VADANO BENE
		mock.perform(MockMvcRequestBuilders.get("/customers/3/full").header("Authorization", token))
		.andExpect(status().isOk());
		//.andExpect(MockMvcResultMatchers.content().json(ClienteControllerUtilities.cliente3FullJson));
		
		//IN CASO VADANO BENE
		mock.perform(MockMvcRequestBuilders.get("/customers/3/nolist").header("Authorization", token))
		.andExpect(status().isOk());
		//.andExpect(MockMvcResultMatchers.content().json(ClienteControllerUtilities.cliente3FullJson));

		//PARAMETRO MANCANTE
		mock.perform(MockMvcRequestBuilders.get("/customers/ ").header("Authorization", token))
		.andExpect(status().isBadRequest())
		.andExpect(MockMvcResultMatchers.content().string("Parametro mancante nel'URI. Inserisci il parametro babbeo!"));
		
		//PARAMETRO NON NUMERICO
		mock.perform(MockMvcRequestBuilders.get("/customers/paperino").header("Authorization", token))
		.andExpect(status().isBadRequest())
		.andExpect(MockMvcResultMatchers.content().string("Occhio al parametro in ingresso."));
		
		//IN CASO DI MANCATA CORRISPONDENZA DELL'ID
		Integer idTest = 250000000;
		
		mock.perform(MockMvcRequestBuilders.get("/customers/"+idTest).header("Authorization", token))
		.andExpect(status().isNotFound())
		.andExpect(MockMvcResultMatchers.content().string("Non ci sono users con id "+idTest+" nel DB"));
		
		 
	}

	@Test
	void testGetCustomerByUsername() throws Exception
	{
		mock.perform(MockMvcRequestBuilders.get("/customers/emily.green@example.com/username").header("Authorization", token))
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.content().json(JsonUsername));
		
		//PARAMETRO MANCANTE
		String usernameTest=" ";
		mock.perform(MockMvcRequestBuilders.get("/customers/"+usernameTest+"/username").header("Authorization", token))
		.andExpect(status().isNotFound())
		.andExpect(MockMvcResultMatchers.content().string("Non ci sono users con email "+usernameTest+" nel DB"));	
		
		//PARAMETRO SBAGLIATO
		String usernameTest2="janedoe";
		mock.perform(MockMvcRequestBuilders.get("/customers/"+usernameTest2+"/username").header("Authorization", token))
		.andExpect(status().isNotFound())
		.andExpect(MockMvcResultMatchers.content().string("Non ci sono users con email "+usernameTest2+" nel DB"));
		
	}

	@Transactional
	@Test
	void testInsertCustomer() throws Exception
	{
		//DATI CORRETTI E JSON CORRETTO
		CustomerDTOnoList dtoValido= new CustomerDTOnoList();
		dtoValido.setName("TestInsert");
		dtoValido.setSurname("Customer");
		dtoValido.setDob(LocalDate.of(2023,02,06));
		dtoValido.setUsername("test.customer@example.com");
		
		String dtoJsonizzatoValido = asJsonString(dtoValido);
		mock.perform( MockMvcRequestBuilders
			      .post("/customers").header("Authorization", token)							
			      .content(dtoJsonizzatoValido)				
			      .contentType(MediaType.APPLICATION_JSON) 
			      .accept(MediaType.APPLICATION_JSON))		
		     	  .andExpect(status().isOk());			
		
		//ELIMINAZIONE DEL CUSTOMER INSERITO PER IL TEST
		cRepo.deleteByUsername("test.customer@example.com");
		
		
		
		//DATO INSERITI DELL UTENTE NON VALIDI
		CustomerDTOnoList dtoNonValido= new CustomerDTOnoList();
		dtoNonValido.setName("TestInsert");
		dtoNonValido.setSurname("");
		dtoNonValido.setDob(LocalDate.of(2023,02,06));
		dtoNonValido.setUsername("test.customer@example.com");
		
		String dtoJsonizzatoNonValido = asJsonString(dtoNonValido);
		mock.perform( MockMvcRequestBuilders
			      .post("/customers").header("Authorization", token)							
			      .content(dtoJsonizzatoNonValido)				
			      .contentType(MediaType.APPLICATION_JSON) 
			      .accept(MediaType.APPLICATION_JSON))		
		     	  .andExpect(status().isNotAcceptable());	
		
		
		//JSON ERRATO
		mock.perform( MockMvcRequestBuilders
			      .post("/customers").header("Authorization", token)							
			      .content(JsonCustomerNonValido)				
			      .contentType(MediaType.APPLICATION_JSON) 
			      .accept(MediaType.APPLICATION_JSON))		
		     	  .andExpect(status().isBadRequest());
	}
	
	void testModifyCustomer() throws Exception
	{
		//DATI CORRETTI
		
		mock.perform( MockMvcRequestBuilders
			      .put("/customers/2").header("Authorization", token)						
			      .content(JsonCustomer2Valido)				
			      .contentType(MediaType.APPLICATION_JSON) 
			      .accept(MediaType.APPLICATION_JSON))
		     		.andExpect(status().isOk());
		
		//CUSTOMER NON PRESENTE SUL DB
		Integer id=999;
		mock.perform( MockMvcRequestBuilders
			      .put("/customers/999").header("Authorization", token)						
			      .content(JsonCustomer2Valido)				
			      .contentType(MediaType.APPLICATION_JSON) 
			      .accept(MediaType.APPLICATION_JSON))
		     		.andExpect(status().isNotFound())
		     		.andExpect(MockMvcResultMatchers.content().string("Non ci sono users con id "+id+" nel DB"));
		
		//JSON ERRATO
		mock.perform( MockMvcRequestBuilders
			      .put("/customers/2").header("Authorization", token)						
			      .content(JsonCustomer2NonValido)				
			      .contentType(MediaType.APPLICATION_JSON) 
			      .accept(MediaType.APPLICATION_JSON))
		     		.andExpect(status().isBadRequest())
		     		.andExpect(MockMvcResultMatchers.content().string("Il JSON non e in formato corretto!"));
		
	}
	
	@Transactional
	@Test
	void testDeleteCustomer() throws Exception 	
	{
		

		Customer toDelete = new Customer();
		toDelete.setUsername("aaaaaaaaa@gmail.com");
		toDelete = cRepo.save(toDelete);
		
		//CUSTOMER trovato ed eliminato
		mock.perform( MockMvcRequestBuilders
			      .delete("/customers/"+toDelete.getId()).header("Authorization", token)								
			      .contentType(MediaType.APPLICATION_JSON) )
		     		.andExpect(status().isOk());		
		
		//Customer non trovato su db 404 no such element
		Integer id=9999;
		mock.perform( MockMvcRequestBuilders
			      .delete("/customers/9999").header("Authorization", token)							
			      .contentType(MediaType.APPLICATION_JSON)) 
		     	  	.andExpect(status().isNotFound())  
		     	  	.andExpect(MockMvcResultMatchers.content().string("Non ci sono users con email "+id+" nel DB."));
		
		
	}
		
		
	
	
	
	
	
	
}

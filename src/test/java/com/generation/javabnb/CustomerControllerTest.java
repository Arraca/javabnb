package com.generation.javabnb;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.generation.javabnb.JavabnbApplication;
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
	
	String token = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJlbnJpY29AZ21haWwuY29tIiwiZXhwIjoxNjk4NzU4OTI2LCJpYXQiOjE2OTg3NDA5MjZ9.OI6Fr8eoNidU81dQyMMmQvQXFh7hcyHH4I0Vg82PohxxkafsrnejHyzt2pf7SzdhiAhK1lW7tUXhYbcpot3wUA";
	
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

	

}

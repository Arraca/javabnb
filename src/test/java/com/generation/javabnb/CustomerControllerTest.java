package com.generation.javabnb;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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
	
	@Test
	void testGetAllClienti() throws Exception 
	{
		//REQUEST CORRETTE
		mock.perform(MockMvcRequestBuilders.get("/customers"))
		.andExpect(status().isOk());
		
		mock.perform(MockMvcRequestBuilders.get("/customers/full"))
		.andExpect(status().isOk());
		
		mock.perform(MockMvcRequestBuilders.get("/customers/nolist"))
		.andExpect(status().isOk());
		
	}

	

}

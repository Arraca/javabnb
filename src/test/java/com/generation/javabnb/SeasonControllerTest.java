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
import com.generation.javabnb.model.dto.season.SeasonDTO;
import com.generation.javabnb.model.entities.Customer;
import com.generation.javabnb.model.entities.Season;
import com.generation.javabnb.model.repositories.SeasonRepository;



@SpringBootTest
(
		webEnvironment = SpringBootTest.WebEnvironment.MOCK,
		classes = JavabnbApplication.class
)
@AutoConfigureMockMvc
class SeasonControllerTest 
{
	@Autowired
	MockMvc mock;
	
	@Autowired
	SeasonRepository sRepo;
	
	String token ="Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJnaWFjb21vQGdtYWlsLmNvbSIsImV4cCI6MTY5OTIzNTg1OCwiaWF0IjoxNjk5MjE3ODU4fQ.0YYt5ifvyJEplc-UAu2a8CCiq5FS7pwwsTttMkbAl8JkXipZBr_AvdxywljL8hDeTWi6geWZ45YbMmHTPrHYnw";
	
	
	String JsonSeason="{\r\n"
			+ "    \"id\": 2,\r\n"
			+ "    \"name\": \"Natale\",\r\n"
			+ "    \"begin\": \"2023-12-20\",\r\n"
			+ "    \"end\": \"2023-12-28\",\r\n"
			+ "    \"percent\": 0.2,\r\n"
			+ "    \"valid\": true\r\n"
			+ "}";
	
	String JsonSeasonErrato="{\r\n"
			+ "    \"id\": 2,\r\n"
			+ "    \"name\": \"Natale\",\r\n"
			+ "    \"begin\": \"2023-12-20\",\r\n"
			+ "    \"en"
			+ "    \"percent\": 0.2,\r\n"
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
	void testGetAllSeason() throws Exception
	{

		mock.perform(MockMvcRequestBuilders.get("/season").header("Authorization", token))
		.andExpect(status().isOk());
		
	}
	
	
	@Test
	void testGetSeasonById() throws Exception
	{
		//DATI CORRETTI
		mock.perform(MockMvcRequestBuilders.get("/season/2").header("Authorization", token))
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.content().json(JsonSeason));
		
		//PARAMETRO NON PRESENTE SUL DB
		Integer id=999;
		mock.perform(MockMvcRequestBuilders.get("/season/999").header("Authorization", token))
		.andExpect(status().isNotFound())
		.andExpect(MockMvcResultMatchers.content().string("Non è presente una season nel database con id:"+id+"."));
		
		
		//PARAMETRO NON NUMERICO
		mock.perform(MockMvcRequestBuilders.get("/season/paperino").header("Authorization", token))
		.andExpect(status().isBadRequest())
		.andExpect(MockMvcResultMatchers.content().string("Occhio al parametro in ingresso."));
		
	}
	
	
	@Transactional
	@Test
	void testInsertSeason() throws Exception
	{
		
		//TUTTO OK :)
		SeasonDTO s = new SeasonDTO();
		s.setName("SeasonTest");
		s.setBegin(LocalDate.of(2023,02,06));
		s.setEnd(LocalDate.of(2023,02,12));
		s.setPercent(0.10);
		
		String dtoValido = asJsonString(s);
		
		mock.perform( MockMvcRequestBuilders
			      .post("/season").header("Authorization", token)							
			      .content(dtoValido)				
			      .contentType(MediaType.APPLICATION_JSON) 
			      .accept(MediaType.APPLICATION_JSON))		
		     	  .andExpect(status().isOk());
		
		sRepo.deleteByName("SeasonTest");
		
		
		//DATI INSERITI NON VALIDI
		SeasonDTO s1 = new SeasonDTO();
		s1.setName("");
		s1.setBegin(LocalDate.of(2023,01,06));
		s1.setEnd(LocalDate.of(2023,01,12));
		s1.setPercent(0.1);
		
		String dtoNonValido = asJsonString(s1);
		
		mock.perform( MockMvcRequestBuilders
			      .post("/season").header("Authorization", token)							
			      .content(dtoNonValido)				
			      .contentType(MediaType.APPLICATION_JSON) 
			      .accept(MediaType.APPLICATION_JSON))		
		     	  .andExpect(status().isNotFound())
		     	  .andExpect(MockMvcResultMatchers.content().string( "La season che vuoi inserire non è valida, controlla di nuovo i dati"));
                  
		
		//JSON ERRATO
		mock.perform( MockMvcRequestBuilders
			      .post("/season").header("Authorization", token)						
			      .content(JsonSeasonErrato)				
			      .contentType(MediaType.APPLICATION_JSON) 
			      .accept(MediaType.APPLICATION_JSON))
		     		.andExpect(status().isBadRequest())
		     		.andExpect(MockMvcResultMatchers.content().string("Il JSON non e in formato corretto!"));
		
	}
	
	@Test
	void testModifySeason() throws Exception
	{

		mock.perform( MockMvcRequestBuilders
			      .put("/season/2").header("Authorization", token)						
			      .content(JsonSeason)				
			      .contentType(MediaType.APPLICATION_JSON) 
			      .accept(MediaType.APPLICATION_JSON))
		     		.andExpect(status().isOk());
		
		//CUSTOMER NON PRESENTE SUL DB
		Integer id=999;
		mock.perform( MockMvcRequestBuilders
			      .put("/season/999").header("Authorization", token)						
			      .content(JsonSeason)				
			      .contentType(MediaType.APPLICATION_JSON) 
			      .accept(MediaType.APPLICATION_JSON))
		     		.andExpect(status().isNotFound())
		     		.andExpect(MockMvcResultMatchers.content().string("Impossibile trovare la season con id" +id+" che vuoi modificare."));
		
		//JSON ERRATO
		mock.perform( MockMvcRequestBuilders
			      .put("/season/2").header("Authorization", token)						
			      .content(JsonSeasonErrato)				
			      .contentType(MediaType.APPLICATION_JSON) 
			      .accept(MediaType.APPLICATION_JSON))
		     		.andExpect(status().isBadRequest())
		     		.andExpect(MockMvcResultMatchers.content().string("Il JSON non e in formato corretto!"));
		
	}
	
	@Test
	void testDeleteSeason() throws Exception
	{
		Season seasonToDelete = new Season();
		seasonToDelete = sRepo.save(seasonToDelete);
		
		mock.perform( MockMvcRequestBuilders
			      .delete("/season/"+seasonToDelete.getId()).header("Authorization", token)								
			      .contentType(MediaType.APPLICATION_JSON))
		     		.andExpect(status().isOk());
		
		Integer id=9999;
		mock.perform( MockMvcRequestBuilders
			      .delete("/season/9999").header("Authorization", token)							
			      .contentType(MediaType.APPLICATION_JSON)) 
		     	  	.andExpect(status().isNotFound()) 
				    .andExpect(MockMvcResultMatchers.content().string("Impossibile trovare la season con id" +id+" che vuoi eliminare."));
		
		
		
	}
}

package com.generation.javabnb;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

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
import com.generation.javabnb.model.dto.room.RoomDTOnoList;
import com.generation.javabnb.model.entities.Room;
import com.generation.javabnb.model.repositories.RoomRepository;

@SpringBootTest
(
		webEnvironment = SpringBootTest.WebEnvironment.MOCK,
		classes = JavabnbApplication.class
)
@AutoConfigureMockMvc
class RoomControllerTest 
{
	@Autowired
	MockMvc mock;
	@Autowired
	RoomRepository rRepo;
	
	String token ="Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJnaWFjb21vQGdtYWlsLmNvbSIsImV4cCI6MTY5OTIzNTg1OCwiaWF0IjoxNjk5MjE3ODU4fQ.0YYt5ifvyJEplc-UAu2a8CCiq5FS7pwwsTttMkbAl8JkXipZBr_AvdxywljL8hDeTWi6geWZ45YbMmHTPrHYnw";
	
	String JsonRoom = "{\r\n"
			+ "    \"id\": 2,\r\n"
			+ "    \"name\": \"Camera Deluxe\",\r\n"
			+ "    \"capacity\": 4,\r\n"
			+ "    \"base_price\": 220.5,\r\n"
			+ "    \"notes\": \"Vista panoramica\",\r\n"
			+ "    \"img_url\": \"image2.jpg\",\r\n"
			+ "    \"valid\": true\r\n"
			+ "}";
	
	String JsonRoomErrato="{\r\n"
			+ "    \"id\": 2,\r\n"
			+ "    \"name\": \"Camera Deluxe\",\r\n"
			+ "    \"capac"
			+ "    \"base_price\": 220.5,\r\n"
			+ "    \"notes\": \"Vista panoramica\",\r\n"
			+ "    \"img_url\": \"image2.jpg\",\r\n"
			+ "    \"valid\": true\r\n"
			+ "}";
	
	String JsonRoomPut = "{\r\n"
			+ "    \"id\": 1,\r\n"
			+ "    \"name\": \"Camera Standard\",\r\n"
			+ "    \"capacity\": 2,\r\n"
			+ "    \"base_price\": 120.0,\r\n"
			+ "    \"notes\": \"Vista sulla cittÃ \",\r\n"
			+ "    \"img_url\": \"image1.jpg\",\r\n"
			+ "    \"valid\": true\r\n"
			+ "}";
	
	String JsonRoomPutErrato = "{\r\n"
			+ "    \"id\": 1,\r\n"
			+ "    \"name\": \"Camera Standard\",\r\n"
			+ "    \"capacity\": 2,\r\n"
			+ "    \"base_price\": 120.0,\r\n"
			+ "    \"notes\": \"Vista sulla cittÃ \",\r\n"
			+ "    \"img_url\": \"image1.jpg\",\r\n"
			+ "    \"bookings\": [\r\n"
			+ "        {\r\n"
			+ "            \"id\": 1,\r\n"
			+ "            \"checkIn\": \"2023-10-28\",\r\n"
			+ "            \"checkOut\": \"2023-10-31\",\r\n"
			+ "            \"totalPrice\": 500.75,\r\n"
			+ "            \"saved\": true,\r\n"
			+ "            \"season\": null,\r\n"
			+ "            \"r\n"
			+ "                \"username\": \"emily.green@example.com\",\r\n"
			+ "                \"valid\": true\r\n"
			+ "            },\r\n"
			+ "            \"valid\": true\r\n"
			+ "        }\r\n"
			+ "    ],\r\n"
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
	void testGetAllRooms() throws Exception
	{
		
		//REQUEST CORRETTE
		mock.perform(MockMvcRequestBuilders.get("/rooms").header("Authorization", token))
		.andExpect(status().isOk());
		
		mock.perform(MockMvcRequestBuilders.get("/rooms/full").header("Authorization", token))
		.andExpect(status().isOk());
		
		mock.perform(MockMvcRequestBuilders.get("/rooms/nolist").header("Authorization", token))
		.andExpect(status().isOk());
		
	}
	
	
	@Test
	void testGetOneRoom() throws Exception
	{
		//CHIAMATA A BUON FINE
		mock.perform(MockMvcRequestBuilders.get("/rooms/2").header("Authorization", token))
		.andExpect(status().isOk());
		
		mock.perform(MockMvcRequestBuilders.get("/rooms/2/full").header("Authorization", token))
		.andExpect(status().isOk());
		
		mock.perform(MockMvcRequestBuilders.get("/rooms/2").header("Authorization", token))
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.content().json(JsonRoom));
		
		//ID NON PRESENTE SUL DB
		Integer id=999;
		mock.perform(MockMvcRequestBuilders.get("/rooms/999").header("Authorization", token))
		.andExpect(status().isNotFound())
		.andExpect(MockMvcResultMatchers.content().string("Non ci sono stanze con id "+id+" nel DB."));
		
		mock.perform(MockMvcRequestBuilders.get("/rooms/999/full").header("Authorization", token))
		.andExpect(status().isNotFound())
		.andExpect(MockMvcResultMatchers.content().string("Non ci sono stanze con id "+id+" nel DB."));
		
		mock.perform(MockMvcRequestBuilders.get("/rooms/999/nolist").header("Authorization", token))
		.andExpect(status().isNotFound())
		.andExpect(MockMvcResultMatchers.content().string("Non ci sono stanze con id "+id+" nel DB."));
		
		//PARAMETRO NON NUMERICO
		mock.perform(MockMvcRequestBuilders.get("/rooms/paperino").header("Authorization", token))
		.andExpect(status().isBadRequest())
		.andExpect(MockMvcResultMatchers.content().string("Occhio al parametro in ingresso."));
		
		mock.perform(MockMvcRequestBuilders.get("/rooms/paperino/full").header("Authorization", token))
		.andExpect(status().isBadRequest())
		.andExpect(MockMvcResultMatchers.content().string("Occhio al parametro in ingresso."));
		
		mock.perform(MockMvcRequestBuilders.get("/rooms/paperino/nolist").header("Authorization", token))
		.andExpect(status().isBadRequest())
		.andExpect(MockMvcResultMatchers.content().string("Occhio al parametro in ingresso."));
		
	}
	
	
	@Transactional
	@Test
	void testInsertOneRoom() throws Exception
	{
		RoomDTOnoList r = new RoomDTOnoList();
		r.setName("NOMETEST");
		r.setCapacity(2);
		r.setBase_price(300.00);
		r.setNotes("NOMETEST");
		r.setImg_url("TEST.jpg");
		
		String roomValida = asJsonString(r);
		
		mock.perform( MockMvcRequestBuilders
			      .post("/rooms").header("Authorization", token)							
			      .content(roomValida)				
			      .contentType(MediaType.APPLICATION_JSON) 
			      .accept(MediaType.APPLICATION_JSON))		
		     	  .andExpect(status().isOk());			
		
		//ELIMINAZIONE DELLA ROOM INSERITA PER IL TEST
		rRepo.deleteByName("NOMETEST");
		
	}
	
	@Test
	void testModifyOneRoom() throws Exception
	{
//		mock.perform( MockMvcRequestBuilders
//			      .put("/rooms/1").header("Authorization", token)						
//			      .content(JsonRoomPut)				
//			      .contentType(MediaType.APPLICATION_JSON) 
//			      .accept(MediaType.APPLICATION_JSON))
//		     		.andExpect(status().isOk());
			
		//prenotazione non esistente sul db
		Integer id = 999;
			mock.perform( MockMvcRequestBuilders
				      .put("/rooms/999").header("Authorization", token)						
				      .content(JsonRoomPut)				
				      .contentType(MediaType.APPLICATION_JSON) 
				      .accept(MediaType.APPLICATION_JSON))
			     		.andExpect(status().isNotFound());
			
			//JSON ERRATO
			mock.perform( MockMvcRequestBuilders
				      .put("/rooms/1").header("Authorization", token)						
				      .content(JsonRoomPutErrato)				
				      .contentType(MediaType.APPLICATION_JSON) 
				      .accept(MediaType.APPLICATION_JSON))
			     		.andExpect(status().isBadRequest())
			     		.andExpect(MockMvcResultMatchers.content().string("Il JSON non e in formato corretto!"));
		
	}
	
	
	@Transactional
	@Test
	void deleteOneRoom() throws Exception
	{
		Room toDelete = new Room();
		toDelete.setName("NOMETESTROOM");
		toDelete = rRepo.save(toDelete);
		//CUSTOMER trovato ed eliminato
		mock.perform( MockMvcRequestBuilders
			      .delete("/rooms/"+toDelete.getId()).header("Authorization", token)								
			      .contentType(MediaType.APPLICATION_JSON) )
		     	  .andExpect(status().isOk());		
		
		//Room non trovata su db 404 no such element
		Integer id=9999;
		mock.perform( MockMvcRequestBuilders
			      .delete("/rooms/9999").header("Authorization", token)							
			      .contentType(MediaType.APPLICATION_JSON)) 
		     	  	.andExpect(status().isNotFound())  
		     	  	.andExpect(MockMvcResultMatchers.content().string("Non ci sono stanze con id "+id+" nel DB, non posso eseguire la modifica!"));
		
	}
	
	
}
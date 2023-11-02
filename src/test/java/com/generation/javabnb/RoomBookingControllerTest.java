package com.generation.javabnb;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.generation.javabnb.model.dto.roombooking.RoomBookingDTO;
import com.generation.javabnb.model.dto.roombooking.RoomBookingDTOnoCustomer;
import com.generation.javabnb.model.entities.RoomBooking;
import com.generation.javabnb.model.repositories.CustomerRepository;
import com.generation.javabnb.model.repositories.RoomBookingRepository;
import com.generation.javabnb.model.repositories.RoomRepository;
import com.generation.javabnb.model.repositories.SeasonRepository;


@SpringBootTest
(
		webEnvironment = SpringBootTest.WebEnvironment.MOCK,
		classes = JavabnbApplication.class
)
@AutoConfigureMockMvc
class RoomBookingControllerTest 
{
	@Autowired
	MockMvc mock;
	@Autowired
	RoomBookingRepository rBRepo;
	@Autowired
	RoomRepository rRepo;
	@Autowired
	CustomerRepository cRepo;
	@Autowired
	SeasonRepository sRepo;
	
	String token = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJnaWFjb21vQGdtYWlsLmNvbSIsImV4cCI6MTY5ODc4ODkyMCwiaWF0IjoxNjk4NzcwOTIwfQ.Lfgmj2OhKyiY4x48ojRWzt_wwHd5CWpM9LdPrNj_cQtpUAnwKmbVRTfgI6S2B427zZicVaiYe1TcowxV1xl2BA";
	
	
	String JsonOneRoomBooking="{\r\n"
			+ "    \"id\": 2,\r\n"
			+ "    \"checkIn\": \"2023-11-05\",\r\n"
			+ "    \"checkOut\": \"2023-11-10\",\r\n"
			+ "    \"totalPrice\": 600.0,\r\n"
			+ "    \"saved\": false,\r\n"
			+ "    \"season\": null,\r\n"
			+ "    \"room\": {\r\n"
			+ "        \"id\": 2,\r\n"
			+ "        \"name\": \"Camera Deluxe\",\r\n"
			+ "        \"capacity\": 4,\r\n"
			+ "        \"base_price\": 220.5,\r\n"
			+ "        \"notes\": \"Vista panoramica\",\r\n"
			+ "        \"img_url\": \"image2.jpg\",\r\n"
			+ "        \"valid\": true\r\n"
			+ "    },\r\n"
			+ "    \"customer\": {\r\n"
			+ "        \"id\": 1,\r\n"
			+ "        \"name\": \"Emily\",\r\n"
			+ "        \"surname\": \"Green\",\r\n"
			+ "        \"dob\": \"1993-07-28\",\r\n"
			+ "        \"username\": \"emily.green@example.com\",\r\n"
			+ "        \"valid\": true\r\n"
			+ "    },\r\n"
			+ "    \"valid\": true\r\n"
			+ "}";
	
	String JsonOneRoomBookingBrutto="{\r\n"
			+ "    \"id\": 2,\r\n"
			+ "    \"checkIn\": \"2023-11-05\",\r"
			+ "    \"checkOut\": \"2023-11-10\",\r\n"
			+ "    \"totalPrice\": 600.0,\r\n"
			+ "    \"saved\": false,\r\n"
			+ "    \"season\": null,\r\n"
			+ "    \"room\": {\r\n"
			+ "        \"id\": 2,\r\n"
			+ "        \"name\": \"Camera Deluxe\",\r\n"
			+ "        \"capacity\": 4,\r\n"
			+ "        \"base_price\": 220.5,\r\n"
			+ "        \"notes\": \"Vista panoramica\"\r\n"
			+ "        \"img_url\": \"image2.jpg\",\r\n"
			+ "        \"valid\": true\r\n"
			+ "    },\r\n"
			+ "    \"customer\": {\r\n"
			+ "        \"id\": 1,\r\n"
			+ "        \"name\": \"Emily\",\r\n"
			+ "        \"surn\": \"Green\",\r\n"                 //surname diventa surn
			+ "        \"dob\": \"1993-07-28\",\r\n"
			+ "        \"username\": \"emily.green@example.com\",\r\n"
			+ "        \"valid\": true\r\n"
			+ "    },\r\n"
			+ "    \"valid\": true\r\n"
			+ "}";
	
	
	
	
	
	@Test
	void testgetAllRoomBookings() throws Exception 
	{
		mock.perform(MockMvcRequestBuilders.get("/roombookings").header("Authorization", token))
		.andExpect(status().isOk());
		
		
		mock.perform(MockMvcRequestBuilders.get("/roombookings/nolist").header("Authorization", token))
		.andExpect(status().isOk());
	}
	
	
	
	@Test
	void getoneRoomBooking() throws Exception
	{
		// NEL CASO SIA TUTTO GIUSTO
		mock.perform(MockMvcRequestBuilders.get("/roombookings/2").header("Authorization", token))
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.content().json(JsonOneRoomBooking));	
		//PARAMETRO NON NUMERICO
		mock.perform(MockMvcRequestBuilders.get("/roombookings/paperino").header("Authorization", token))
		.andExpect(status().isBadRequest())
		.andExpect(MockMvcResultMatchers.content().string("Occhio al parametro in ingresso."));
		
		//PARAMETRO NON PRESENTE NEL DB 
		mock.perform(MockMvcRequestBuilders.get("/roombookings/20000").header("Authorization", token))
		.andExpect(status().isNotFound())
		.andExpect(MockMvcResultMatchers.content().string("Prenotazione non trovata"));
		
	}
	
	private String asJsonString(Object obj) 
    {
        try 
        {
            return new ObjectMapper().writeValueAsString(obj);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
	
	
	@Test
	void insertOneRoomBooking() throws Exception
	{
		//INSERIMENTO DI UNA PRENOTAZIONE 
		RoomBookingDTO prenotazioneValida = new RoomBookingDTO();
//		prenotazioneValida.setCheckIn(LocalDate.of(2023,02,06));
//		prenotazioneValida.setCheckOut(LocalDate.of(2023,02,10));
		
		String dtoJsonizzatoValido = asJsonString(prenotazioneValida);
		mock.perform( MockMvcRequestBuilders
			      .post("/roombookings/2/room/2/customer").header("Authorization", token)						
			      .content("{\"checkIn\":\"2023-02-06\",\"checkOut\":\"2023-02-10\"}")				
			      .contentType(MediaType.APPLICATION_JSON) 
			      .accept(MediaType.APPLICATION_JSON))		
		     		.andExpect(status().isOk());		
		System.out.println("++++++++++++++++++++++++"+dtoJsonizzatoValido+"+++++++++++++++++++++++++"); 
		RoomBookingDTO prenotazioneNonValida = new RoomBookingDTO();
		prenotazioneNonValida.setCheckIn(LocalDate.of(2023,02,06));
		prenotazioneNonValida.setCheckOut(LocalDate.of(2023,02,10));
		
		String dtoNonValidoJson = asJsonString(prenotazioneNonValida);
		mock.perform( MockMvcRequestBuilders
			      .post("/roombookings/2/room/999/customer").header("Authorization", token)							
			      .content(dtoNonValidoJson)				
			      .contentType(MediaType.APPLICATION_JSON) 
			      .accept(MediaType.APPLICATION_JSON))		
		     		.andExpect(status().isBadRequest());	
		
		//json incorretto
		String dtoNonValidoJson2 = asJsonString(prenotazioneNonValida);
		mock.perform( MockMvcRequestBuilders
			      .post("/roombookings/200/room/2/customer").header("Authorization", token)							
			      .content(dtoNonValidoJson2)				
			      .contentType(MediaType.APPLICATION_JSON) 
			      .accept(MediaType.APPLICATION_JSON))		
		     		.andExpect(status().isBadRequest());	
		
		//parametro mancante
		String dtoNonValidoJson3 = asJsonString(prenotazioneNonValida);
		mock.perform( MockMvcRequestBuilders
			      .post("/roombookings/2/room//customer").header("Authorization", token)							
			      .content(dtoNonValidoJson)				
			      .contentType(MediaType.APPLICATION_JSON) 
			      .accept(MediaType.APPLICATION_JSON))		
		     		.andExpect(status().isNotFound());	
		
		}
	
	@Test	
	void modifyOneRoomBooking() throws Exception 
	{
		mock.perform( MockMvcRequestBuilders
			      .put("/roombookings/2").header("Authorization", token)						
			      .content(JsonOneRoomBooking)				
			      .contentType(MediaType.APPLICATION_JSON) 
			      .accept(MediaType.APPLICATION_JSON))
		     		.andExpect(status().isOk());
			
		//prenotazione non esistente sul db
			mock.perform( MockMvcRequestBuilders
				      .put("/roombookings/999").header("Authorization", token)						
				      .content(JsonOneRoomBooking)				
				      .contentType(MediaType.APPLICATION_JSON) 
				      .accept(MediaType.APPLICATION_JSON))
			     		.andExpect(status().isNotFound())
			     		.andExpect(MockMvcResultMatchers.content().string("Prenotazione da modificare non trovata"));
			
			
			mock.perform( MockMvcRequestBuilders
				      .put("/roombookings/2").header("Authorization", token)						
				      .content(JsonOneRoomBookingBrutto)				
				      .contentType(MediaType.APPLICATION_JSON) 
				      .accept(MediaType.APPLICATION_JSON))
			     		.andExpect(status().isBadRequest())
			     		.andExpect(MockMvcResultMatchers.content().string("Il JSON non e in formato corretto!"));
			
	}	
	
	@Test
	void deleteOneRoomBooking() throws Exception
	{
		
		RoomBooking toDelete = new RoomBooking();
		
		toDelete = rBRepo.save(toDelete);
		//RoomBooking trovato ed eliminato
		mock.perform( MockMvcRequestBuilders
			      .delete("/roombookings/"+toDelete.getId()).header("Authorization", token)								
			      .contentType(MediaType.APPLICATION_JSON) )
		     		.andExpect(status().isOk());		
		
		//RoomBooking non trovato su db 404 no such element
		mock.perform( MockMvcRequestBuilders
			      .delete("/roombookings/9999").header("Authorization", token)							
			      .contentType(MediaType.APPLICATION_JSON)) 
		     	  	.andExpect(status().isNotFound())  
		     	  	.andExpect(MockMvcResultMatchers.content().string("Prenotazione da eliminare non trovata"));
		
	}	
		
		
		
		
		
		
		
		
	
}

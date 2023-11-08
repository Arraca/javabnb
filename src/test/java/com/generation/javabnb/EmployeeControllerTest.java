package com.generation.javabnb;

import static org.junit.jupiter.api.Assertions.*;
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
import com.generation.javabnb.model.dto.employee.EmployeeDTO;
import com.generation.javabnb.model.entities.Employee;
import com.generation.javabnb.model.repositories.EmployeeRepository;
import com.generation.javabnb.model.repositories.SeasonRepository;


@SpringBootTest
(
		webEnvironment = SpringBootTest.WebEnvironment.MOCK,
			classes = JavabnbApplication.class
)
@AutoConfigureMockMvc
class EmployeeControllerTest
{
	@Autowired
	MockMvc mock;
	
	@Autowired
	EmployeeRepository eRepo;
		
	String token ="Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJnaWFjb21vQGdtYWlsLmNvbSIsImV4cCI6MTY5OTIzNTg1OCwiaWF0IjoxNjk5MjE3ODU4fQ.0YYt5ifvyJEplc-UAu2a8CCiq5FS7pwwsTttMkbAl8JkXipZBr_AvdxywljL8hDeTWi6geWZ45YbMmHTPrHYnw";
	
	String JsonEmployee="{\r\n"
			+ "    \"id\": 1,\r\n"
			+ "    \"name\": \"Alice\",\r\n"
			+ "    \"surname\": \"Smith\",\r\n"
			+ "    \"dob\": \"1995-03-10\",\r\n"
			+ "    \"img_url\": \"alice_smith.jpg\",\r\n"
			+ "    \"username\": \"alice.smith@example.com\",\r\n"
			+ "    \"valid\": true\r\n"
			+ "}";
	
	String JsonEmployeeErrato="{\r\n"
			+ "    \"id\": 1,\r\n"
			+ "    \"name\": \"Alice\",\r\n"
			+ "    \"surname\": \"Smith\",\r\n"
			+ "    \"dob\": \"1995-03-10\",\r\n"
			+ "    \"img_url\": \"alice_smith.jpg\",\r\n"
			+ "    \"usern\n"
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
	void testGetAllEmployee() throws Exception
	{
		mock.perform(MockMvcRequestBuilders.get("/employees").header("Authorization", token))
		.andExpect(status().isOk());
		
	}
	
	
	@Test
	void testGetEmployeeById() throws Exception
	{
		//TUTTO OK
		mock.perform(MockMvcRequestBuilders.get("/employees/1").header("Authorization", token))
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.content().json(JsonEmployee));
		
		//PARAMETRO NON PRESENTE NEL DB
		Integer id = 999;
		mock.perform(MockMvcRequestBuilders.get("/employees/999").header("Authorization", token))
		.andExpect(status().isNotFound())
		.andExpect(MockMvcResultMatchers.content().string("Non ci sono users con id "+id+" nel DB"));
		
		//PARAMETRO NON NUMERICO
		mock.perform(MockMvcRequestBuilders.get("/employees/paperino").header("Authorization", token))
		.andExpect(status().isBadRequest())
		.andExpect(MockMvcResultMatchers.content().string("Occhio al parametro in ingresso."));
		
		
		
	}
	
	
	@Transactional
	@Test
	void testInsertEmployee() throws Exception
	{
		//TUTTO OK
		EmployeeDTO e = new EmployeeDTO();
		e.setName("TEST");
		e.setSurname("SURNAMETEST");
		e.setDob(LocalDate.of(2023,02,06));
		e.setImg_url("TEST.jpg");
		e.setUsername("testemployee@gmail.com");
		
		String employeeValido = asJsonString(e);
		
		mock.perform( MockMvcRequestBuilders
			      .post("/employees").header("Authorization", token)							
			      .content(employeeValido)				
			      .contentType(MediaType.APPLICATION_JSON) 
			      .accept(MediaType.APPLICATION_JSON))		
		     	  .andExpect(status().isOk());
		
		eRepo.deleteByUsername("testemployee@gmail.com");
		
		
		//DATI EMPLOYEE NON VALIDI
		EmployeeDTO e1 = new EmployeeDTO();
		e1.setName("");
		e1.setSurname("SURNAMETEST");
		e1.setDob(LocalDate.of(2023,02,06));
		e1.setImg_url("TEST.jpg");
		e1.setUsername("testemployee@gmail.com");
		
		String employeeValido1=asJsonString(e1);
		mock.perform( MockMvcRequestBuilders
			      .post("/employees").header("Authorization", token)							
			      .content(employeeValido1)				
			      .contentType(MediaType.APPLICATION_JSON) 
			      .accept(MediaType.APPLICATION_JSON))		
		     	  .andExpect(status().isNotAcceptable())
				  .andExpect(MockMvcResultMatchers.content().string("L'Impiegato che si vuole inserire non è valido! compilare correttamente tutti i campi."));
		
		//JSON ERRATO
		mock.perform( MockMvcRequestBuilders
			      .post("/employees").header("Authorization", token)							
			      .content(JsonEmployeeErrato)				
			      .contentType(MediaType.APPLICATION_JSON) 
			      .accept(MediaType.APPLICATION_JSON))		
		     	  .andExpect(status().isBadRequest())
				  .andExpect(MockMvcResultMatchers.content().string("Il JSON non e in formato corretto!"));
		
	}
	
	@Test
	void testModifyOneEmployee() throws Exception
	{
		mock.perform( MockMvcRequestBuilders
			      .put("/employees/1").header("Authorization", token)						
			      .content(JsonEmployee)				
			      .contentType(MediaType.APPLICATION_JSON) 
			      .accept(MediaType.APPLICATION_JSON))
		     		.andExpect(status().isOk());
		
		//EMPLOYEE NON PRESENTE SUL DB
		Integer id=999;
		mock.perform( MockMvcRequestBuilders
			      .put("/employees/999").header("Authorization", token)						
			      .content(JsonEmployee)				
			      .contentType(MediaType.APPLICATION_JSON) 
			      .accept(MediaType.APPLICATION_JSON))
		     		.andExpect(status().isNotFound())
		     		.andExpect(MockMvcResultMatchers.content().string("Non ci sono impiegati con id "+id+" nel DB."));
		
		//JSON ERRATO
		mock.perform( MockMvcRequestBuilders
			      .put("/employees/1").header("Authorization", token)						
			      .content(JsonEmployeeErrato)				
			      .contentType(MediaType.APPLICATION_JSON) 
			      .accept(MediaType.APPLICATION_JSON))
		     		.andExpect(status().isBadRequest())
		     		.andExpect(MockMvcResultMatchers.content().string("Il JSON non e in formato corretto!"));
		
	}
	
	
	@Test
	void testDeleteEmployee() throws Exception
	{
		Employee employeeToDelete = new Employee();
		employeeToDelete.setUsername("TESTDELETE@gmail.com");
		employeeToDelete = eRepo.save(employeeToDelete);
		
		//TUTTO OK
		mock.perform( MockMvcRequestBuilders
			      .delete("/employees/"+employeeToDelete.getId()).header("Authorization", token)						
			      .contentType(MediaType.APPLICATION_JSON) 
			      .accept(MediaType.APPLICATION_JSON))
		     		.andExpect(status().isOk());
		
		//EMPLOYEE non trovato su db 404 no such element
				Integer id=999;
				mock.perform( MockMvcRequestBuilders
					      .delete("/employees/999").header("Authorization", token)							
					      .contentType(MediaType.APPLICATION_JSON)) 
				     	  	.andExpect(status().isNotFound())  
				     	  	.andExpect(MockMvcResultMatchers.content().string("Non ci sono employee con id "+id+" nel DB."));
		
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
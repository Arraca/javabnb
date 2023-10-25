package com.generation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.generation.config.JwtTokenUtil;

@RestController
@CrossOrigin
public class HelloWorldController {

	@Autowired
	JwtTokenUtil util;
	
	@GetMapping("/hello")
	public String hello(@RequestHeader String Authorization)
	{
		System.out.println(Authorization);
		String token  = Authorization.substring(7);
		String name =util.getUsernameFromToken(token);
		return "Ciao "+ name + " Numero casuale:"+(100*Math.random());
	}
	
	@GetMapping("/check")
	public String hello()
	{
		
		return "sei autorizzato";
	}

}

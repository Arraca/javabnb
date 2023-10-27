package com.generation.javabnb.model.entities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Season 
{
	private String season;
	private LocalDate begin;
	private LocalDate end;
	private Double percent;
	
	
	 public Season(String season, String begin, String end, Double percent) 
	 {
	    this.season = season;
	    this.begin = LocalDate.parse(begin, DateTimeFormatter.ofPattern("yyyy-MM-dd")); 
	    this.end = LocalDate.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd")); 
	    this.percent = percent;
    }
	
	
	
}

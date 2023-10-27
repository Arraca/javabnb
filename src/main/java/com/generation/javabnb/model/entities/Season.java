package com.generation.javabnb.model.entities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Season 
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private LocalDate begin;
	private LocalDate end;
	private Double percent;
	
	
//	 public Season(String season, String begin, String end, Double percent) 
//	 {
//	    this.name = season;
//	    this.begin = LocalDate.parse(begin, DateTimeFormatter.ofPattern("yyyy-MM-dd")); 
//	    this.end = LocalDate.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd")); 
//	    this.percent = percent;
//    }
//	
//	 public Season(String season, LocalDate begin, LocalDate end, Double percent) 
//	 {
//	    this.name = season;
//	    this.begin = begin; 
//	    this.end = end; 
//	    this.percent = percent;
//    }
	
}

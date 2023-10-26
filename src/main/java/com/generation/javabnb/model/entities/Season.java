package com.generation.javabnb.model.entities;

import java.time.LocalDate;

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
}

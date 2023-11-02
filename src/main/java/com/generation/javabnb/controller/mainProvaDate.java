package com.generation.javabnb.controller;

import java.time.LocalDate;
import java.time.Period;

import com.generation.javabnb.model.entities.Season;

public class mainProvaDate {

	public static void main(String[] args) 
	{
		
		//PERIODO 1
		LocalDate inizio1 = LocalDate.of(2023, 05, 18);
		LocalDate fine1 = LocalDate.of(2023, 05, 23);
		
		LocalDate inizio2 = LocalDate.of(2023, 05, 15);
		LocalDate fine2 = LocalDate.of(2023, 05, 20);


		
		// TODO Auto-generated method stub
		Double res = 0.0;
			if(intersectionV2(inizio1, fine1, inizio2, fine2)>0)
				res+=intersectionV2(inizio1, fine1, inizio2, fine2);
		
		System.out.println(res); 

	}
	
	private static Integer intersectionV2(LocalDate firstBegin, LocalDate firstEnd, LocalDate secondBegin, LocalDate secondEnd) 
	{
		LocalDate periodBegin = firstBegin.isAfter(secondBegin)? firstBegin : secondBegin;
		LocalDate periodEnd = firstEnd.isBefore(secondEnd)? firstEnd : secondEnd;
		
		Period period = periodBegin.until(periodEnd);
		Integer days = period.getDays()+1;
		return days;
	}


}

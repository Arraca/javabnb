package com.generation.javabnb.model.entities.dto.season;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import com.generation.javabnb.model.entities.Season;

public class SeasonDTO
{
	protected String season;
	protected LocalDate begin;
	protected LocalDate end;
	protected Double percent;
	
	public SeasonDTO() {}
	
	public SeasonDTO(Season s) 
	{
		season=s.getSeason();
		begin=s.getBegin();
		end=s.getEnd();
		percent=s.getPercent();
		
	}
	
	public boolean DateIsValid(LocalDate begin,LocalDate end)
	{
		try 
		{
			if (end.isAfter(begin))
	            return true;
			else 
				return false;
	    }
		catch (DateTimeParseException e) 
		{
            return false; 
        }
    }
		
	
	public boolean isValid()
	{
		return season!=null && !season.isBlank()  &&
			   begin != null && end != null && DateIsValid(begin, end);
		
		
		
		
		
		
		
	}
	
	
}

package com.generation.javabnb.model.dto.season;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.generation.javabnb.model.entities.Season;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class SeasonDTO 
{
	protected Integer id;
    protected String name;
    protected LocalDate begin;
    protected LocalDate end;
    protected Double percent;

    public SeasonDTO() {}

    public SeasonDTO(Season s) 
    {
        id=s.getId();
        name=s.getName();
        begin=s.getBegin();
        end=s.getEnd();
        percent=s.getPercent();

    }

    public boolean DateIsValid()
    {
            if (end.isAfter(begin))
                return true;
            else 
                return false;
    }

    public boolean DateIsValid(LocalDate begin, LocalDate end)
    {
            if (end.isAfter(begin))
                return true;
            else 
                return false;
    }


    public boolean isValid()
    {
        return name!=null 		&& !name.isBlank()  &&
               begin != null 	&& end != null 		&&
               DateIsValid()	;

    }
    
    public Season convertToSeason()
    {
        Season res = new Season();
        res.setId(id);
        res.setName(name);
        res.setBegin(begin);
        res.setEnd(end);
        res.setPercent(percent);


        return res;
    }
	    
}

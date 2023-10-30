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
    protected String begin;
    protected String end;
    protected Double percent;

    public SeasonDTO() {}

    public SeasonDTO(Season s) 
    {
        id=s.getId();
        name=s.getName();
        begin=s.getBegin()+"";
        end=s.getEnd()+"";
        percent=s.getPercent();

    }

    public boolean DateIsValid(String begin,String end)
    {
        try 
        {
            if (LocalDate.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd")).isAfter(LocalDate.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd"))))
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
        return name!=null && !name.isBlank()  &&
               begin != null && end != null;


    }
    
    public Season convertToSeason()
    {
        Season res = new Season();
        res.setId(id);
        res.setName(name);
        res.setBegin(LocalDate.parse(begin, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        res.setEnd(LocalDate.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        res.setPercent(percent);


        return res;
    }
	    
}

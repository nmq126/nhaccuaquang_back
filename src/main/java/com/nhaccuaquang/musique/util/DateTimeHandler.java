package com.nhaccuaquang.musique.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
public class DateTimeHandler {
    private String date;

    public LocalDate convertStringToLocalDate(String date){
        DateTimeFormatter pattern =  DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            return LocalDate.parse(date, pattern);
        }catch (DateTimeParseException e){
            e.printStackTrace();
            return null;
        }
    }
}

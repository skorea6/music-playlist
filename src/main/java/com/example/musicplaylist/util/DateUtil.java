package com.example.musicplaylist.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtil {
    public static String formatDateTime(LocalDateTime date) {
        if(date != null){
            return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }else{
            return null;
        }
    }

    public static String formatDateTimeMonthAndDate(LocalDateTime date) {
        if(date != null){
            return date.format(DateTimeFormatter.ofPattern("MM-dd"));
        }else{
            return null;
        }
    }

    public static String formatDate(LocalDate date) {
        if(date != null){
            return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }else{
            return null;
        }
    }

    public static LocalDate stringToLocalDate(String date){
        LocalDate returnDate = null;
        if(date != null){
            try {
                returnDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("date format이 올바르지 않습니다.");
            }
        }

        return returnDate;
    }
}

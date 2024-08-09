/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.nehoda;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author dorgu
 */
public class DateManager {
   private static final DateTimeFormatter baseDf=DateTimeFormatter.ofPattern("dd.MM.yyyy"); 
   private static final DateTimeFormatter defaultDf=DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
   
   public static LocalDate getBaseDate(String date)
   {
        return LocalDate.parse(date, baseDf);
   }
   
      public static LocalDate getDefaultDate(String date)
   {
        return LocalDate.parse(date, defaultDf);
   }
   
    
   public static String getDefaultDateAsString(String date)
   {        
       return getBaseDate(date).format(defaultDf);
   }
   
 
}

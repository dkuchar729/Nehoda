/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.nehoda;

/**
 *
 * @author David
 */
public class TimeManager {

    
    public static String returnHour(String time) {
       
        switch (time.length()) {
            case 1:
                return "0";
            case 2:
                return "0";
            case 3:
                return time.substring(0, 1);
            case 4:
                String hour = time.substring(0, 2);
                if (Integer.parseInt(hour) < 25) {
                    return time.substring(0, 2);
                }

            default:
                return null;

        }
    }
}

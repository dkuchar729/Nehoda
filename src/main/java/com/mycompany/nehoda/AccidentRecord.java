/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.nehoda;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class AccidentRecord {

    public static List<String> headerRow = new LinkedList<>(Arrays.asList("hodina", "umisteniObec", "druhNehody", "zavineniNehody", "alkohol", "pricinaNehody", "povrchVozovky", "stavVozovky", "povetrnostniPodminky", "viditelnost", "rozhled", "deleniKomunikace", "situovaniNehody", "specifickeMisto", "smerovyPomer", "druhKomunikace", "den", "souradniceX", "souradniceY", "maxPrumTeplota", "minPrumTeplota", "prumSrazky"));
    public static List<String> headerRowAltitude = new LinkedList<>(Arrays.asList("hodina", "umisteniObec", "druhNehody", "zavineniNehody", "alkohol", "pricinaNehody", "povrchVozovky", "stavVozovky", "povetrnostniPodminky", "viditelnost", "rozhled", "deleniKomunikace", "situovaniNehody", "specifickeMisto", "smerovyPomer", "druhKomunikace", "den", "maxPrumTeplota", "minPrumTeplota", "prumSrazky", "nadmorskaVyska"));
    public static List<String> allHeaderRow = new LinkedList<>(Arrays.asList("id", "datum", "hodina", "kraj", "okres", "umisteniObec", "druhNehody", "zavineniNehody", "alkohol", "pricinaNehody", "povrchVozovky", "stavVozovky", "povetrnostniPodminky", "viditelnost", "rozhled", "deleniKomunikace", "situovaniNehody", "specifickeMisto", "smerovyPomer", "druhKomunikace", "cisloKomunikace", "typVozidla", "souradniceX", "souradniceY", "obec", "den", "maxPrumTeplota", "minPrumTeplota", "prumSrazky", "hustotaProvozu", "zavaznostNehody", "typSrazkyVozidlo", "typSrazkyPrekazka", "druhPovrchu", "pocetVozidel"));
    public static Map<Integer, String> fileHeaderRow = new HashMap<>();

    public static void updateHeader(String[] headerRow) {
        for (int column = 0; column < headerRow.length; column++) {
            fileHeaderRow.put(column,headerRow[column]);
        }
    }

}

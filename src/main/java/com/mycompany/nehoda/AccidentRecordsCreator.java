/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.nehoda;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author dorgu
 */
public class AccidentRecordsCreator {

    private final String[] row;
    private final List<Map<String, Map<String, Boolean>>> directionMapList;
    private final Map<String, Map<String, Boolean>> directSection;
    private final Map<String, Map<String, Boolean>> curve;
    private final Map<String, Map<String, Boolean>> crossroads;
    private final Map<String, Map<String, Boolean>> roundabout;

    Map<String, Map<String, Boolean>> havarie;
    Map<String, Map<String, Boolean>> prekazka;
    Map<String, Map<String, Boolean>> zvire;
    Map<String, Map<String, Boolean>> srazkaVozidlo;
    Map<String, Map<String, Boolean>> tramvaj;
    Map<String, Map<String, Boolean>> srazkaVlak;
    Map<String, Map<String, Boolean>> srazkaOdstaveneVozidlo;
    Map<String, Map<String, Boolean>> chodec;
    Map<String, Map<String, Boolean>> ostatni;
/*
    private int time = 0;
    private int accidentUrbanLocation = 1;
    private int accidentType = 2;
    private int guilty = 3;
    private int cause = 5;
    private int surface = 6;
    private int roadState = 7;
    private int weatherConditions = 8;
    private int visibility = 9;
    private int vision = 10;
    private int roadLines = 11;
    private int accidentLocation = 12;
    private int specificPlace = 13;
    private int direction = 14;
    private int roadType = 15;
    private int day = 16;
    private int result = 21;
*/
    private int density = 10;
    
    private int time = -1;
    private int accidentUrbanLocation = 1;
    private int accidentType = 1;
    private int guilty = 3;
    private int cause = 5;
    private int surface = 2;
    private int roadState = 3;
    private int weatherConditions = 8;
    private int visibility = 4;
    private int vision = 10;
    private int roadLines = 11;
    private int accidentLocation = 12;
    private int specificPlace = 13;
    private int direction = 14;
    private int roadType = 5;
    private int day = 6;
   
    private int result = 11;
     

    private List<Map<String, Map<String, Boolean>>> seznamDen = createAccidentTypeList(new String[]{"srazkaVozidlo", "srazkaOdstaveneVozidlo", "prekazka", "chodec", "zvire", "srazkaVlak", "srazkaTramvaj", "havarie", "ostatni"});

    public AccidentRecordsCreator(String[] row) {

        this.row = row;
        directionMapList = new LinkedList<>();
        directSection = createDirectionMap("primyUsek");
        curve = createDirectionMap("zatacka");
        crossroads = createDirectionMap("krizovatka");
        roundabout = createDirectionMap("kruhovyObjezd");

        directionMapList.add(directSection);
        directionMapList.add(curve);
        directionMapList.add(crossroads);
        directionMapList.add(roundabout);
    }

    public List<String[]> getNewRecords() {

        List<String[]> newRecords = new LinkedList<>();
        for (Map<String, Map<String, Boolean>> directionMap : directionMapList) {

            for (Map.Entry<String, Map<String, Boolean>> roadTypeEntry : directionMap.entrySet()) {

                for (Map.Entry<String, Boolean> roadType : roadTypeEntry.getValue().entrySet()) {

                    if (roadType.getValue() == false) {
                        String[] newRow = row.clone();
                        newRow[getDirection()] = roadTypeEntry.getKey();
                        newRow[this.getRoadType()] = roadType.getKey();
                        newRow[getResult()] = "0";

                        newRecords.add(newRow);

                    }
                }
            }

        }
        return newRecords;
    }

    public boolean compareAccidentRecord(String[] comparedRow) {

        if (compareRecord(row, comparedRow)) {

            switch (comparedRow[getDirection()]) {
                case "primyUsek" -> {
                    directSection.get(comparedRow[getDirection()]).put(comparedRow[getRoadType()], true);
                    return true;
                }
                case "krizovatka" -> {
                    crossroads.get(comparedRow[getDirection()]).put(comparedRow[getRoadType()], true);
                    return true;
                }
                case "zatacka" -> {
                    curve.get(comparedRow[getDirection()]).put(comparedRow[getRoadType()], true);
                    return true;
                }
                case "kruhovyObjezd" -> {
                    roundabout.get(comparedRow[getDirection()]).put(comparedRow[getRoadType()], true);
                    return true;
                }
                default -> {
                }
            }
        }
        return false;
    }

    public boolean compareRecord(String[] firstRow, String[] secondRow) {

        return compareRowsItem(firstRow, secondRow, getTime()) && compareRowsItem(firstRow, secondRow, getAccidentType()) && compareRowsItem(firstRow, secondRow, getGuilty())
                && compareRowsItem(firstRow, secondRow, getCause()) && compareRowsItem(firstRow, secondRow, getVisibility())
                && compareRowsItem(firstRow, secondRow, getRoadLines()) && compareRowsItem(firstRow, secondRow, getSurface())
                && compareRowsItem(firstRow, secondRow, getWeatherConditions()) && compareRowsItem(firstRow, secondRow, getAccidentUrbanLocation())
                && compareRowsItem(firstRow, secondRow, getRoadState()) && compareRowsItem(firstRow, secondRow, getVision())
                && compareRowsItem(firstRow, secondRow, getAccidentLocation()) && compareRowsItem(firstRow, secondRow, getSpecificPlace()) && compareRowsItem(firstRow, secondRow, getDay());
    }

    public static boolean compareRowsItem(String[] firstRow, String[] secondRow, int index) {
        return firstRow[index].equals(secondRow[index]);
    }

    public Map<String, Boolean> createRoadTypeMap() {
        Map<String, Boolean> roadTypeMap = new HashMap<>();

        roadTypeMap.put("1.trida", Boolean.FALSE);
        roadTypeMap.put("2.trida", Boolean.FALSE);
        roadTypeMap.put("3.trida", Boolean.FALSE);
        roadTypeMap.put("ucelova", Boolean.FALSE);
        roadTypeMap.put("mistni", Boolean.FALSE);
        roadTypeMap.put("dalnice", Boolean.FALSE);

        return roadTypeMap;
    }

    public List<Map<String, Map<String, Boolean>>> createAccidentTypeList(String[] keys) {
        List<Map<String, Map<String, Boolean>>> list = new LinkedList<>();
        for (int i = 0; i < keys.length; i++) {
            list.add(createDirectionMap(keys[i]));
        }

        return list;
    }

    public Map<String, Map<String, Boolean>> createDirectionMap(String key) {
        Map<String, Map<String, Boolean>> directionMap = new HashMap<>();

        directionMap.put(key, createRoadTypeMap());

        return directionMap;

    }

    /**
     * @return the time
     */
    public int getTime() {
        return time;
    }

    /**
     * @return the accidentUrbanLocation
     */
    public int getAccidentUrbanLocation() {
        return accidentUrbanLocation;
    }

    /**
     * @return the accidentType
     */
    public int getAccidentType() {
        return accidentType;
    }

    /**
     * @return the guilty
     */
    public int getGuilty() {
        return guilty;
    }

    /**
     * @return the cause
     */
    public int getCause() {
        return cause;
    }

    /**
     * @return the surface
     */
    public int getSurface() {
        return surface;
    }

    public int getDensity() {
        return density;
    }

    /**
     * @return the roadState
     */
    public int getRoadState() {
        return roadState;
    }

    /**
     * @return the weatherConditions
     */
    public int getWeatherConditions() {
        return weatherConditions;
    }

    /**
     * @return the visibility
     */
    public int getVisibility() {
        return visibility;
    }

    /**
     * @return the vision
     */
    public int getVision() {
        return vision;
    }

    /**
     * @return the roadLines
     */
    public int getRoadLines() {
        return roadLines;
    }

    /**
     * @return the accidentLocation
     */
    public int getAccidentLocation() {
        return accidentLocation;
    }

    /**
     * @return the specificPlace
     */
    public int getSpecificPlace() {
        return specificPlace;
    }

    /**
     * @return the direction
     */
    public int getDirection() {
        return direction;
    }

    /**
     * @return the roadType
     */
    public int getRoadType() {
        return roadType;
    }

    /**
     * @return the day
     */
    public int getDay() {
        return day;
    }

    /**
     * @return the result
     */
    public int getResult() {
        return result;
    }

    public static boolean porovnejDenDvaZaznam(String[] firstRow, String[] secondRow, int surface, int roadState, int visibility) {
        return AccidentRecordsCreator.compareRowsItem(firstRow, secondRow, surface) && AccidentRecordsCreator.compareRowsItem(firstRow, secondRow, roadState)
                && AccidentRecordsCreator.compareRowsItem(firstRow, secondRow, visibility);
    }

    public static boolean compareIdenticalRow(String[] firstRow, String[] secondRow, int accidentType, int roadType) {
        return AccidentRecordsCreator.compareRowsItem(firstRow, secondRow, accidentType) && AccidentRecordsCreator.compareRowsItem(firstRow, secondRow, roadType);
    }

    public boolean compareDay(String[] radka, double d1,int range) {

        if (porovnejDenDvaZaznam(row, radka, getSurface(), getRoadState(), getVisibility())
                 && checkDensity(Double.parseDouble(row[density]), d1,range)
                ) {//  && checkDensity(Double.parseDouble(row[density]), d1,1000)  Double.parseDouble(radka[density]

            for (int i = 0; i < seznamDen.size(); i++) {

                if (seznamDen.get(i).containsKey(radka[getAccidentType()])) {
                    seznamDen.get(i).get(radka[getAccidentType()]).put(radka[getRoadType()], true);
                    return compareIdenticalRow(row, radka, getAccidentType(), getRoadType());
                }
            }

        }
        return false;
    }

    public boolean compareDay(String[] radka, boolean density) {

        if (porovnejDenDvaZaznam(row, radka, getSurface(), getRoadState(), getVisibility())) {

            for (int i = 0; i < seznamDen.size(); i++) {

                if (seznamDen.get(i).containsKey(radka[getAccidentType()])) {
                    seznamDen.get(i).get(radka[getAccidentType()]).put(radka[getRoadType()], true);
                    return compareIdenticalRow(row, radka, getAccidentType(), getRoadType());
                }
            }

        }
        return false;
    }

    public List<String[]> vratDnyRadky(int druhNehody, int druhKomunikace, int pocet) {

        List<String[]> seznamRadek = new LinkedList<>();
        for (Map<String, Map<String, Boolean>> mapa : seznamDen) {

            for (Map.Entry<String, Map<String, Boolean>> entry : mapa.entrySet()) {

                for (Map.Entry<String, Boolean> komunikace : entry.getValue().entrySet()) {

                    if (komunikace.getValue() == false) {
                        String[] newRow = row.clone();
                        newRow[druhNehody] = entry.getKey();
                        newRow[druhKomunikace] = komunikace.getKey();
                        newRow[pocet] = "0";

                        seznamRadek.add(newRow);

                    }
                }
            }

        }
        return seznamRadek;
    }

    public static boolean checkDensity(double actualDensity, double newDensity, int range) {

        return range >= Math.sqrt(Math.pow(actualDensity - newDensity, 2));
    }

    public static boolean checkDensityy(double actualDensity, double newDensity, int range) {
        return ((int) actualDensity / range) == ((int) newDensity / range);

    }
}

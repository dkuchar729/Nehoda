/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.nehoda;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author dorgu
 */
public final class ParameterManager {

    private Indexes headerIndexes;
    private Map<Integer, String> districts;
    private Map<Integer, String> accidentType;
    private Map<Integer, String> town;
    private Map<Integer, String> daysOfWeek;
    private Map<Integer, String> guilty;
    private Map<Integer, String> alcohol;
    private Map<Integer, String> causes;
    private Map<Integer, String> surface;
    private Map<Integer, String> weather;
    private Map<Integer, String> visibility;
    private Map<Integer, String> roadLine;
    private Map<Integer, String> roadType;
    private Map<Integer, String> direction;
    private Map<Integer, String> roadArea;
    private Map<Integer, String> specificPlace;
    private Map<Integer, String> roadState;
    private Map<Integer, String> vision;
    private Map<String, String> headerRemoveValues;
    private Map<String, String> headerValues;

    //cars
    private Map<Integer, String> vehicleTypes;

    public ParameterManager() {
        headerIndexes = new Indexes();
        districts = new HashMap<>();
        accidentType = new HashMap<>();
        daysOfWeek = new HashMap<>();
        guilty = new HashMap<>();
        alcohol = new HashMap<>();
        causes = new HashMap<>();
        surface = new HashMap<>();
        weather = new HashMap<>();
        visibility = new HashMap<>();
        roadLine = new HashMap<>();
        roadType = new HashMap<>();
        direction = new HashMap<>();
        headerValues = new HashMap<>();
        town = new HashMap<>();
        roadArea = new HashMap<>();
        specificPlace = new HashMap<>();
        roadState = new HashMap<>();
        vision = new HashMap<>();
        vision = new HashMap<>();

        vehicleTypes = new HashMap<>();

        setMapValue(headerValues, "p1", "id");
        setMapValue(headerValues, "p2a", "datum");
        setMapValue(headerValues, "p2b", "hodina");
        setMapValue(headerValues, "p4a", "kraj");
        setMapValue(headerValues, "p4b", "okres");
        setMapValue(headerValues, "p5a", "umisteniObec");
        setMapValue(headerValues, "p6", "druhNehody");
        setMapValue(headerValues, "p10", "zavineniNehody");
        setMapValue(headerValues, "p11", "alkohol");
        setMapValue(headerValues, "p12", "pricinaNehody");
        setMapValue(headerValues, "p16", "povrchVozovky");
        setMapValue(headerValues, "p17", "stavVozovky");
        setMapValue(headerValues, "p18", "povetrnostniPodminky");
        setMapValue(headerValues, "p19", "viditelnost");
        setMapValue(headerValues, "p20", "rozhled");
        setMapValue(headerValues, "p21", "deleniKomunikace");
        setMapValue(headerValues, "p22", "situovaniNehody");
        setMapValue(headerValues, "p27", "specifickeMisto");
        setMapValue(headerValues, "p28", "smerovyPomer");
        setMapValue(headerValues, "p36", "druhKomunikace");
        setMapValue(headerValues, "p37", "cisloKomunikace");
        setMapValue(headerValues, "p44", "typVozidla");
        setMapValue(headerValues, "h", "obec");
        setMapValue(headerValues, "d", "souradniceX");
        setMapValue(headerValues, "e", "souradniceY");

        setMapValue(districts, 1, "ceskeBudejovice");
        setMapValue(districts, 2, "ceskyKrumlov");
        setMapValue(districts, 3, "jindrichuvHradec");
        setMapValue(districts, 5, "pisek");
        setMapValue(districts, 6, "prachatice");
        setMapValue(districts, 7, "strakonice");
        setMapValue(districts, 8, "tabor");

        setMapValues(accidentType, new Integer[]{1}, "srazkaVozidlo");
        setMapValues(accidentType, new Integer[]{2}, "srazkaOdstaveneVozidlo");
        setMapValue(accidentType, 3, "prekazka");
        setMapValue(accidentType, 4, "chodec");
        setMapValues(accidentType, new Integer[]{5, 6}, "zvire");
        setMapValues(accidentType, new Integer[]{7}, "srazkaVlak");
        setMapValues(accidentType, new Integer[]{8}, "srazkaTramvaj");
        setMapValues(accidentType, new Integer[]{9}, "havarie");
        setMapValues(accidentType, new Integer[]{0}, "ostatni");

        setMapValue(daysOfWeek, 1, "pondeli");
        setMapValue(daysOfWeek, 2, "utery");
        setMapValue(daysOfWeek, 3, "streda");
        setMapValue(daysOfWeek, 4, "ctvrtek");
        setMapValue(daysOfWeek, 5, "patek");
        setMapValue(daysOfWeek, 6, "sobota");
        setMapValue(daysOfWeek, 7, "nedele");

        setMapValues(guilty, new Integer[]{1, 2}, "ridic");
        setMapValue(guilty, 3, "chodec");
        setMapValue(guilty, 4, "zvire");
        setMapValue(guilty, 5, "ucastnik");
        setMapValue(guilty, 6, "komunikace");
        setMapValues(guilty, new Integer[]{0, 7}, "ostatni");

        setMapValues(alcohol, new Integer[]{1, 3, 6, 7, 8, 9}, "ano");
        setMapValue(alcohol, 2, "ne");
        setMapValues(alcohol, new Integer[]{0, 4}, "neznamo");

        setMapValues(causes, new Integer[]{201, 202, 203, 204, 205, 206, 207, 208, 209}, "rychlost");
        setMapValues(causes, new Integer[]{301, 302, 303, 304, 305, 306, 307, 308, 309, 310, 311}, "predjizdeni");
        setMapValues(causes, new Integer[]{401, 402, 403, 404, 405, 406, 407, 408, 409, 410, 411, 412, 413, 414}, "nedaniPrednosti");
        setMapValues(causes, new Integer[]{501, 502, 503, 504, 505, 506, 507, 508, 509, 510, 511, 512, 513, 514, 515, 516}, "zpusobJizdy");
        setMapValues(causes, new Integer[]{601, 602, 603, 604, 605, 606, 607, 608, 609, 610, 611, 612, 613, 614, 615}, "zavada");
        setMapValue(causes, 100, "nezavineniRidicem");

        setMapValues(surface, new Integer[]{1, 2}, "suchy");
        setMapValue(surface, 3, "mokry");
        setMapValues(surface, new Integer[]{5, 6, 8, 9}, "naledi/snih");
        setMapValues(surface, new Integer[]{4, 7, 0}, "ostatni");

        setMapValue(weather, 1, "neztizene");
        setMapValue(weather, 3, "slabyDest");
        setMapValue(weather, 4, "dest");
        setMapValue(weather, 5, "snezeni");
        setMapValue(weather, 6, "namraza");
        setMapValues(weather, new Integer[]{2, 7, 0}, "ostatni");

        setMapValues(visibility, new Integer[]{1, 4, 6}, "neztizena");
        setMapValue(visibility, 2, "svitani/soumrak");
        setMapValues(visibility, new Integer[]{3, 5, 7}, "ztizenaPocasim");

        setMapValue(roadLine, 1, "dvoupruh");
        setMapValue(roadLine, 2, "tripruh");
        setMapValues(roadLine, new Integer[]{3, 4}, "ctyrpruh");
        setMapValue(roadLine, 5, "vicepruh");
        setMapValue(roadLine, 6, "rychlostni");
        setMapValue(roadLine, 0, "ostatni");

        setMapValue(roadType, 0, "dalnice");
        setMapValue(roadType, 1, "1.trida");
        setMapValue(roadType, 2, "2.trida");
        setMapValue(roadType, 3, "3.trida");
        setMapValue(roadType, 6, "mistni");
        setMapValues(roadType, new Integer[]{7, 8}, "ucelova");
        setMapValues(roadType, new Integer[]{4, 5}, "ostatni");

        setMapValues(direction, new Integer[]{1, 2}, "primyUsek");
        setMapValue(direction, 3, "zatacka");
        setMapValues(direction, new Integer[]{4, 5, 6}, "krizovatka");
        setMapValue(direction, 7, "kruhovyObjezd");

        setMapValue(town, 1, "obec");
        setMapValue(town, 2, "mimoObec");

        setMapValue(roadArea, 1, "jizdniPruh");
        setMapValue(roadArea, 2, "odstavenyPruh");
        setMapValue(roadArea, 3, "krajnice");
        setMapValue(roadArea, 4, "odbocovaciPruh/pripojovaciPruh");
        setMapValue(roadArea, 5, "pomalaVozidlaPruh");
        setMapValue(roadArea, 6, "chodnik/ostruvek");
        setMapValue(roadArea, 7, "kolejeTramvaje");
        setMapValue(roadArea, 8, "mimoKomunikaci");
        setMapValue(roadArea, 9, "cyklistickaStezka");
        setMapValue(roadArea, 0, "ostatni");

        setMapValue(specificPlace, 1, "prechodProChodce");
        setMapValue(specificPlace, 2, "blizkostPrechodu");
        setMapValue(specificPlace, 3, "nezabezpecenyPrejezd");
        setMapValue(specificPlace, 4, "zabezpecenyPrejezd");
        setMapValues(specificPlace, new Integer[]{5, 52}, "most/nadjezd/podjezd");
        setMapValue(specificPlace, 6, "zastavkaOstrov");
        setMapValue(specificPlace, 7, "zastavkaBezOstrov");
        setMapValue(specificPlace, 8, "vyjezdParkoviste/LesniCesta");
        setMapValue(specificPlace, 9, "cerpadlo");
        setMapValue(specificPlace, 10, "parkovisteKomunikace");
        setMapValue(specificPlace, 0, "ostatni");

        setMapValue(roadState, 1, "dobry");
        setMapValue(roadState, 2, "sklon8");
        setMapValue(roadState, 3, "chybaZnacka");
        setMapValue(roadState, 4, "zvlnenyPovrch");
        setMapValues(roadState, new Integer[]{5, 6}, "vytluky");
        setMapValue(roadState, 7, "trvaleZuzeni");
        setMapValue(roadState, 8, "struzka/hrbol/kolejnice");
        setMapValue(roadState, 9, "spatneZnaceni");
        setMapValue(roadState, 10, "prechodnaUzavirka1Pruh");
        setMapValue(roadState, 11, "prechodnaUzavirkaKomunikace/jizdnihoPruhu");
        setMapValue(roadState, 12, "ostatni");

        setMapValue(vision, 1, "dobre");
        setMapValues(vision, new Integer[]{0, 2, 3, 4, 5, 6, 7}, "spatne");

        setMapValues(vehicleTypes, new Integer[]{0, 1, 2}, "motocykl");
        setMapValues(vehicleTypes, new Integer[]{3, 4}, "osobniAutomobil");
        setMapValues(vehicleTypes, new Integer[]{5, 6, 7}, "nakladniAutomobil");
        setMapValue(vehicleTypes, 8, "autobus");
        setMapValue(vehicleTypes, 9, "traktor");
        setMapValue(vehicleTypes, 10, "tramvaj");
        setMapValue(vehicleTypes, 11, "trolejbus");
        setMapValue(vehicleTypes, 13, "jizdniKolo");
        setMapValue(vehicleTypes, 8, "autobus");
        setMapValue(vehicleTypes, 16, "vlak");
        setMapValues(vehicleTypes, new Integer[]{12, 14, 15, 17, 18}, "ostatni");
    }

    public <A, B> void setMapValues(Map<A, B> map, A[] keys, B value) {
        for (int i = 0; i < keys.length; i++) {
            map.put(keys[i], value);
        }
    }

    public <A, B> void setMapValue(Map<A, B> map, A key, B value) {
        map.put(key, value);
    }

    public boolean setHeaderIndex(int index, String key) {

        //String headerValue = headerValues.get(key);
        switch (key) {
            case "p1" ->
                headerIndexes.setId(index);

            case "p2a" ->
                headerIndexes.setDate(index);

            case "p2b" ->
                headerIndexes.setTime(index);

            case "p4a" ->
                headerIndexes.setRegion(index);

            case "p4b" ->
                headerIndexes.setDistrict(index);

            case "p5a" ->
                headerIndexes.setTown(index);

            case "p6" ->
                headerIndexes.setAccidentType(index);

            case "p10" ->
                headerIndexes.setGuilty(index);

            case "p11" ->
                headerIndexes.setAlcohol(index);

            case "p12" ->
                headerIndexes.setCause(index);

            case "p16" ->
                headerIndexes.setSurface(index);

            case "p17" ->
                headerIndexes.setRoadState(index);

            case "p18" ->
                headerIndexes.setWeatherConditions(index);

            case "p19" ->
                headerIndexes.setVisibility(index);

            case "p20" ->
                headerIndexes.setVision(index);
            case "p21" ->
                headerIndexes.setRoadLine(index);
            case "p22" ->
                headerIndexes.setRoadArea(index);

            case "p27" ->
                headerIndexes.setSpecificPlace(index);

            case "p28" ->
                headerIndexes.setDirection(index);

            case "p36" ->
                headerIndexes.setRoadType(index);

            case "p37" ->
                headerIndexes.setRoadName(index);

            case "p44" ->
                headerIndexes.setVehicleType(index);
                
                  case "h" ->
                headerIndexes.setT(index);
                
                  case "d" ->
                headerIndexes.setcX(index);
                
                  case "e" ->
                headerIndexes.setcY(index);

            default -> {
                return false;
            }

        }
        return true;
    }

    public String getHeaderValue(String key) {

        String headerValue = headerValues.get(key);

        if (headerValue != null) {
            return headerValue;
        }

        return key;

    }

    public String getDistrictValue(String key) {
        return getValue(districts, key);
    }

    public String getTownValue(String key) {
        return getValue(town, key);
    }

    public String getAccidentTypeValue(String key) {
        return getValue(accidentType, key);
    }

    public String getDayOfWeekValue(int key) {

        String day = daysOfWeek.get(key);

        if (day != null) {
            return day;
        }
        return "null";

    }

    public String getRoadArea(String key) {
        return getValue(roadArea, key);
    }

    public String getVision(String key) {
        return getValue(vision, key);
    }

    public String getRoadState(String key) {
        return getValue(roadState, key);
    }

    public String getSpecificPlace(String key) {
        return getValue(specificPlace, key);
    }

    public String getGuiltyValue(String key) {
        return getValue(guilty, key);
    }

    public String getAlcoholValue(String key) {
        return getValue(alcohol, key);
    }

    public String getCauseValue(String key) {
        return getValue(causes, key);
    }

    public String getSurfaceValue(String key) {
        return getValue(surface, key);
    }

    public String getWeatherValue(String key) {
        return getValue(weather, key);
    }

    public String getVisibilityValue(String key) {
        return getValue(visibility, key);
    }

    public String getRoadLineValue(String key) {
        return getValue(roadLine, key);
    }

    public String getRoadTypeValue(String key) {
        return getValue(roadType, key);
    }

    public String getDirectionValue(String key) {
        return getValue(direction, key);
    }

    public String getVehicleType(String key) {
        return getValue(vehicleTypes, key);
    }

    private String getValue(Map<Integer, String> map, String key) {

        try {
            return map.get(Integer.valueOf(key));
        } catch (NumberFormatException e) {
            return "null";
        }

    }

    public Map<Integer, String> getCrashes() {
        return accidentType;
    }

    public Indexes getIndexes() {
        return headerIndexes;
    }
}

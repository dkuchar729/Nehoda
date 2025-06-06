/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.nehoda;

/**
 *
 * @author David
 */
public class HeaderRowManager {

    private Indexes columnIndexes;

    public HeaderRowManager() {
        columnIndexes = new Indexes();
    }

    public void setHeaderColumnsIndexes(String[] headerRow) {
        for (int columnIndex = 0; columnIndex < headerRow.length; columnIndex++) {
            setHeaderIndex(columnIndex, headerRow[columnIndex]);
            
           
        }
    }

    private boolean setHeaderIndex(int index, String columnName) {

        switch (columnName) {
            case "p1", "id" ->

                columnIndexes.setId(index);

            case "p2a", "datum" ->
                columnIndexes.setDate(index);

            case "p2b", "hodina" ->
                columnIndexes.setTime(index);

            case "p4a", "kraj" ->
                columnIndexes.setRegion(index);

            case "p4b", "okres" ->
                columnIndexes.setDistrict(index);

            case "p5a", "umisteniObec" ->
                columnIndexes.setTown(index);

            case "p6", "druhNehody" ->
                columnIndexes.setAccidentType(index);

            case "p7", "typSrazkyVozidlo" ->
                columnIndexes.setVehicleCrashType(index);

            case "p8", "typSrazkyPrekazka" ->
                columnIndexes.setObstacleCrashType(index);

            case "p10", "zavineniNehody" ->
                columnIndexes.setGuilty(index);

            case "p11", "alkohol" ->
                columnIndexes.setAlcohol(index);

            case "p12", "pricinaNehody" ->
                columnIndexes.setCause(index);

            case "p15", "druhPovrchu" ->
                columnIndexes.setRoadSurfaceType(index);

            case "p16", "povrchVozovky" ->
                columnIndexes.setSurface(index);

            case "p17", "stavVozovky" ->
                columnIndexes.setRoadState(index);

            case "p18", "povetrnostniPodminky" ->
                columnIndexes.setWeatherConditions(index);

            case "p19", "viditelnost" ->
                columnIndexes.setVisibility(index);

            case "p20", "rozhled" ->
                columnIndexes.setVision(index);

            case "p21", "deleniKomunikace" ->
                columnIndexes.setRoadLine(index);

            case "p22", "situovaniNehody" ->
                columnIndexes.setRoadArea(index);

            case "p27", "specifickeMisto" ->
                columnIndexes.setSpecificPlace(index);

            case "p28", "smerovyPomer" ->
                columnIndexes.setDirection(index);

            case "p34", "pocetVozidel" ->
                columnIndexes.setVehicleNumber(index);

            case "p36", "druhKomunikace" ->
                columnIndexes.setRoadType(index);

            case "p37", "cisloKomunikace" ->
                columnIndexes.setRoadName(index);

            case "p44", "typVozidla" ->
                columnIndexes.setVehicleType(index);

            case "h", "obec" ->
                columnIndexes.setT(index);

            case "d", "souradniceX" ->
                columnIndexes.setcX(index);

            case "e", "souradniceY" ->
                columnIndexes.setcY(index);

            case "zavaznostNehody" ->
                columnIndexes.setAccidentSeverity(index);


            case "p9", "charakterNehody" -> {
                columnIndexes.setNatureOfAccident(index);

                return false;
            }

            case "p13a", "usmrcenoOsob" -> {
                columnIndexes.setFatalAccident(index);

            }

            case "p13b", "tezceZranenoOsob" -> {
                columnIndexes.setSeriousAccident(index);

            }
            case "p13c", "lehceZranenoOsob" -> {
                columnIndexes.setMinorAccident(index);

            }

            case "p14", "celkovaCastka" -> {
                columnIndexes.setTotalAmount(index);
            }
            
            case "hustotaDopravy"->{
                columnIndexes.setRoadDensity(index);
            }
            
            case "vysledek"->{
                columnIndexes.setResult(index);
            }

            default -> {
                return false;
            }

        }
        return true;
    }

    public Indexes getColumnIndexes() {
        return columnIndexes;
    }
}

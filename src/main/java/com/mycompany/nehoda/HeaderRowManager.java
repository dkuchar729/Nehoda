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

    private ColumnsIndexesManager columnColumnsIndexesManager;

    public HeaderRowManager() {
        columnColumnsIndexesManager = new ColumnsIndexesManager();
    }

    public void setHeaderColumnsIndexes(String[] headerRow) {
        for (int columnIndex = 0; columnIndex < headerRow.length; columnIndex++) {
            setHeaderIndex(columnIndex, headerRow[columnIndex]);
            
           
        }
    }

    private boolean setHeaderIndex(int index, String columnName) {

        switch (columnName) {
            case "p1", "id" ->

                columnColumnsIndexesManager.setId(index);

            case "p2a", "datum" ->
                columnColumnsIndexesManager.setDate(index);

            case "p2b", "hodina" ->
                columnColumnsIndexesManager.setTime(index);

            case "p4a", "kraj" ->
                columnColumnsIndexesManager.setRegion(index);

            case "p4b", "okres" ->
                columnColumnsIndexesManager.setDistrict(index);

            case "p5a", "umisteniObec" ->
                columnColumnsIndexesManager.setTown(index);

            case "p6", "druhNehody" ->
                columnColumnsIndexesManager.setAccidentType(index);

            case "p7", "typSrazkyVozidlo" ->
                columnColumnsIndexesManager.setVehicleCrashType(index);

            case "p8", "typSrazkyPrekazka" ->
                columnColumnsIndexesManager.setObstacleCrashType(index);

            case "p10", "zavineniNehody" ->
                columnColumnsIndexesManager.setGuilty(index);

            case "p11", "alkohol" ->
                columnColumnsIndexesManager.setAlcohol(index);

            case "p12", "pricinaNehody" ->
                columnColumnsIndexesManager.setCause(index);

            case "p15", "druhPovrchu" ->
                columnColumnsIndexesManager.setRoadSurfaceType(index);

            case "p16", "povrchVozovky" ->
                columnColumnsIndexesManager.setSurface(index);

            case "p17", "stavVozovky" ->
                columnColumnsIndexesManager.setRoadState(index);

            case "p18", "povetrnostniPodminky" ->
                columnColumnsIndexesManager.setWeatherConditions(index);

            case "p19", "viditelnost" ->
                columnColumnsIndexesManager.setVisibility(index);

            case "p20", "rozhled" ->
                columnColumnsIndexesManager.setVision(index);

            case "p21", "deleniKomunikace" ->
                columnColumnsIndexesManager.setRoadLine(index);

            case "p22", "situovaniNehody" ->
                columnColumnsIndexesManager.setRoadArea(index);

            case "p27", "specifickeMisto" ->
                columnColumnsIndexesManager.setSpecificPlace(index);

            case "p28", "smerovyPomer" ->
                columnColumnsIndexesManager.setDirection(index);

            case "p34", "pocetVozidel" ->
                columnColumnsIndexesManager.setVehicleNumber(index);

            case "p36", "druhKomunikace" ->
                columnColumnsIndexesManager.setRoadType(index);

            case "p37", "cisloKomunikace" ->
                columnColumnsIndexesManager.setRoadName(index);

            case "p44", "typVozidla" ->
                columnColumnsIndexesManager.setVehicleType(index);

            case "h", "obec" ->
                columnColumnsIndexesManager.setT(index);

            case "d", "souradniceX" ->
                columnColumnsIndexesManager.setcX(index);

            case "e", "souradniceY" ->
                columnColumnsIndexesManager.setcY(index);

            case "zavaznostNehody" ->
                columnColumnsIndexesManager.setAccidentSeverity(index);


            case "p9", "charakterNehody" -> {
                columnColumnsIndexesManager.setNatureOfAccident(index);

                return false;
            }

            case "p13a", "usmrcenoOsob" -> {
                columnColumnsIndexesManager.setFatalAccident(index);

            }

            case "p13b", "tezceZranenoOsob" -> {
                columnColumnsIndexesManager.setSeriousAccident(index);

            }
            case "p13c", "lehceZranenoOsob" -> {
                columnColumnsIndexesManager.setMinorAccident(index);

            }

            case "p14", "celkovaCastka" -> {
                columnColumnsIndexesManager.setTotalAmount(index);
            }
            
            case "hustotaDopravy"->{
                columnColumnsIndexesManager.setRoadDensity(index);
            }
            
            case "vysledek"->{
                columnColumnsIndexesManager.setResult(index);
            }

            default -> {
                return false;
            }

        }
        return true;
    }

    public ColumnsIndexesManager getColumnIndexes() {
        return columnColumnsIndexesManager;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.nehoda;

import java.util.Map;


public class ColumnsIndexesManager {

    private int id;
    private int date;
    private int time;
    private int region;
    private int district;
    private int town;
    private int accidentType;
    private int guilty;
    private int alcohol;
    private int cause;
    private int surface;
    private int roadState;
    private int weatherConditions;
    private int visibility;
    private int vision;
    private int roadLine;
    private int roadArea;
    private int specificPlace;
    private int direction;
    private int specificDirections;
    private int roadType;
    private int roadName;
    private int roadDensity;
    private int vehicleType;
    private int cX;
    private int cY;
    private int t;
    private int natureOfAccident;
    private int fatalAccident;
    private int seriousAccident;
    private int minorAccident;
    private int accidentSeverity;
    private int vehicleCrashType;
    private int obstacleCrashType;
    private int roadSurfaceType;
    private int vehicleNumber;
    private int totalAmount;
    private int result;
    private int specificCause;

private Map<String[],Integer> columnIndexes;

    public int getSpecificDirections() {
        return specificDirections;
    }

    public ColumnsIndexesManager() {

        columnIndexes=Map.of(
                new String[]{"p1","id"},-1,
        new String[]{"p1","id"},-1
        );
        id = -1;
        date = -1;
        time = -1;
        region = -1;
        district = -1;
        town = -1;
        accidentType = -1;
        guilty = -1;
        alcohol = -1;
        cause = -1;
        surface = -1;
        roadState = -1;
        weatherConditions = -1;
        visibility = -1;
        vision = -1;
        roadLine = -1;
        roadArea = -1;
        specificPlace = -1;
        direction = -1;
        roadType = -1;
        roadName = -1;
        roadDensity = -1;
        vehicleType = -1;
        cX = -1;
        cY = -1;
        t = -1;
        natureOfAccident = -1;
        fatalAccident = -1;
        seriousAccident = -1;
        minorAccident = -1;
        accidentSeverity = -1;
        vehicleCrashType = -1;
        obstacleCrashType = -1;
        roadSurfaceType = -1;
        vehicleNumber = -1;
        totalAmount = -1;
        specificDirections = -1;
        result = -1;
        specificCause=-1;
    }

    public int getSpecificCause() {
        return specificCause;
    }

    public void setSpecificCause(int specificCause) {
        this.specificCause = specificCause;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the date
     */
    public int getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(int date) {
        this.date = date;
    }

    /**
     * @return the hour
     */
    public int getTime() {
        return time;
    }

    /**
     * @param time the hour to set
     */
    public void setTime(int time) {
        this.time = time;
    }

    public void setSpecificDirections(int specificDirections) {
        this.specificDirections = specificDirections;
    }

    /**
     * @return the region
     */
    public int getRegion() {
        return region;
    }

    /**
     * @param region the region to set
     */
    public void setRegion(int region) {
        this.region = region;
    }

    public int getDistrict() {
        return district;
    }

    public void setDistrict(int district) {
        this.district = district;
    }

    /**
     * @return the town
     */
    public int getTown() {
        return town;
    }

    /**
     * @param town the town to set
     */
    public void setTown(int town) {
        this.town = town;
    }

    /**
     * @return the accidentType
     */
    public int getAccidentType() {
        return accidentType;
    }

    /**
     * @param accidentType the accidentType to set
     */
    public void setAccidentType(int accidentType) {
        this.accidentType = accidentType;
    }

    /**
     * @return the guilty
     */
    public int getGuilty() {
        return guilty;
    }

    /**
     * @param guilty the guilty to set
     */
    public void setGuilty(int guilty) {
        this.guilty = guilty;
    }

    /**
     * @return the alcohol
     */
    public int getAlcohol() {
        return alcohol;
    }

    /**
     * @param alcohol the alcohol to set
     */
    public void setAlcohol(int alcohol) {
        this.alcohol = alcohol;
    }

    /**
     * @return the cause
     */
    public int getCause() {
        return cause;
    }

    /**
     * @param cause the cause to set
     */
    public void setCause(int cause) {
        this.cause = cause;
    }

    /**
     * @return the surface
     */
    public int getSurface() {
        return surface;
    }

    /**
     * @param surface the surface to set
     */
    public void setSurface(int surface) {
        this.surface = surface;
    }

    /**
     * @return the roadState
     */
    public int getRoadState() {
        return roadState;
    }

    /**
     * @param roadState the roadState to set
     */
    public void setRoadState(int roadState) {
        this.roadState = roadState;
    }

    /**
     * @return the weatherConditions
     */
    public int getWeatherConditions() {
        return weatherConditions;
    }

    /**
     * @param weatherConditions the weatherConditions to set
     */
    public void setWeatherConditions(int weatherConditions) {
        this.weatherConditions = weatherConditions;
    }

    /**
     * @return the visibility
     */
    public int getVisibility() {
        return visibility;
    }

    /**
     * @param visibility the visibility to set
     */
    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    /**
     * @return the vision
     */
    public int getVision() {
        return vision;
    }

    /**
     * @param vision the vision to set
     */
    public void setVision(int vision) {
        this.vision = vision;
    }

    /**
     * @return the roadLine
     */
    public int getRoadLine() {
        return roadLine;
    }

    /**
     * @param roadLine the roadLine to set
     */
    public void setRoadLine(int roadLine) {
        this.roadLine = roadLine;
    }

    /**
     * @return the roadArea
     */
    public int getRoadArea() {
        return roadArea;
    }

    /**
     * @param roadArea the roadArea to set
     */
    public void setRoadArea(int roadArea) {
        this.roadArea = roadArea;
    }

    /**
     * @return the specificPlace
     */
    public int getSpecificPlace() {
        return specificPlace;
    }

    /**
     * @param specificPlace the specificPlace to set
     */
    public void setSpecificPlace(int specificPlace) {
        this.specificPlace = specificPlace;
    }

    /**
     * @return the direction
     */
    public int getDirection() {
        return direction;
    }

    /**
     * @param direction the direction to set
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }

    /**
     * @return the roadType
     */
    public int getRoadType() {
        return roadType;
    }

    /**
     * @param roadType the roadType to set
     */
    public void setRoadType(int roadType) {
        this.roadType = roadType;
    }

    /**
     * @return the roadName
     */
    public int getRoadName() {
        return roadName;
    }

    /**
     * @param roadName the roadName to set
     */
    public void setRoadName(int roadName) {
        this.roadName = roadName;
    }

    /**
     * @return the roadDensity
     */
    public int getRoadDensity() {
        return roadDensity;
    }

    /**
     * @param roadDensity the roadDensity to set
     */
    public void setRoadDensity(int roadDensity) {
        this.roadDensity = roadDensity;
    }

    public void setVehicleType(int vehicleType) {
        this.vehicleType = vehicleType;
    }

    public int getVehicleType() {
        return vehicleType;
    }

    /**
     * @return the cX
     */
    public int getcX() {
        return cX;
    }

    /**
     * @param cX the cX to set
     */
    public void setcX(int cX) {
        this.cX = cX;
    }

    /**
     * @return the cY
     */
    public int getcY() {
        return cY;
    }

    /**
     * @param cY the cY to set
     */
    public void setcY(int cY) {
        this.cY = cY;
    }

    /**
     * @return the t
     */
    public int getT() {
        return t;
    }

    /**
     * @param t the t to set
     */
    public void setT(int t) {
        this.t = t;
    }

    /**
     * @return the natureOfAccident
     */
    public int getNatureOfAccident() {
        return natureOfAccident;
    }

    /**
     * @param natureOfAccident the natureOfAccident to set
     */
    public void setNatureOfAccident(int natureOfAccident) {
        this.natureOfAccident = natureOfAccident;
    }

    /**
     * @return the accidentSeverity
     */
    public int getAccidentSeverity() {
        return accidentSeverity;
    }

    /**
     * @param accidentSeverity the accidentSeverity to set
     */
    public void setAccidentSeverity(int accidentSeverity) {
        this.accidentSeverity = accidentSeverity;
    }

    /**
     * @return the fatalAccident
     */
    public int getFatalAccident() {
        return fatalAccident;
    }

    /**
     * @param fatalAccident the fatalAccident to set
     */
    public void setFatalAccident(int fatalAccident) {
        this.fatalAccident = fatalAccident;
    }

    /**
     * @return the seriousAccident
     */
    public int getSeriousAccident() {
        return seriousAccident;
    }

    /**
     * @param seriousAccident the seriousAccident to set
     */
    public void setSeriousAccident(int seriousAccident) {
        this.seriousAccident = seriousAccident;
    }

    /**
     * @return the minorAccident
     */
    public int getMinorAccident() {
        return minorAccident;
    }

    /**
     * @param minorAccident the minorAccident to set
     */
    public void setMinorAccident(int minorAccident) {
        this.minorAccident = minorAccident;
    }

    /**
     * @return the vehicleCrashType
     */
    public int getVehicleCrashType() {
        return vehicleCrashType;
    }

    /**
     * @param vehicleCrashType the vehicleCrashType to set
     */
    public void setVehicleCrashType(int vehicleCrashType) {
        this.vehicleCrashType = vehicleCrashType;
    }

    /**
     * @return the obstacleCrashType
     */
    public int getObstacleCrashType() {
        return obstacleCrashType;
    }

    /**
     * @param obstacleCrashType the obstacleCrashType to set
     */
    public void setObstacleCrashType(int obstacleCrashType) {
        this.obstacleCrashType = obstacleCrashType;
    }

    /**
     * @return the roadSurfaceType
     */
    public int getRoadSurfaceType() {
        return roadSurfaceType;
    }

    /**
     * @param roadSurfaceType the roadSurfaceType to set
     */
    public void setRoadSurfaceType(int roadSurfaceType) {
        this.roadSurfaceType = roadSurfaceType;
    }

    /**
     * @return the vehicleNumber
     */
    public int getVehicleNumber() {
        return vehicleNumber;
    }

    /**
     * @param vehicleNumber the vehicleNumber to set
     */
    public void setVehicleNumber(int vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    /**
     * @return the totalAmount
     */
    public int getTotalAmount() {
        return totalAmount;
    }

    /**
     * @param totalAmount the totalAmount to set
     */
    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**
     * @return the result
     */
    public int getResult() {
        return result;
    }

    /**
     * @param result the result to set
     */
    public void setResult(int result) {
        this.result = result;
    }

}

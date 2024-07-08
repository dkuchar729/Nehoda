/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.mycompany.nehoda;

import java.io.File;

/**
 *
 * @author dorgu
 */
public enum WeatherInfo {
    JIHOCESKY(new File("jihoceskyKrajPocasi.csv"),0);
    
    private final File regionWeatherFile;
    private final int weatherDateColumnIndex;
    
    WeatherInfo(File regionWeatherFile,int weatherDateColumnIndex)
    {
        this.regionWeatherFile=regionWeatherFile;
        this.weatherDateColumnIndex=weatherDateColumnIndex;
    }

    /**
     * @return the regionWeatherFile
     */
    public File getRegionWeatherFile() {
        return regionWeatherFile;
    }

    /**
     * @return the weatherDateColumnIndex
     */
    public int getWeatherDateColumnIndex() {
        return weatherDateColumnIndex;
    }
    
    
}

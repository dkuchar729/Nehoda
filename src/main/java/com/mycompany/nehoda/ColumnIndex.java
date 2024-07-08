/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.mycompany.nehoda;

/**
 *
 * @author dorgu
 */
public enum ColumnIndex {
    //1, 2, 4, 5, 6, 7, 8, 9, 10, 11,12

    /*int dateColumnIndex, int hourColumnIndex, 
            int crashColumnIndex, int guiltyColumnIndex, int alcoholColumnIndex, int causeColumnIndex, int causeSurfaceIndex,
            int weatherColumnIndex, int visibilityColumnIndex, int roadLineColumnIndex, int roadTypeColumnIndex*/
    ID(0),
    DATE(1),
    HOUR(2),
    REGION(3),
    TOWN(6),
    ACCIDENT_TYPE(7),
    GUILTY(12),
    ALCOHOL(13),
    CAUSE(15),
    SURFACE(21),
    ROAD_STATE(22),
    WEATHER_CONDITIONS(23),
    VISIBILITY(24),
    VISION(25),
    ROAD_LINE(26),
    ROAD_AREA(27),
    SPECIFIC_PLACE(30),
    DIRECTION(31),
    ROAD_TYPE(34),
    ROAD_NAME(35),
    ROAD_DENSITY(15);

    private final int columnIndex;
    private final static int[] columnsToRemove = {2, 2, 4, 13};//    0,1,3,14

    ColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    /**
     * @return the columnIndex
     */
    public int getColumnIndex() {
        return columnIndex;
    }

    /**
     * @return the columnsToRemove
     */
    public static int[] getColumnsToRemove() {
        return columnsToRemove;
    }

}

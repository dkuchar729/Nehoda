/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.nehoda;

import java.util.LinkedList;
import java.util.List;

/**
 * @author dorgu
 */
public class AccidentRecordManager {

    private AccidentRecordsCreator lastAccidentRecordsCreator;
    private final List<AccidentRecordsCreator> arcList;

    public AccidentRecordManager() {
        arcList = new LinkedList<>();
    }

    public void processNewRow(String[] row) {
        lastAccidentRecordsCreator = new AccidentRecordsCreator(row);
        lastAccidentRecordsCreator.compareAccidentRecord(row);
        arcList.add(lastAccidentRecordsCreator);
    }

    public boolean compareWithLastInputRow(String[] row) {
        return lastAccidentRecordsCreator.compareAccidentRecord(row);
    }

    public List<AccidentRecordsCreator> getAccidentRecorsCreatorsList() {
        return arcList;
    }

    /*
     public void pridejDen(String[] row) {
        lastNastaveni = new Nastaveni(row);
        lastNastaveni.compareDay(row);
        seznam.add(lastNastaveni);
    }

    public boolean porovnejDen(String[] row) {
        return lastNastaveni.compareDay(row);
    }
     */
}

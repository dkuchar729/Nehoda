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

   public   AccidentRecordsCreator lastAccidentRecordsCreator;
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

    
     public void pridejDen(String[] row,double d1,int range) {
       lastAccidentRecordsCreator = new AccidentRecordsCreator(row);
        lastAccidentRecordsCreator.compareDay(row,d1,range);
       arcList.add(lastAccidentRecordsCreator);
    }

    public boolean porovnejDen(String[] row,double d1,int range) {
        return lastAccidentRecordsCreator.compareDay(row,d1,range);
    }
     
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.nehoda;

import ColumnValuesMappers.SpecificMapper;
import com.mycompany.nehoda.ColumnsIndexing.AvailableColumns;
import com.mycompany.nehoda.CsvManagers.CsvManager;
import com.mycompany.nehoda.CsvManagers.*;
import com.mycompany.nehoda.DatabaseManagers.AccidentDatabaseManager;

import java.io.File;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.mycompany.nehoda.DatabaseManagers.DatabaseManager;
import org.apache.commons.lang3.ArrayUtils;

/**
 * @author dorgu
 */
public class Nehoda {

    public static void main(String[] args) {
        try {


           // System.out.println(Date.valueOf("2020-01-02"));
//data/predpovedNehody/nehodyVsechny.csv
            CsvManager cm = new CsvManager(new File("nehodyPocasiUpraveno.csv"), ';');

            //cm.addD();
            //cm.addStations();

            //cm.udpateWeatherInput();
            //cm.checkHeaderRTest();


            //cm.updateDatabaseColumnByID("specifikacePricinaNehody");
            //cm.AddSpecificColumn();
            /*DatabaseManager db=new DatabaseManager();

            db.open();*/
            //predpovedNehody
            //cm.addAltitude();
            //cm.createDupliciteRecords(20);
            //cm.cccreateAccidentRecords(50,50);
            //cm.vypiss();
            // cm.loadAllToDatabase();
            //cm.loadFromDatabase(AccidentRecord.allHeaderRow,false);
            //cm.vypiss();
            //cm.loadInfo3ToDatabase();
            //cm.loadAccidentSeverityToDatabase();
            //cm.loadFromDatabase();
            //cm.filter();

            //cm.createDupliciteRecords(10);
//cm.createAccidentRecordsAltitude(70);

            //cm.loadAllToDatabase();
            //obecne
            //cm.removeMissingValues(22);
            cm.updateTime(2);
            //cm.addId();
            //cm.updateOlderCsvFile();
            //cm.updateCsvFile();

            //pocet nehod za den
            //cm.sortByDate(0);
            //cm.pripravDny(2000);
            //cm.removeMissingValues(11);
            //cm.removeDate(0);

            // cm.sortByDay(5);


            // cm.loadToDatabase();
            // cm.addDatabaseDataToFile();
            //cm.createAccidentRecords();
            //cm.addId();

            //VehicleCsvFileManager vcm=new VehicleCsvFileManager(new File("IVozidla.csv"), ';');           
            //vcm.addVehicleTypeToDatabase();
            //vcm.updateCsvFile();
            //CoordinatesCsvManager csm=new CoordinatesCsvManager(new File("souradnice.csv"), ';');
            // csm.addCoordinatesDataToDatabase();
            //cm.sortByCoordinates(755830,1166150,500);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.nehoda;

import com.mycompany.nehoda.CsvManagers.CsvManager;
import com.mycompany.nehoda.CsvManagers.*;

import java.io.File;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import org.apache.commons.lang3.ArrayUtils;

/**
 *
 * @author dorgu
 */
public class Nehoda {

    public static void main(String[] args) {
        try {

            CsvManager cm = new CsvManager(new File("predpovedNehody.csv"), ',');
           // cm.updateOlderCsvFile();
            //cm.updateCsvFile();               
           // cm.loadToDatabase();
            // cm.addDatabaseDataToFile();
cm.addId();
            
            //cm.pripravDny();            
             //cm.createAccidentRecords();
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

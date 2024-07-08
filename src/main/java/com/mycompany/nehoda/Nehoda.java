/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.nehoda;

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

            CsvManager cm = new CsvManager(new File("iNehody.csv"), ';');
            //cm.loadCsvData();
            //cm.nastavCas(0);
            //cm.writeData();
            //cm.pripravDny();
            cm.updateCsvFile();
            //cm.prohledejData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.nehoda.CsvManagers;

import com.opencsv.exceptions.CsvException;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 *@author David
 */
public class CoordinatesCsvManager extends CsvManager {

    public CoordinatesCsvManager(File csvFile, char separator) throws IOException {
        super(csvFile, separator);

    }

    public void addCoordinatesDataToDatabase() throws SQLException, IOException, CsvException, ClassNotFoundException {

        loadCsvData();
        dm.open();
        ResultSet rs;
        String[] actualRow;

        for (int row = 1; row < csvData.size(); row++) {

            actualRow = csvData.get(row);
            rs = dm.getById(Long.parseLong(actualRow[0]));

            if (rs.next()) {
                rs.updateInt("souradniceX", Integer.parseInt(actualRow[1]));
                rs.updateInt("souradniceY", Integer.parseInt(actualRow[2]));
                rs.updateString("obec", actualRow[3]);
                rs.updateRow();
            }

            rs.close();
        }

        dm.close();

    }
}

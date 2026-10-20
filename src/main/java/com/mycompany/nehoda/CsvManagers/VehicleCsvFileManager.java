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
import java.util.List;
import com.mycompany.nehoda.DatabaseManagers.VehicleDatabaseManager;

/**
 *
 *@author David
 */
public class VehicleCsvFileManager extends CsvManager {

    private VehicleDatabaseManager vdm;

    public VehicleCsvFileManager(File csvFile, char separator) throws IOException {
        super(csvFile, separator);
        vdm = new VehicleDatabaseManager();
    }

    @Override
    public void updateCsvFile() throws IOException, CsvException {
        loadCsvData();

        for (int row = 1; row < csvData.size(); row++) {
            updateColumn(row, 2, pm.getVehicleType(getData(row, 2)));
        }

        writeData();
    }

    public void loadToDatabase() throws IOException, SQLException, CsvException, ClassNotFoundException {

        loadCsvData();
        vdm.open();
        ResultSet rs = vdm.selectAll();
        String[] actualRow;

        for (int row = 1; row < csvData.size(); row++) {

            actualRow = csvData.get(row);
            vdm.insert(rs, actualRow);
        }

        rs.close();
        vdm.close();

    }

    public void addVehicleTypeToDatabase() throws SQLException, IOException, CsvException, ClassNotFoundException {

        loadCsvData();
        dm.open();
        ResultSet rs;
        String[] actualRow;

        for (int row = 1; row < csvData.size(); row++) {

            actualRow = csvData.get(row);

            if (actualRow[1].equals("1")) {
                rs = dm.getById(Long.parseLong(actualRow[0]));

                if (rs.next()) {

                    rs.updateString("typVozidla", actualRow[2]);
                    rs.updateRow();
                }

                rs.close();
            }
        }

        dm.close();

    }

}

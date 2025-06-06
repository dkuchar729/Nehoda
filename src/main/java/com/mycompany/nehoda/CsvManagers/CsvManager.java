/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.nehoda.CsvManagers;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.mycompany.nehoda.AccidentRecord;
import com.mycompany.nehoda.AccidentRecordsCreator;
import com.mycompany.nehoda.DatabaseManagers.AccidentDatabaseManager;
import com.mycompany.nehoda.ParameterManager;
import com.mycompany.nehoda.AccidentRecordManager;
import com.mycompany.nehoda.TimeManager;
import com.mycompany.nehoda.WeatherInfo;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author dorgu
 */
public class CsvManager {

    protected File csvFile;

    private CSVReader cr;
    private AccidentRecordManager sprava;
    private CSVWriter cw;
    protected List<String[]> csvData;
    protected String[] actualRow;
    protected char separator;
    private final DateTimeFormatter df;
    private final DateTimeFormatter bf;
    protected final ParameterManager pm;
    private LocalDate lastDate;

    protected List<Integer> columnsToRemove;
    protected AccidentDatabaseManager dm;
    protected List<Integer> rowsToRemove;

    private String[] lastWeatherRow;

    public CsvManager(File csvFile, char separator) throws FileNotFoundException, IOException {
        this.csvFile = csvFile;
        this.separator = separator;
        lastDate = LocalDate.MIN;
        sprava = new AccidentRecordManager();
        dm = new AccidentDatabaseManager();
        pm = new ParameterManager();
        columnsToRemove = new LinkedList<>();

        df = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        bf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    }

    public void loadToDatabase() throws IOException, CsvException, SQLException, ClassNotFoundException {
        loadCsvData();
        dm.open();
        for (int row = 1; row < csvData.size(); row++) {
            dm.insert(csvData.get(row));
        }
        dm.close();

    }

    public void updateCsvFile() throws IOException, CsvException {
        loadCsvData();
        rowsToRemove = new LinkedList<>();

        updateHeader();
        for (int row = 1; row < csvData.size(); row++) {

            if (!updateHours(row, pm.getIndexes().getTime())) {

                rowsToRemove.add(row);
                continue;
            }

            updateColumn(row, pm.getIndexes().getDistrict(), pm.getDistrictValue(getData(row, pm.getIndexes().getDistrict())));
            updateColumn(row, pm.getIndexes().getTown(), pm.getTownValue(getData(row, pm.getIndexes().getTown())));
            updateColumn(row, pm.getIndexes().getAccidentType(), pm.getAccidentTypeValue(getData(row, pm.getIndexes().getAccidentType())));
            updateColumn(row, pm.getIndexes().getGuilty(), pm.getGuiltyValue(getData(row, pm.getIndexes().getGuilty())));
            updateColumn(row, pm.getIndexes().getAlcohol(), pm.getAlcoholValue(getData(row, pm.getIndexes().getAlcohol())));
            updateColumn(row, pm.getIndexes().getCause(), pm.getCauseValue(getData(row, pm.getIndexes().getCause())));
            updateColumn(row, pm.getIndexes().getSurface(), pm.getSurfaceValue(getData(row, pm.getIndexes().getSurface())));
            updateColumn(row, pm.getIndexes().getWeatherConditions(), pm.getWeatherValue(getData(row, pm.getIndexes().getWeatherConditions())));
            updateColumn(row, pm.getIndexes().getVisibility(), pm.getVisibilityValue(getData(row, pm.getIndexes().getVisibility())));
            updateColumn(row, pm.getIndexes().getRoadLine(), pm.getRoadLineValue(getData(row, pm.getIndexes().getRoadLine())));
            updateColumn(row, pm.getIndexes().getRoadType(), pm.getRoadTypeValue(getData(row, pm.getIndexes().getRoadType())));

            updateColumn(row, pm.getIndexes().getDirection(), pm.getDirectionValue(getData(row, pm.getIndexes().getDirection())));
            updateColumn(row, pm.getIndexes().getRoadState(), pm.getRoadState(getData(row, pm.getIndexes().getRoadState())));
            updateColumn(row, pm.getIndexes().getVision(), pm.getVision(getData(row, pm.getIndexes().getVision())));
            updateColumn(row, pm.getIndexes().getRoadArea(), pm.getRoadArea(getData(row, pm.getIndexes().getRoadArea())));
            updateColumn(row, pm.getIndexes().getSpecificPlace(), pm.getSpecificPlace(getData(row, pm.getIndexes().getSpecificPlace())));
            updateColumn(row, pm.getIndexes().getVehicleCrashType(), pm.getVehicleCrashType(getData(row, pm.getIndexes().getVehicleCrashType())));
            updateColumn(row, pm.getIndexes().getObstacleCrashType(), pm.getObstacleCrashType(getData(row, pm.getIndexes().getObstacleCrashType())));
            updateColumn(row, pm.getIndexes().getRoadSurfaceType(), pm.getRoadSurfaceType(getData(row, pm.getIndexes().getRoadSurfaceType())));

            addValue(row, pm.getAccidentSeverity(updateAccidentSeverity(row)));

            LocalDate date = parseDate(getData(row, pm.getIndexes().getDate()), true);
            addValue(row, pm.getDayOfWeekValue(date.getDayOfWeek().getValue()));
            addWeatherData(row, date, WeatherInfo.JIHOCESKY, 2);
            addValue(row, getTrafficDensity(getData(row, pm.getIndexes().getRoadName()), 1, 5));

            //updateColumn(row, pm.getIndexes().getDate(), DateManager.getDefaultDateAsString(getData(row, pm.getIndexes().getDate())));
        }

        removeRows();
        removeColumns();

        //updateTime();
        writeData();
    }

    public void updateOlderCsvFile() throws IOException, CsvException {
        loadCsvData();
        rowsToRemove = new LinkedList<>();

        updateOlderHeader();
        for (int row = 1; row < csvData.size(); row++) {

            if (!updateHours(row, pm.getIndexes().getTime())) {

                rowsToRemove.add(row);
                continue;
            }

            updateColumn(row, pm.getIndexes().getDistrict(), pm.getDistrictValue(parseId(getData(row, pm.getIndexes().getId()))));
            updateColumn(row, pm.getIndexes().getTown(), pm.getTownValue(getData(row, pm.getIndexes().getTown())));
            updateColumn(row, pm.getIndexes().getAccidentType(), pm.getAccidentTypeValue(getData(row, pm.getIndexes().getAccidentType())));
            updateColumn(row, pm.getIndexes().getGuilty(), pm.getGuiltyValue(getData(row, pm.getIndexes().getGuilty())));
            updateColumn(row, pm.getIndexes().getAlcohol(), pm.getAlcoholValue(getData(row, pm.getIndexes().getAlcohol())));
            updateColumn(row, pm.getIndexes().getCause(), pm.getCauseValue(getData(row, pm.getIndexes().getCause())));
            updateColumn(row, pm.getIndexes().getSurface(), pm.getSurfaceValue(getData(row, pm.getIndexes().getSurface())));
            updateColumn(row, pm.getIndexes().getWeatherConditions(), pm.getWeatherValue(getData(row, pm.getIndexes().getWeatherConditions())));
            updateColumn(row, pm.getIndexes().getVisibility(), pm.getVisibilityValue(getData(row, pm.getIndexes().getVisibility())));
            updateColumn(row, pm.getIndexes().getRoadLine(), pm.getRoadLineValue(getData(row, pm.getIndexes().getRoadLine())));
            updateColumn(row, pm.getIndexes().getRoadType(), pm.getRoadTypeValue(getData(row, pm.getIndexes().getRoadType())));
            updateColumn(row, pm.getIndexes().getDirection(), pm.getDirectionValue(getData(row, pm.getIndexes().getDirection())));
            updateColumn(row, pm.getIndexes().getRoadState(), pm.getRoadState(getData(row, pm.getIndexes().getRoadState())));
            updateColumn(row, pm.getIndexes().getVision(), pm.getVision(getData(row, pm.getIndexes().getVision())));
            updateColumn(row, pm.getIndexes().getRoadArea(), pm.getRoadArea(getData(row, pm.getIndexes().getRoadArea())));
            updateColumn(row, pm.getIndexes().getSpecificPlace(), pm.getSpecificPlace(getData(row, pm.getIndexes().getSpecificPlace())));
            updateColumn(row, pm.getIndexes().getVehicleType(), pm.getVehicleType(getData(row, pm.getIndexes().getVehicleType())));
            updateColumn(row, pm.getIndexes().getVehicleCrashType(), pm.getVehicleCrashType(getData(row, pm.getIndexes().getVehicleCrashType())));
            updateColumn(row, pm.getIndexes().getObstacleCrashType(), pm.getObstacleCrashType(getData(row, pm.getIndexes().getObstacleCrashType())));
            updateColumn(row, pm.getIndexes().getRoadSurfaceType(), pm.getRoadSurfaceType(getData(row, pm.getIndexes().getRoadSurfaceType())));

            addValue(row, pm.getAccidentSeverity(updateAccidentSeverity(row)));

            LocalDate date = parseDate(getData(row, pm.getIndexes().getDate()), false);
            addValue(row, pm.getDayOfWeekValue(date.getDayOfWeek().getValue()));
            addWeatherData(row, date, WeatherInfo.JIHOCESKY, 2);
            addValue(row, getTrafficDensity(getData(row, pm.getIndexes().getRoadName()), 1, 5));

            updateColumn(row, pm.getIndexes().getDate(), date.format(df));

        }

        removeRows();
        //updateTime();
        removeColumns();

        writeData();
    }
public void AddSpecificColumn() throws IOException, CsvException {
    loadCsvData();
    updateHeader();

    for (int row = 1; row < csvData.size(); row++) {
       addValue(row, pm.getDirectionTypeValue(getData(row,pm.getIndexes().getDirection())));
    }
    writeData();
}
    public LocalDate getDate(String date) {
        return LocalDate.parse(date, df);
    }

    public void addDatabaseDataToFile() throws SQLException, ClassNotFoundException, IOException, CsvException {
        loadCsvData();
        dm.open();
        ResultSet rs;
        String value;
        for (int row = 1; row < csvData.size(); row++) {
            rs = dm.getById(Long.parseLong(getData(row, 0)));

            if (rs.next()) {
                value = rs.getString("typVozidla");

                if (value != null) {
                    value = value.trim();
                    updateColumn(row, 28, value);
                }

                value = rs.getString("obec");

                if (value != null) {
                    updateColumn(row, 29, value);
                }

                updateColumn(row, 30, String.valueOf(rs.getLong("souradniceX")));
                updateColumn(row, 31, String.valueOf(rs.getLong("souradniceY")));
            }
            rs.close();
        }

        dm.close();

        writeData();

    }

    public void updateTime() throws IOException, CsvException {
        for (int row = 1; row < csvData.size(); row++) {
            updateColumn(row, pm.getIndexes().getTime(), getDaytime(getData(row, pm.getIndexes().getTime())));
        }

    }

    public void updateTime(int index) throws IOException, CsvException {

        loadCsvData();
        for (int row = 1; row < csvData.size(); row++) {
            updateColumn(row, index, getDaytime(getData(row, index)));
        }
        writeData();
    }

    public List<String[]> updateTime(int index, List<String[]> list) throws IOException, CsvException {
        String[] rowToUpdate;
        loadCsvData();
        for (int row = 0; row < list.size(); row++) {
            rowToUpdate = list.get(row);
            rowToUpdate[index] = getDaytime(rowToUpdate[index]);
            list.set(index, rowToUpdate);
        }
        return list;
    }

    public List<String[]> updateTimeList(int index, List<String[]> list) throws IOException, CsvException {
        String[] rowToUpdate;

        for (int row = 0; row < list.size(); row++) {
            rowToUpdate = list.get(row);
            rowToUpdate[index] = getDaytime(rowToUpdate[index]);
            list.set(row, rowToUpdate);
        }
        return list;
    }

    public String getDaytime(String value) {

        int hodina = Integer.parseInt(value);

        if (hodina > -1 && hodina < 6 || hodina > 20 && hodina < 24) {
            return "noc";
        } else if (hodina > 5 && hodina < 10) {
            return "rano";
        } else if (hodina > 9 && hodina < 12) {
            return "dopoledne";
        } else if (hodina > 11 && hodina < 17) {
            return "odpoledne";
        } else {
            return "vecer";
        }
    }

    public void createAccidentRecords() throws IOException, CsvException {

        loadCsvData();
        AccidentRecordManager sprava = new AccidentRecordManager();
        List<Integer> skipRow = new LinkedList<>();
        String[] actualRow;

        for (int row = 1; row < csvData.size(); row++) {

            if (skipRow.contains(row)) {
                continue;
            }

            addValue(row, "1");
            actualRow = csvData.get(row);
            sprava.processNewRow(actualRow);

            for (int i = row + 1; i < csvData.size(); i++) {
                if (skipRow.contains(i)) {
                    continue;
                }

                if (sprava.compareWithLastInputRow(csvData.get(i))) {
                    addValue(i, "1");
                    skipRow.add(i);
                }
            }
        }

        for (AccidentRecordsCreator arc : sprava.getAccidentRecorsCreatorsList()) {
            for (String[] newRecord : arc.getNewRecords()) {
                csvData.add(newRecord);
            }
        }

        writeData();
    }

    public void ccreateAccidentRecords() throws IOException, CsvException {
        CsvManager srazkyc = new CsvManager(new File("data/predpovedNehody/srazky.csv"), ';');
        CsvManager vysledek = new CsvManager(new File("data/predpovedNehody/vysledekTest.csv"), ';');
        srazkyc.loadCsvData();

        AccidentRecordManager sprava = new AccidentRecordManager();
        List<Integer> skipRow = new LinkedList<>();
        String[] actualRow;

        for (int row = 1; row < srazkyc.csvData.size(); row++) {

            if (skipRow.contains(row)) {
                continue;
            }

            srazkyc.addValue(row, "1");
            actualRow = srazkyc.csvData.get(row);
            sprava.processNewRow(actualRow);

            for (int i = row + 1; i < srazkyc.csvData.size(); i++) {
                if (skipRow.contains(i)) {
                    continue;
                }

                if (sprava.compareWithLastInputRow(srazkyc.csvData.get(i))) {
                    srazkyc.addValue(i, "1");
                    skipRow.add(i);
                }
            }
        }

        for (AccidentRecordsCreator arc : sprava.getAccidentRecorsCreatorsList()) {
            for (String[] newRecord : arc.getNewRecords()) {
                srazkyc.csvData.add(newRecord);
            }
        }

        vysledek.loadCsvData();
        for (int row = 1; row < srazkyc.csvData.size(); row++) {
            vysledek.csvData.add(srazkyc.csvData.get(row));
        }
        //srazkyc.closeCsvWriter();
        vysledek.writeData();

    }

    public void cccreateAccidentRecords(int coordinatesRange) throws IOException, CsvException, SQLException, ClassNotFoundException {

        CsvManager vysledek = new CsvManager(new File("data/predpovedNehody/vysledekTest.csv"), ';');
        List<String[]> allRecords = new LinkedList<>();
        AccidentRecordManager sprava = new AccidentRecordManager();
        List<Integer> skipRow;
        String[] actualRow;

        dm.open();
        List<List<String[]>> l = dm.getDataToAccidentExecutionn(AccidentRecord.headerRowAltitude, coordinatesRange);
        dm.close();

        for (int group = 0; group < l.size(); group++) {

            sprava = new AccidentRecordManager();
            List<String[]> groupList = l.get(group);
            skipRow = new LinkedList<>();
            groupList = updateTimeList(0, groupList);

            for (int row = 0; row < groupList.size(); row++) {

                if (skipRow.contains(row)) {

                    continue;
                }

                actualRow = getNewRow(groupList.get(row), "1");

                groupList.set(row, actualRow);

                sprava.processNewRow(actualRow);

                for (int i = row + 1; i < groupList.size(); i++) {

                    if (skipRow.contains(i)) {
                        continue;
                    }

                    if (sprava.compareWithLastInputRow(groupList.get(i))) {

                        String[] nextRow = getNewRow(groupList.get(i), "1");
                        groupList.set(i, nextRow);

                        skipRow.add(i);
                    }
                }
            }

            for (AccidentRecordsCreator arc : sprava.getAccidentRecorsCreatorsList()) {
                for (String[] newRecord : arc.getNewRecords()) {

                    groupList.add(newRecord);
                }
            }

            for (int row = 0; row < groupList.size(); row++) {

                allRecords.add(groupList.get(row));
            }
        }
        vysledek.loadCsvData();
        int c = 0;
        for (int i = 0; i < allRecords.size(); i++) {

            if (allRecords.get(i).length < 24) {
                c++;
                //System.out.println(i + " " + allRecords.get(i)[22]);
            }
            vysledek.csvData.add(allRecords.get(i));

        }
        System.out.println("c " + c);

        vysledek.writeData();

    }
       public void createAccidentRecordsAltitude( int altitudeRange) throws IOException, CsvException, SQLException, ClassNotFoundException {

        CsvManager vysledek = new CsvManager(new File("data/predpovedNehody/vysledekNadmorska.csv"), ';');
        List<String[]> allRecords = new LinkedList<>();
        AccidentRecordManager sprava = new AccidentRecordManager();
        List<Integer> skipRow;
        String[] actualRow;

        dm.open();
        List<List<String[]>> l = dm.getDataToAccidentExecutionAltitude(AccidentRecord.headerRowAltitude, altitudeRange);
        dm.close();

        for (int group = 0; group < l.size(); group++) {

            sprava = new AccidentRecordManager();
            List<String[]> groupList = l.get(group);
            skipRow = new LinkedList<>();
            groupList = updateTimeList(0, groupList);

            for (int row = 0; row < groupList.size(); row++) {

                if (skipRow.contains(row)) {

                    continue;
                }

                actualRow = getNewRow(groupList.get(row), "1");

                groupList.set(row, actualRow);

                sprava.processNewRow(actualRow);

                for (int i = row + 1; i < groupList.size(); i++) {

                    if (skipRow.contains(i)) {
                        continue;
                    }

                    if (sprava.compareWithLastInputRow(groupList.get(i))) {

                        String[] nextRow = getNewRow(groupList.get(i), "1");
                        groupList.set(i, nextRow);

                        skipRow.add(i);
                    }
                }
            }

            for (AccidentRecordsCreator arc : sprava.getAccidentRecorsCreatorsList()) {
                for (String[] newRecord : arc.getNewRecords()) {

                    groupList.add(newRecord);
                }
            }

            for (int row = 0; row < groupList.size(); row++) {

                allRecords.add(groupList.get(row));
            }
        }
        vysledek.loadCsvData();
        int c = 0;
        for (int i = 0; i < allRecords.size(); i++) {

            if (allRecords.get(i).length < 24) {
                c++;
                //System.out.println(i + " " + allRecords.get(i)[22]);
            }
            vysledek.csvData.add(allRecords.get(i));

        }
        System.out.println("c " + c);

        vysledek.writeData();

    }

    public void updateHeader() throws IOException, CsvException {
        String[] header = csvData.get(0);

        for (int i = 0; i < header.length; i++) {
            if (!pm.setHeaderIndex(i, header[i])) {

               // System.out.println(i+" "+pm.setHeaderIndex(i, header[i]));
                System.out.println(getData(0,i));
                columnsToRemove.add(i);
            }

            updateColumn(0, i, pm.getHeaderValue(header[i]));
        }

        addValues(0, new String[]{"zavaznostNehody", "den", "maxPrumTeplota", "minPrumTeplota", "prumSrazky", "minPrumSrazky", "maxPrumSrazky", "hustotaProvozu", "typVozidla", "obec", "souradniceX", "souradniceY"});
        pm.setHeaderIndex(csvData.get(0).length - 11, "zavaznostNehody");
        columnsToRemove = columnsToRemove.reversed();

    }

    public void updateOlderHeader() throws IOException, CsvException {
        String[] header = csvData.get(0);

        for (int i = 0; i < header.length; i++) {
            if (!pm.setHeaderIndex(i, header[i])) {
                columnsToRemove.add(i);
            }
            updateColumn(0, i, pm.getHeaderValue(header[i]));
        }

        addValues(0, new String[]{"zavaznostNehody", "den", "maxPrumTeplota", "minPrumTeplota", "prumSrazky", "minPrumSrazky", "maxPrumSrazky", "hustotaProvozu"});
        pm.setHeaderIndex(csvData.get(0).length - 8, "zavaznostNehody");

        columnsToRemove = columnsToRemove.reversed();

    }

    private void removeRows(List<Integer> rowsToRemove) {
        for (int i = rowsToRemove.size() - 1; i >= 0; --i) {
            csvData.remove(rowsToRemove.get(i).intValue());
        }
    }

    public String getTrafficDensity(String roadName, int roadNameColumnIndex, int carCountColumnIndex) throws IOException, CsvException {
        List<String[]> trafficDensityData = loadCsvData(new File("hustotaJizniCechy.csv"), ';');

        int count = 0;
        int total = 0;

        for (int row = 1; row < trafficDensityData.size(); row++) {
            if (trafficDensityData.get(row)[roadNameColumnIndex].equals(roadName)) {

                total += Integer.parseInt(trafficDensityData.get(row)[carCountColumnIndex]);
                count++;
            }
        }

        if (count != 0) {
            return String.valueOf(total / count);
        }
        return "null";
    }

    public boolean addColumnValues(List<String> data) throws IOException, CsvException {

        for (int i = 0; i < data.size(); i++) {
            addValue(i, data.get(i));
        }
        writeData();

        return csvData.size() - 1 == data.size();
    }

    public boolean updateColumnValues(List<String> data, int column) throws IOException, CsvException {
        loadCsvData();
        for (int row = 0; row < data.size(); row++) {
            updateColumn(row, column, data.get(row));
        }
        writeData();

        return csvData.size() - 1 == data.size();
    }

    public String getData(int row, int column) throws IOException, CsvException {
        return csvData.get(row)[column];
    }

    public void addDays() throws IOException, CsvException {
        loadCsvData();
        for (int i = 1; i < csvData.size(); i++) {
            addValue(i, String.valueOf(LocalDate.parse(getData(i, 1), df).getDayOfWeek().getValue()));

        }
        writeData();

    }

    public boolean updateHours(int row, int column) throws IOException, CsvException {
        String hour = TimeManager.returnHour(getData(row, column));
        if (hour == null) {
            return false;
        }
        updateColumn(row, column, hour);
        return true;
    }

    public String[] getWeatherDataRow(File csvFile, char delimiter, int dateColumnIndex, LocalDate requestedDate) throws IOException, CsvException {

        List<String[]> weatherData = loadCsvData(csvFile, delimiter);

        for (int row = 1; row < weatherData.size(); row++) {
            String[] weatherRow = weatherData.get(row);
            LocalDate weatherDate = LocalDate.parse(weatherRow[dateColumnIndex], df);

            if (weatherDate.isEqual(requestedDate)) {
                return weatherRow;
            }
        }
        return null;

    }

    private void addWeatherData(int row, LocalDate requestedDate, WeatherInfo weatherInfo, int startWeatherColumnIndex) throws IOException, CsvException {

        if (!lastDate.isEqual(requestedDate)) {

            String[] weatherRow = getWeatherDataRow(weatherInfo.getRegionWeatherFile(), ',', weatherInfo.getWeatherDateColumnIndex(), requestedDate);

            if (weatherRow != null) {
                lastWeatherRow = Arrays.copyOfRange(weatherRow, startWeatherColumnIndex, weatherRow.length);
                lastDate = parseDate(weatherRow[weatherInfo.getWeatherDateColumnIndex()], true);
                addValues(row, lastWeatherRow);
            }

        } else {
            addValues(row, lastWeatherRow);
        }

    }

    public void addId() throws IOException, CsvException {
        loadCsvData();

        for (int row = 1; row < csvData.size(); row++) {
            addValue(row, String.valueOf(row));
        }

        writeData();
    }

    public void addValue(int row, String value) throws IOException, CsvException {

        csvData.set(row, getNewRow(csvData.get(row), value));
    }

    public void addValues(int row, String[] values) throws IOException, CsvException {
        csvData.set(row, getNewRow(csvData.get(row), values));
    }

    public void updateColumn(int row, int column, String value) throws IOException, CsvException {
        csvData.get(row)[column] = value;
    }

    public void loadCsvData() throws IOException, CsvException {
        resetCsvReader();
        csvData = cr.readAll();
        cr.close();
    }

    public List<String[]> loadCsvData(File file, char delimiter) throws IOException, CsvException {
        CSVReader cr = new CSVReader(new FileReader(file), delimiter);
        List<String[]> csvData = cr.readAll();
        cr.close();
        return csvData;
    }

    public void writeData() throws IOException {
        resetCsvWriter();
        cw.writeAll(csvData, false);
        closeCsvWriter();
    }

    public static String[] getNewRow(String[] originalRow, String[] values) {
        String[] newRow = new String[originalRow.length + values.length];

        System.arraycopy(originalRow, 0, newRow, 0, originalRow.length);
        System.arraycopy(values, 0, newRow, originalRow.length, values.length);

        return newRow;

    }

    public static String[] getNewRow(String[] originalRow, String value) {
        String[] newRow = new String[originalRow.length + 1];
        System.arraycopy(originalRow, 0, newRow, 0, originalRow.length);
        newRow[newRow.length - 1] = value;
        return newRow;

    }

    private LocalDate parseDate(String date, boolean base) {
        if (base) {
            return LocalDate.parse(date, df);
        }

        return LocalDate.parse(date, bf);

    }

    private void resetCsvReader() throws FileNotFoundException {
        cr = new CSVReader(new FileReader(csvFile), separator);
    }

    private void resetCsvWriter() throws FileNotFoundException, IOException {
        cw = new CSVWriter(new FileWriter(csvFile), separator);
    }

    private void closeCsvWriter() throws IOException {
        cw.flush();
        cw.close();
    }

    private void getCarData(File carsFile, char delimiter, int searchedRow, String id) throws IOException, CsvException {
        List<String[]> carsData = loadCsvData(carsFile, delimiter);

        for (int row = 1; row < carsData.size(); row++) {
            if (id.equals(carsData.get(row)[0])) {
                addValue(searchedRow, carsData.get(row)[2]);

            }
        }
        addValue(searchedRow, "null");

    }

    public void sortByCoordinates(double centerX, double centerY, double range) throws IOException, CsvException {
        loadCsvData();
        rowsToRemove = new LinkedList<>();
        for (int row = 1; row < csvData.size(); row++) {

            double x = Double.parseDouble(csvData.get(row)[18]) * -1;
            double y = Double.parseDouble(csvData.get(row)[19]) * -1;

            if ((x > centerX + range || x < centerX - range) || (y < centerY - range || y > centerY + range)) {
                rowsToRemove.add(row);
            } else {

            }
        }
        for (int i = rowsToRemove.size() - 1; i > -1; i--) {
            csvData.remove((int) rowsToRemove.get(i));
        }
        writeData();
    }

    public boolean checkCoordinates(double x, double y, double newX, double newY, double range) {
        return !((newX > x + range || newX < x - range) || (newY < y - range || newY > y + range));
    }

    private void removeRows() {
        for (int i = rowsToRemove.size() - 1; i > -1; i--) {
            csvData.remove((int) rowsToRemove.get(i));
        }
    }

    private void removeColumns() {
        for (int i = 0; i < csvData.size(); i++) {
            String[] arrayRow = csvData.get(i);
            List<String> listRow = new LinkedList<>(Arrays.asList(arrayRow));

            for (int columnIndex : columnsToRemove) {
                listRow.remove(columnIndex);
            }
            csvData.set(i, listRow.stream().toArray(String[]::new));
        }
    }

    private String parseId(String id) {
        return id.substring(3, 4);
    }

    public void filter() throws IOException, CsvException {
        loadCsvData();

        while (csvData.size() != 1) {
            CsvManager srazkyc = new CsvManager(new File("data/predpovedNehody/srazky.csv"), ';');
            actualRow = csvData.get(1);
            rowsToRemove = new LinkedList<>();
            rowsToRemove.add(1);

            if (csvData.size() > 2) {

                srazkyc.loadCsvData();
                srazkyc.csvData = getDat(srazkyc.csvData);
                srazkyc.csvData.add(actualRow);

                for (int row = 2; row < csvData.size(); row++) {
                    String[] newRow = csvData.get(row);

                    /*
                    (getByMaxTemp(Double.parseDouble(actualRow[17]), Double.parseDouble(actualRow[18]), Double.parseDouble(newRow[17]), Double.parseDouble(newRow[18])) && getByPrec(Double.parseDouble(actualRow[19]), Double.parseDouble(newRow[19])) 
                            && checkCoordinates(Double.parseDouble(actualRow[17]), Double.parseDouble(actualRow[18]), row, row, row)
                     */
                    if (getByMaxTemp(Double.parseDouble(actualRow[19]), Double.parseDouble(actualRow[20]), Double.parseDouble(newRow[19]), Double.parseDouble(newRow[20])) && getByPrec(Double.parseDouble(actualRow[21]), Double.parseDouble(newRow[21]))
                            && checkCoordinates(Double.parseDouble(actualRow[17]), Double.parseDouble(actualRow[18]), Double.parseDouble(newRow[17]), Double.parseDouble(newRow[18]), 1000)) {
                        rowsToRemove.add(row);
                        srazkyc.csvData.add(newRow);
                    }
                }
                srazkyc.writeData();

            } else {

                srazkyc.loadCsvData();
                srazkyc.csvData = getDat(srazkyc.csvData);
                srazkyc.csvData.add(actualRow);
                srazkyc.writeData();
            }
            removeRows();
            ccreateAccidentRecords();

        }

    }

    public boolean getByMaxTemp(double actualMax, double actualMin, double newMaxTemp, double newMinTemp) {

        if (!((actualMax > 0.4 && newMaxTemp <= 0.4) || (actualMin > 0.4 && newMinTemp <= 0.4)) && newMaxTemp >= actualMax - 5 && newMaxTemp <= actualMax && newMinTemp >= actualMin - 5 && newMinTemp <= actualMin) {
            return true;
        }

        return false;

    }

    public boolean getByPrec(double actualPrec, double newPrec) {
        if (actualPrec == 0 && newPrec == 0) {
            return true;
        } else if (actualPrec > 0 && actualPrec <= 0.3 && newPrec > 0 && newPrec <= 0.3) {
            return true;
        } else if (actualPrec > 0.3 && actualPrec <= 0.6 && newPrec > 0.3 && newPrec <= 0.6) {
            return true;
        } else if (actualPrec > 0.6 && actualPrec <= 0.9 && newPrec > 0.6 && newPrec <= 0.9) {

            return true;
        } else if (actualPrec > 0.9 && actualPrec <= 1.5 && newPrec > 0.9 && newPrec <= 1.5) {
            return true;
        } else if (actualPrec > 1.5 && actualPrec <= 2.9 && newPrec > 1.5 && newPrec <= 2.9) {
            return true;
        } else if (actualPrec > 2.9 && actualPrec <= 4.9 && newPrec > 2.9 && newPrec <= 4.9) {
            return true;
        } else if (actualPrec > 4.9 && newPrec > 4.9 && newPrec <= actualPrec + 5) {
            return true;
        }

        return false;
    }

    public List<String[]> getDat(List<String[]> data) {
        data = new LinkedList<String[]>();
        data.add(new String[]{"hodina", "umisteniObec", "druhNehody", "zavineniNehody", "alkohol", "pricinaNehody", "povrchVozovky", "stavVozovky", "povetrnostniPodminky", "viditelnost", "rozhled", "deleniKomunikace", "situovaniNehody", "specifickeMisto", "smerovyPomer", "druhKomunikace", "den", "souradniceX", "souradniceY", "maxPrumTeplota", "minPrumTeplota", "prumSrazky"});
        return data;
    }

    public void executePrec() throws IOException, CsvException {
        loadCsvData();

        while (csvData.size() != 1) {
            CsvManager srazkyc = new CsvManager(new File("data/predpovedNehody/srazky.csv"), ';');
            actualRow = csvData.get(1);
            rowsToRemove = new LinkedList<>();
            rowsToRemove.add(1);

            if (csvData.size() > 2) {

                srazkyc.loadCsvData();
                srazkyc.csvData = getDat(srazkyc.csvData);
                srazkyc.csvData.add(actualRow);

                for (int row = 2; row < csvData.size(); row++) {
                    String[] newRow = csvData.get(row);
                    if (getByMaxTemp(Double.parseDouble(actualRow[17]), Double.parseDouble(actualRow[18]), Double.parseDouble(newRow[17]), Double.parseDouble(newRow[18])) && getByPrec(Double.parseDouble(actualRow[19]), Double.parseDouble(newRow[19]))) {
                        rowsToRemove.add(row);
                        srazkyc.csvData.add(newRow);
                    }
                }
                srazkyc.writeData();
                removeRows();
            } else {

                srazkyc.loadCsvData();
                srazkyc.csvData = getDat(srazkyc.csvData);
                srazkyc.csvData.add(actualRow);
                srazkyc.writeData();
            }
        }
    }

    //pocetNehodZaDen
    public void createAccidentDayCountRecords(List<String[]> rows, int startIndex, int range) throws IOException, CsvException {
        Map<Integer, Double> densityMap = new HashMap<>();

        List<Integer> skipRow = new LinkedList<>();
        List<Integer> skipRow2 = new LinkedList<>();
        double firstDensity = 0;
        double secondDensity = 0;
        for (int i = 0; i < rows.size(); i++) {

            if (!densityMap.containsKey(i)) {
             
                densityMap.put(i, Double.valueOf(rows.get(i)[10]));
            }
            startIndex++;
            if (skipRow.contains(i)) {
                continue;
            }

            if (densityMap.containsKey(i)) {
                firstDensity = densityMap.get(i);

            } else {
                firstDensity = Double.parseDouble(rows.get(i)[10]);
            }

            if (!skipRow2.contains(i)) {
                sprava.pridejDen(rows.get(i), firstDensity, range);
            }

            int counter = 1;
            for (int j = i + 1; j < rows.size(); j++) {

                if (skipRow.contains(j)) {
                    continue;
                }

                if (densityMap.containsKey(j)) {
                    secondDensity = densityMap.get(j);

                } else {
                    secondDensity = Double.parseDouble(rows.get(j)[10]);
                }


                if (AccidentRecordsCreator.porovnejDenDvaZaznam(rows.get(i), rows.get(j), sprava.lastAccidentRecordsCreator.getSurface(), sprava.lastAccidentRecordsCreator.getRoadState(), sprava.lastAccidentRecordsCreator.getVisibility())
                        && AccidentRecordsCreator.checkDensity(firstDensity, secondDensity, range)) {//   && AccidentRecordsCreator.checkDensity(firstDensity, secondDensity, 1000)

                    sprava.porovnejDen(rows.get(j), secondDensity, range);
                    densityMap.put(j, firstDensity);
                    skipRow2.add(j);

                    if (AccidentRecordsCreator.compareIdenticalRow(rows.get(i), rows.get(j), sprava.lastAccidentRecordsCreator.getAccidentType(), sprava.lastAccidentRecordsCreator.getRoadType())) {
                        skipRow.add(j);

                        counter++;
                    }

                    rows.get(j)[10] = String.valueOf(Math.round(firstDensity));
                }
            }

            updateColumn(startIndex, sprava.lastAccidentRecordsCreator.getResult(), String.valueOf(counter));

        }
    }

    public boolean compareDate(String[] row1, String[] row2, int dateIndex) {
        return LocalDate.parse(row1[dateIndex], df).isEqual(LocalDate.parse(row2[dateIndex], df));
    }

    public boolean compareRows(String[] firstRow, String[] secondRow, int druhNehody, int povrchKomunikace, int stavKomunikace, int viditelnost, int druhKomunikace) {
        return AccidentRecordsCreator.compareRowsItem(firstRow, secondRow, druhNehody) && AccidentRecordsCreator.compareRowsItem(firstRow, secondRow, povrchKomunikace)
                && AccidentRecordsCreator.compareRowsItem(firstRow, secondRow, stavKomunikace) && AccidentRecordsCreator.compareRowsItem(firstRow, secondRow, viditelnost)
                && AccidentRecordsCreator.compareRowsItem(firstRow, secondRow, druhKomunikace);
    }

    public void pripravDny(int range) throws IOException, CsvException {
        loadCsvData();

        List<String[]> rows = new LinkedList<>();
        String[] hlavniRow = csvData.get(1);
        rows.add(hlavniRow);

        for (int i = 2; i < csvData.size(); i++) {
            String[] row2 = csvData.get(i);
            if (compareDate(hlavniRow, row2, 0)) {
                rows.add(row2);

                if (i == csvData.size() - 1) {
                    createAccidentDayCountRecords(rows, i - rows.size(), range);
                }
            } else {
                createAccidentDayCountRecords(rows, i - rows.size() - 1, range);
                hlavniRow = csvData.get(i);
                rows = new LinkedList<>();
                rows.add(hlavniRow);

            }
        }
        for (AccidentRecordsCreator nastaveni : sprava.getAccidentRecorsCreatorsList()) {

            for (String[] rowDay : nastaveni.vratDnyRadky(sprava.lastAccidentRecordsCreator.getAccidentType(), sprava.lastAccidentRecordsCreator.getRoadType(), sprava.lastAccidentRecordsCreator.getResult())) {

                csvData.add(rowDay);
            }
        }
        writeData();
    }

    public String updateAccidentSeverity(int row) throws IOException, CsvException {
        String severityAccidentValue = "null";
        if (getData(row, pm.getIndexes().getNatureOfAccident()).equals("2")) {
            severityAccidentValue = "1";
        } else {
            if (Integer.parseInt(getData(row, pm.getIndexes().getMinorAccident())) > 0) {
                severityAccidentValue = "2";
            }

            if (Integer.parseInt(getData(row, pm.getIndexes().getSeriousAccident())) > 0) {
                severityAccidentValue = "3";
            }

            if (Integer.parseInt(getData(row, pm.getIndexes().getFatalAccident())) > 0) {
                severityAccidentValue = "4";
            }
        }

        return severityAccidentValue;
    }

    public void loadAccidentSeverityToDatabase() throws IOException, CsvException, SQLException, ClassNotFoundException {
        loadCsvData();
        for (int row = 1; row < csvData.size(); row++) {
            dm.open();
            dm.loadAccidentSeverity(Long.parseLong(getData(row, 0)), getData(row, csvData.get(0).length - 5));
            dm.close();
        }
    }

    public void loadInfo3ToDatabase() throws IOException, CsvException, SQLException, ClassNotFoundException {
        loadCsvData();
        String[] actualRoww;
        for (int row = 1; row < csvData.size(); row++) {
            actualRoww = csvData.get(row);
            dm.open();
            dm.loadStringToDatabaseById(Long.parseLong(actualRoww[0]), "typSrazkyVozidlo", actualRoww[actualRoww.length - 4]);
            dm.loadStringToDatabaseById(Long.parseLong(actualRoww[0]), "typSrazkyPrekazka", actualRoww[actualRoww.length - 3]);
            dm.loadStringToDatabaseById(Long.parseLong(actualRoww[0]), "druhPovrchu", actualRoww[actualRoww.length - 2]);
            dm.loadIntToDatabaseById(Long.parseLong(actualRoww[0]), "pocetVozidel", Integer.parseInt(actualRoww[actualRoww.length - 1]));
            dm.close();
        }
    }

    public void loadAllFromDatabase() throws CsvException, IOException, SQLException, ClassNotFoundException {
        loadCsvData();
        dm.open();
        ResultSet rs = dm.selectAll();
        while (rs.next()) {
            if (rs.getString("zavaznostNehody") != null) {
                List<String> listRecord = new LinkedList<>();

                listRecord.add(getVal(rs.getLong("id")));
                listRecord.add(rs.getDate("datum").toLocalDate().format(df));
                listRecord.add(getVal(rs.getInt("hodina")));
                listRecord.add(String.valueOf(rs.getInt("kraj")));
                listRecord.add(getVal(rs.getString("okres")));
                listRecord.add(getVal(rs.getString("umisteniObec")));
                listRecord.add(getVal(rs.getString("druhNehody")));
                listRecord.add(getVal(rs.getString("zavineniNehody")));
                listRecord.add(getVal(rs.getString("alkohol")));
                listRecord.add(getVal(rs.getString("pricinaNehody")));
                listRecord.add(getVal(rs.getString("povrchVozovky")));
                listRecord.add(getVal(rs.getString("stavVozovky")));
                listRecord.add(getVal(rs.getString("povetrnostniPodminky")));
                listRecord.add(getVal(rs.getString("viditelnost")));
                listRecord.add(getVal(rs.getString("rozhled")));
                listRecord.add(getVal(rs.getString("deleniKomunikace")));
                listRecord.add(getVal(rs.getString("situovaniNehody")));
                listRecord.add(getVal(rs.getString("specifickeMisto")));
                listRecord.add(getVal(rs.getString("smerovyPomer")));
                listRecord.add(getVal(rs.getString("druhKomunikace")));
                listRecord.add(getVal(rs.getString("cisloKomunikace")));
                listRecord.add(getVal(rs.getString("typVozidla")));

                listRecord.add(getVal(rs.getLong("souradniceX")));
                listRecord.add(getVal(rs.getLong("souradniceY")));
                listRecord.add(getVal(rs.getString("obec")));
                listRecord.add(getVal(rs.getString("den")));
                listRecord.add(getVal(rs.getDouble("maxPrumTeplota")));
                listRecord.add(getVal(rs.getDouble("minPrumTeplota")));
                listRecord.add(getVal(rs.getDouble("prumSrazky")));
                listRecord.add(getVal(rs.getInt("hustotaProvozu")));
                listRecord.add(getVal(rs.getString("zavaznostNehody")));
                listRecord.add(getVal(rs.getString("typSrazkyVozidlo")));
                listRecord.add(getVal(rs.getString("typSrazkyPrekazka")));
                listRecord.add(getVal(rs.getString("druhPovrchu")));
                listRecord.add(getVal(rs.getInt("pocetVozidel")));

                csvData.add(listRecord.toArray(String[]::new));
            }
        }
        dm.close();

        writeData();

    }

    public String getVal(String value) {
        if (value == null) {
            return "null";
        }

        return value;
    }

    public String getVal(Long value) {
        if (value == null) {
            return "null";
        }

        return String.valueOf(value);
    }

    public String getVal(Double value) {
        if (value == null) {
            return "null";
        }

        return String.valueOf(value);
    }

    public String getVal(Integer value) {
        if (value == null) {
            return "null";
        }

        return String.valueOf(value);
    }

    /* public boolean checkDensity(Double d1,Double d2,double range)
    {
        
    }*/
    public void sortByDate(int index) throws IOException, CsvException {
        loadCsvData();

        csvData.sort(Comparator.comparing(a -> parseDate(a[index], true)));

        writeData();
    }

    public void sortByDay(int index) throws IOException, CsvException {
        loadCsvData();

        csvData.sort(Comparator.comparing(a -> a[5]));

        writeData();
    }

    public void removeDate(int index) throws IOException, CsvException {
        loadCsvData();

        columnsToRemove = new LinkedList<>();
        columnsToRemove.add(index);

        removeColumns();

        writeData();
    }

    public void removeMissingValues(int index) throws IOException, CsvException {
        loadCsvData();
        rowsToRemove = new LinkedList<>();
        for (int row = 1; row < csvData.size(); row++) {

            if (csvData.get(row).length < index + 1 || csvData.get(row)[index].length() == 0) {

                rowsToRemove.add(row);
            }

        }
        System.out.println(rowsToRemove.size());
        removeRows();
        writeData();
    }

    public void loadAllToDatabase() throws SQLException, ClassNotFoundException, IOException, CsvException {
        dm.open();
        int counter = 0;
        loadCsvData();
        String[] headerRow = csvData.get(0);
        String[] insertedRow;
        for (int row = 1; row < csvData.size(); row++) {

            try {
                dm.insertByName(headerRow, csvData.get(row));
            } catch (SQLServerException e) {
                counter++;
            }

        }
        System.out.println(counter);
        dm.close();

    }

    public void loadFromDatabase(List<String> headerRow, boolean required) throws SQLException, IOException, ClassNotFoundException, CsvException {
        dm.open();
        loadCsvData();
        csvData.addAll(dm.loadFromDatabase(headerRow, required));
        dm.close();
        writeData();

    }

    public void updateDatabaseColumnByID(String columnName) throws SQLException, ClassNotFoundException, IOException, CsvException {
        dm.open();
        loadCsvData();
        updateHeader();

       for(int row=1;row<csvData.size();row++)
       {
        dm.updateByID(getData(row,pm.getIndexes().getId()),columnName,getData(row,pm.getIndexes().getSpecificDirections()));

       }
        dm.close();

    }

    public void vypiss() throws SQLException, ClassNotFoundException {
        /* dm.open();
        dm.getRecords(3.7, -3.12, 0.31, -1164718, -755229, 10000);
        dm.close();*/
        dm.open();
        List<List<String[]>> l = dm.getDataToAccidentExecution(AccidentRecord.headerRow, 100);
        dm.close();

        for (int a = 0; a < l.size(); a++) {
            for (int c = 0; c < l.get(a).size(); c++) {
                for (int b = 0; b < l.get(a).get(c).length; b++) {
                    System.out.print(l.get(a).get(c)[b] + " ");
                }
                System.out.println();
            }
            System.out.println("");

        }

    }

    public void createDupliciteRecords(int result) throws IOException, CsvException {
        loadCsvData();
        List<Integer> rowsToAdd = new LinkedList<>();
        int o = 0;
        int z = 0;
        for (int row = 1; row < csvData.size(); row++) {
            if (getData(row, result).equals("0")) {
                z++;
            } else {
                rowsToAdd.add(row);
                o++;
            }
        }
        while (o < z) {
            for (int row = 0; (row < rowsToAdd.size() && o < z); row++) {
                csvData.add(csvData.get(rowsToAdd.get(row)));
                o++;
            }

        }
        writeData();
    }

    public void repairCoordinates() throws SQLException, ClassNotFoundException {
        dm.open();
        ResultSet rs = dm.selectAll("WHERE souradniceX>-0900000");

        while (rs.next()) {
            Double y = rs.getDouble("souradniceY");
            rs.updateDouble("souradniceY", rs.getDouble("souradniceX"));
            rs.updateDouble("souradniceX", y);

            rs.updateRow();
        }
        rs.close();
        dm.close();
    }

    public void addAltitude() throws IOException, CsvException, SQLException, ClassNotFoundException {
        loadCsvData();
        int altitudeIndex = -1;
        int wgsX = -1;
        int wgsY = -1;
        ResultSet rs;

        for (int column = 0; column < csvData.get(0).length; column++) {
            String[] header = csvData.get(0);
            if (header[column].equals("nadmorskaVyska")) {
                altitudeIndex = column;
            } else if (header[column].equals("wgsX")) {
                wgsX = column;
            } else if (header[column].equals("wgsY")) {
                wgsY = column;
            }
        }
        dm.open();
        for (int row = 1; row < csvData.size(); row++) {
            String[] rowToUpdate = csvData.get(row);
            rs = dm.getById(Long.parseLong(rowToUpdate[0]));

            if (rs.next()) {
                rs.updateDouble("nadmorskaVyska", Double.parseDouble(rowToUpdate[altitudeIndex]));
                rs.updateDouble("wgsX", Double.parseDouble(rowToUpdate[wgsX]));
                rs.updateDouble("wgsY", Double.parseDouble(rowToUpdate[wgsY]));
            }
            rs.updateRow();
            rs.close();
        }
        dm.close();

    }
}

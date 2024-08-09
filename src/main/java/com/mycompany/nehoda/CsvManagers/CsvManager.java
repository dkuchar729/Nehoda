/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.nehoda.CsvManagers;

import com.mycompany.nehoda.AccidentRecordsCreator;
import com.mycompany.nehoda.DatabaseManagers.AccidentDatabaseManager;
import com.mycompany.nehoda.DatabaseManagers.DatabaseManager;
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
import java.util.LinkedList;
import java.util.List;

/**
 * @author dorgu
 */
public class CsvManager {

    protected File csvFile;

    private CSVReader cr;
    private AccidentRecordManager sprava;
    private CSVWriter cw;
    protected List<String[]> csvData;
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

            LocalDate date = parseDate(getData(row, pm.getIndexes().getDate()), true);
            addValue(row, pm.getDayOfWeekValue(date.getDayOfWeek().getValue()));
            addWeatherData(row, date, WeatherInfo.JIHOCESKY, 2);
            addValue(row, getTrafficDensity(getData(row, pm.getIndexes().getRoadName()), 1, 5));

            //updateColumn(row, pm.getIndexes().getDate(), DateManager.getDefaultDateAsString(getData(row, pm.getIndexes().getDate())));
        }

        removeRows();
        removeColumns();

        updateTime();
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

            LocalDate date = parseDate(getData(row, pm.getIndexes().getDate()), false);
            addValue(row, pm.getDayOfWeekValue(date.getDayOfWeek().getValue()));
            addWeatherData(row, date, WeatherInfo.JIHOCESKY, 2);
            addValue(row, getTrafficDensity(getData(row, pm.getIndexes().getRoadName()), 1, 5));

            updateColumn(row, pm.getIndexes().getDate(), date.format(df));

        }

        removeRows();
        removeColumns();

        updateTime();
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

    public void updateHeader() throws IOException, CsvException {
        String[] header = csvData.get(0);

        for (int i = 0; i < header.length; i++) {
            if (!pm.setHeaderIndex(i, header[i])) {
                columnsToRemove.add(i);
            }
            updateColumn(0, i, pm.getHeaderValue(header[i]));
        }

        addValues(0, new String[]{"den", "maxPrumTeplota", "minPrumTeplota", "prumSrazky", "minPrumSrazky", "maxPrumSrazky", "hustotaProvozu", "typVozidla", "obec", "souradniceX", "souradniceY"});
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

        addValues(0, new String[]{"den", "maxPrumTeplota", "minPrumTeplota", "prumSrazky", "minPrumSrazky", "maxPrumSrazky", "hustotaProvozu"});
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

    /*public void createAccidentDayCountRecords(List<String[]> rows, int startIndex) throws IOException, CsvException {

        List<Integer> skipRow = new LinkedList<>();
        List<Integer> skipRow2 = new LinkedList<>();

        for (int i = 0; i < rows.size(); i++) {
            startIndex++;
            if (skipRow.contains(i)) {
                continue;
            }

            if (!skipRow2.contains(i)) {
                sprava.pridejDen(rows.get(i));
            }

            int counter = 1;
            for (int j = i + 1; j < rows.size(); j++) {

                if (skipRow.contains(j)) {
                    continue;
                }

                if (Nastaveni.porovnejDenDvaZaznam(rows.get(i), rows.get(j), sprava.lastNastaveni.getSurface(), sprava.lastNastaveni.getRoadState(), sprava.lastNastaveni.getVisibility())) {

                    sprava.porovnejDen(rows.get(j));
                    skipRow2.add(j);

                    if (Nastaveni.porovnejDenJednaZaznam(rows.get(i), rows.get(j), sprava.lastNastaveni.getAccidentType(), sprava.lastNastaveni.getRoadType())) {
                        skipRow.add(j);

                        counter++;
                    }
                }
            }

            updateColumn(startIndex, sprava.lastNastaveni.getResult(), String.valueOf(counter));

        }
    }

    public boolean compareDate(String[] row1, String[] row2, int dateIndex) {
        return LocalDate.parse(row1[dateIndex], df).isEqual(LocalDate.parse(row2[dateIndex], df));
    }

    public boolean compareRows(String[] firstRow, String[] secondRow, int druhNehody, int povrchKomunikace, int stavKomunikace, int viditelnost, int druhKomunikace) {
        return Nastaveni.compareRowsItem(firstRow, secondRow, druhNehody) && Nastaveni.compareRowsItem(firstRow, secondRow, povrchKomunikace)
                && Nastaveni.compareRowsItem(firstRow, secondRow, stavKomunikace) && Nastaveni.compareRowsItem(firstRow, secondRow, viditelnost)
                && Nastaveni.compareRowsItem(firstRow, secondRow, druhKomunikace);
    }
    
    public void pripravDny() throws IOException, CsvException {
        loadCsvData();

        List<String[]> rows = new LinkedList<>();
        String[] hlavniRow = csvData.get(1);
        rows.add(hlavniRow);

        for (int i = 2; i < csvData.size(); i++) {
            String[] row2 = csvData.get(i);
            if (compareDate(hlavniRow, row2, 0)) {
                rows.add(row2);

                if (i == csvData.size() - 1) {
                    createAccidentDayCountRecords(rows, i - rows.size());
                }
            } else {
                createAccidentDayCountRecords(rows, i - rows.size() - 1);
                hlavniRow = csvData.get(i);
                rows = new LinkedList<>();
                rows.add(hlavniRow);

            }
        }
        for (Nastaveni nastaveni : sprava.seznam) {

            for (String[] rowDay : nastaveni.vratDnyRadky(sprava.lastNastaveni.getAccidentType(), sprava.lastNastaveni.getRoadType(), sprava.lastNastaveni.getResult())) {

                csvData.add(rowDay);
            }
        }
        writeData();
    }
     */
}

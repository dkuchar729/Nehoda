/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.nehoda;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author dorgu
 */
public class CsvManager {

    private File csvFile;
    private CSVReader cr;
    private Sprava sprava;
    private CSVWriter cw;
    private List<String[]> csvData;
    private char separator;
    private final DateTimeFormatter df = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private final ParameterManager pm = new ParameterManager();
    private LocalDate lastDate;
    private boolean updated = false;

    private String[] lastWeatherRow;

    public CsvManager(File csvFile, char separator) throws FileNotFoundException, IOException {
        this.csvFile = csvFile;
        this.separator = separator;
        lastDate = LocalDate.MIN;
        sprava = new Sprava();

    }

    public void updateCsvFile() throws IOException, CsvException {
        loadCsvData();
        List<Integer> rowsToRemove = new LinkedList<>();
        updateHeader();
        for (int row = 1; row < csvData.size(); row++) {

            if (!updateHours(row, ColumnIndex.HOUR.getColumnIndex())) {

                rowsToRemove.add(row);
                continue;
            }

            updateColumn(row, ColumnIndex.TOWN.getColumnIndex(), pm.getTownValue(getData(row, ColumnIndex.TOWN.getColumnIndex())));
            updateColumn(row, ColumnIndex.ACCIDENT_TYPE.getColumnIndex(), pm.getAccidentTypeValue(getData(row, ColumnIndex.ACCIDENT_TYPE.getColumnIndex())));
            updateColumn(row, ColumnIndex.GUILTY.getColumnIndex(), pm.getGuiltyValue(getData(row, ColumnIndex.GUILTY.getColumnIndex())));
            updateColumn(row, ColumnIndex.ALCOHOL.getColumnIndex(), pm.getAlcoholValue(getData(row, ColumnIndex.ALCOHOL.getColumnIndex())));
            updateColumn(row, ColumnIndex.CAUSE.getColumnIndex(), pm.getCauseValue(getData(row, ColumnIndex.CAUSE.getColumnIndex())));
            updateColumn(row, ColumnIndex.SURFACE.getColumnIndex(), pm.getSurfaceValue(getData(row, ColumnIndex.SURFACE.getColumnIndex())));
            updateColumn(row, ColumnIndex.WEATHER_CONDITIONS.getColumnIndex(), pm.getWeatherValue(getData(row, ColumnIndex.WEATHER_CONDITIONS.getColumnIndex())));
            updateColumn(row, ColumnIndex.VISIBILITY.getColumnIndex(), pm.getVisibilityValue(getData(row, ColumnIndex.VISIBILITY.getColumnIndex())));
            updateColumn(row, ColumnIndex.ROAD_LINE.getColumnIndex(), pm.getRoadLineValue(getData(row, ColumnIndex.ROAD_LINE.getColumnIndex())));
            updateColumn(row, ColumnIndex.ROAD_TYPE.getColumnIndex(), pm.getRoadTypeValue(getData(row, ColumnIndex.ROAD_TYPE.getColumnIndex())));
            updateColumn(row, ColumnIndex.DIRECTION.getColumnIndex(), pm.getDirectionValue(getData(row, ColumnIndex.DIRECTION.getColumnIndex())));
            updateColumn(row, ColumnIndex.ROAD_STATE.getColumnIndex(), pm.getRoadState(getData(row, ColumnIndex.ROAD_STATE.getColumnIndex())));
            updateColumn(row, ColumnIndex.VISION.getColumnIndex(), pm.getVision(getData(row, ColumnIndex.VISION.getColumnIndex())));
            updateColumn(row, ColumnIndex.ROAD_AREA.getColumnIndex(), pm.getRoadArea(getData(row, ColumnIndex.ROAD_AREA.getColumnIndex())));
            updateColumn(row, ColumnIndex.SPECIFIC_PLACE.getColumnIndex(), pm.getSpecificPlace(getData(row, ColumnIndex.SPECIFIC_PLACE.getColumnIndex())));

            LocalDate date = parseDate(getData(row, ColumnIndex.DATE.getColumnIndex()));
            addValue(row, pm.getDayOfWeekValue(date.getDayOfWeek().getValue()));
            addWeatherData(row, date, WeatherInfo.JIHOCESKY, 2);
            addValue(row, getTrafficDensity(getData(row, ColumnIndex.ROAD_NAME.getColumnIndex()), 1, 5));
        }


for(int i=rowsToRemove.size()-1;i>-1;i--)
{

    csvData.remove((int)rowsToRemove.get(i));


}
        writeData();
    }

    public void pripravDny() throws IOException, CsvException {
        loadCsvData();

        List<String[]> rows = new LinkedList<>();
        String[] hlavniRow = csvData.get(1);
        rows.add(hlavniRow);

        for (int i = 2; i < csvData.size(); i++) {
            String[] row2 = csvData.get(i);
            if (porovnejDatum(hlavniRow, row2, 0)) {
                rows.add(row2);

                if (i == csvData.size() - 1) {
                    projdiDnyRadky(rows, i - rows.size());
                }
            } else {
                projdiDnyRadky(rows, i - rows.size() - 1);
                hlavniRow = csvData.get(i);
                rows = new LinkedList<>();
                rows.add(hlavniRow);


            }
        }
        for (Nastaveni nastaveni : sprava.seznam) {

            for (String[] rowDay : nastaveni.vratDnyRadky(1, 5, 10)) {

                csvData.add(rowDay);
            }
        }
        writeData();
    }

    public LocalDate getDate(String date) {
        return LocalDate.parse(date, df);
    }

    public void projdiDnyRadky(List<String[]> rows, int startIndex) throws IOException, CsvException {

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

                if (Nastaveni.porovnejDenDvaZaznam(rows.get(i), rows.get(j), 2, 3, 4)) {

                    sprava.porovnejDen(rows.get(j));
                    skipRow2.add(j);
                   
                    if (Nastaveni.porovnejDenJednaZaznam(rows.get(i), rows.get(j), 1, 5)) {
                        skipRow.add(j);

                        counter++;
                    }
                }

            }

            updateColumn(startIndex, 10, String.valueOf(counter));

        }
    }

    public boolean porovnejDatum(String[] row1, String[] row2, int dateIndex) {
        return LocalDate.parse(row1[dateIndex], df).isEqual(LocalDate.parse(row2[dateIndex], df));
    }

    public boolean porovnej(String[] row1, String[] row2, int druhNehody, int povrchKomunikace, int stavKomunikace, int viditelnost, int druhKomunikace) {
        return Nastaveni.porovnejRadku(row1, row2, druhNehody) && Nastaveni.porovnejRadku(row1, row2, povrchKomunikace)
                && Nastaveni.porovnejRadku(row1, row2, stavKomunikace) && Nastaveni.porovnejRadku(row1, row2, viditelnost)
                && Nastaveni.porovnejRadku(row1, row2, druhKomunikace);
    }


    public void nastavCas(int index) throws IOException, CsvException {

        for (int i = 1; i < csvData.size(); i++) {
            updateColumn(i, index, vratCas(getData(i, index)));
        }

    }

    public String vratCas(String value) {
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

    public void prohledejData() throws IOException, CsvException {

        loadCsvData();
        Sprava sprava = new Sprava();
        List<Integer> skipRow = new LinkedList<>();
        String[] actualRow;
//nastavCas(0);up
        for (int row = 1; row < csvData.size(); row++) {

            if (skipRow.contains(row)) {
                continue;
            }

            addValue(row, "1");
            actualRow = csvData.get(row);
            sprava.pridej(actualRow);


            for (int i = row + 1; i < csvData.size(); i++) {
                if (skipRow.contains(i)) {
                    continue;
                }

                if (sprava.porovnej(csvData.get(i))) {
                    addValue(i, "1");
                    skipRow.add(i);
                }
            }
        }

        for (Nastaveni nastaveni : sprava.seznam) {
            for (String[] qwe : nastaveni.vratRadky()) {
                csvData.add(qwe);
            }
        }

        writeData();
    }

    public void updateHeader() throws IOException, CsvException {
        String[] header = csvData.get(0);

        for (int i = 0; i < header.length; i++) {
            updateColumn(0, i, pm.getHeaderValue(header[i]));
        }

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
        loadCsvData();
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
                lastDate = parseDate(weatherRow[weatherInfo.getWeatherDateColumnIndex()]);
                addValues(row, lastWeatherRow);
            }

        } else {
            addValues(row, lastWeatherRow);
        }

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

    private LocalDate parseDate(String date) {
        return LocalDate.parse(date, df);
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

}

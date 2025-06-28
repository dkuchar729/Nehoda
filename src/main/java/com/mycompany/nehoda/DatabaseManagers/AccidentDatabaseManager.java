/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.nehoda.DatabaseManagers;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.mycompany.nehoda.AccidentRecord;
import com.mycompany.nehoda.DateManager;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author dorgu
 */
public class AccidentDatabaseManager extends DatabaseManager {

    private List<String[]> data;

    public ResultSet selectAll() throws SQLException {
        String command = "SELECT * FROM udajeNehody";
        Statement stm = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);

        return stm.executeQuery(command);
    }

    public ResultSet selectAll(String table, int i) throws SQLException {
        String command = "SELECT * FROM " + table;
        Statement stm = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);

        return stm.executeQuery(command);
    }

    public void insert( String value) throws SQLException {

        ResultSet rs = selectAll("stanice", 1);
        boolean exist = false;
        while (rs.next()) {
            if (rs.getString("nazev").toLowerCase().trim().equals(value)) {
                exist = true;
                break;
            }

        }

        if (!exist) {
            rs.moveToInsertRow();
            rs.updateString("nazev", value);
            rs.insertRow();
        }
        rs.close();
    }

    public ResultSet selectAll(String condition) throws SQLException {
        String command = "SELECT * FROM udajeNehody " + condition;
        Statement stm = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);

        return stm.executeQuery(command);
    }

    public void insert(String[] values) throws SQLException {
        ResultSet rs = selectAll();

        rs.moveToInsertRow();

        rs.updateLong("id", Long.parseLong(values[0]));
        rs.updateDate("datum", Date.valueOf(DateManager.getBaseDate(values[1])));
        rs.updateInt("hodina", Integer.parseInt(values[2]));
        rs.updateInt("kraj", Integer.parseInt(values[3]));
        rs.updateString("okres", values[4]);
        rs.updateString("lokace", values[5]);
        rs.updateString("druhNehody", values[6]);
        rs.updateString("vinikNehody", values[7]);
        rs.updateString("alkohol", values[8]);
        rs.updateString("pricinaNehody", values[9]);
        rs.updateString("povrchVozovky", values[10]);
        rs.updateString("stavVozovky", values[11]);
        rs.updateString("povetrnostniPodminky", values[12]);

        rs.updateString("viditelnost", values[13]);
        rs.updateString("rozhled", values[14]);
        rs.updateString("deleniKomunikace", values[15]);
        rs.updateString("situovaniNehody", values[16]);
        rs.updateString("specifikaceMista", values[17]);
        rs.updateString("smerovyPomer", values[18]);
        rs.updateString("druhKomunikace", values[19]);

        if (values[20].length() != 0) {
            rs.updateString("cisloKomunikace", values[20]);
        }

        rs.updateString("den", values[21]);
        rs.updateFloat("maxPrumTeplota", Float.parseFloat(values[22]));
        rs.updateFloat("minPrumTeplota", Float.parseFloat(values[23]));
        rs.updateFloat("prumSrazky", Float.parseFloat(values[24]));
        rs.updateFloat("minPrumSrazky", Float.parseFloat(values[25]));
        rs.updateFloat("maxPrumSrazky", Float.parseFloat(values[26]));

        if (!values[27].equals("null")) {

            rs.updateInt("hustotaProvozu", Integer.parseInt(values[27]));
        }

        rs.insertRow();

        rs.close();
    }

    public void insertByName(String[] headerRow, String[] insertedRow) throws SQLException {
        ResultSet rs = selectAll();

        rs.moveToInsertRow();
        for (int column = 0; column < headerRow.length; column++) {
            if (!isNull(insertedRow[column])) {

                switch (headerRow[column]) {
                    case "id":
                        rs.updateLong(headerRow[column], Long.parseLong(insertedRow[column]));
                        break;

                    case "datum":

                        rs.updateDate(headerRow[column], Date.valueOf(DateManager.getBaseDate(insertedRow[column])));
                        break;

                    case "hodina", "hustotaProvozu":
                        rs.updateInt(headerRow[column], Integer.parseInt(insertedRow[column]));
                        break;

                    case "kraj":
                        rs.updateInt(headerRow[column], 2);
                        break;
                    case "maxPrumTeplota", "minPrumTeplota", "PrumSrazky", "MaxPrumSrazky", "MinPrumSrazky",
                         "souradniceX", "souradniceY":

                        rs.updateDouble(headerRow[column], Double.parseDouble(insertedRow[column]));
                        break;

                    default:
                        rs.updateString(headerRow[column], insertedRow[column]);
                        break;
                }

            }

        }

        rs.insertRow();

        rs.close();
    }

    public boolean contain(int id) throws SQLException {
        String command = "SELECT id FROM udajeNehody WHERE id=" + id;
        Statement stm = connection.createStatement();

        ResultSet rs = stm.executeQuery(command);

        return rs.next();

    }

    public ResultSet getById(long id) throws SQLException {
        String command = "SELECT * FROM udajeNehody WHERE id=" + id;
        Statement stm = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);

        return stm.executeQuery(command);

    }

    public void loadAccidentSeverity(long id, String value) throws SQLException {

        ResultSet rs = getById(id);
        if (rs.next()) {
            rs.updateString("zavaznostNehody", value);
            rs.updateRow();
        }
        rs.close();
    }

    public void loadStringToDatabaseById(long id, String column, String value) throws SQLException {

        ResultSet rs = getById(id);
        if (rs.next()) {
            rs.updateString(column, value);
            rs.updateRow();
        }
        rs.close();
    }

    public void loadIntToDatabaseById(long id, String column, int value) throws SQLException {

        ResultSet rs = getById(id);
        if (rs.next()) {
            rs.updateInt(column, value);
            rs.updateRow();
        }
        rs.close();
    }

    public boolean isNull(String value) {
        return value.equals("null") || value.length() == 0;
    }

    public ResultSet getRecords(double maxTemp, double minTemp, double prec, double cX, double cY, int range) throws SQLException {

        String command = "SELECT * FROM udajeNehody WHERE ((";

        String temp = "NOT((maxPrumTeplota>0.4 AND " + maxTemp + "<=0.4) OR (minPrumTeplota>0.4 AND " + minTemp + "<=0.4 )))"
                + "AND (maxPrumTeplota>=(" + (maxTemp - 5) + ") AND maxPrumTeplota<=" + maxTemp + ") AND "
                + "(minPrumTeplota>=(" + (minTemp - 5) + ")  AND minPrumTeplota<=" + minTemp + ") AND ";

        String precString = "((prumSrazky=0 AND " + prec + "=0) OR (prumSrazky>0 AND prumSrazky<=0.3 AND " + prec + ">0 AND " + prec + "<=0.3 )"
                + "OR (prumSrazky>0.3 AND prumSrazky<=0.6 AND " + prec + ">0.3 AND " + prec + "<=0.6)"
                + "OR (prumSrazky>0.6 AND prumSrazky<=0.9 AND " + prec + ">0.6 AND " + prec + "<=0.9)"
                + "OR (prumSrazky>0.9 AND prumSrazky<=1.5 AND " + prec + ">0.9 AND " + prec + "<=1.5)"
                + "OR (prumSrazky>1.5 AND prumSrazky<=2.9 AND " + prec + ">1.5 AND " + prec + "<=2.9)"
                + "OR (prumSrazky>2.9 AND prumSrazky<=4.9 AND " + prec + ">2.9 AND " + prec + "<=4.9)"
                + "OR (prumSrazky>4.9 AND " + prec + ">4.9 AND " + prec + "<=prumSrazky+5)"
                + ")) ";

        String coordinates = "AND (souradniceX<(" + (cX + range) + ") AND souradniceX>(" + (cX - range) + ") AND "
                + "souradniceY<(" + (cY + range) + ") AND souradniceY>(" + (cY - range) + "))";

        command += temp + precString + coordinates;

        Statement stm = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);

        return stm.executeQuery(command);

    }

    public ResultSet getRecords(double maxTemp, double minTemp, double prec, double cX, double cY, double altitude, int coordinatesRange, int altitudeRange) throws SQLException {

        String command = "SELECT * FROM udajeNehody WHERE ((";

        String temp = "NOT((maxPrumTeplota>0.4 AND " + maxTemp + "<=0.4) OR (minPrumTeplota>0.4 AND " + minTemp + "<=0.4 )))"
                + "AND (maxPrumTeplota>=(" + (maxTemp - 5) + ") AND maxPrumTeplota<=" + maxTemp + ") AND "
                + "(minPrumTeplota>=(" + (minTemp - 5) + ")  AND minPrumTeplota<=" + minTemp + ") AND ";

        String precString = "((prumSrazky=0 AND " + prec + "=0) OR (prumSrazky>0 AND prumSrazky<=0.3 AND " + prec + ">0 AND " + prec + "<=0.3 )"
                + "OR (prumSrazky>0.3 AND prumSrazky<=0.6 AND " + prec + ">0.3 AND " + prec + "<=0.6)"
                + "OR (prumSrazky>0.6 AND prumSrazky<=0.9 AND " + prec + ">0.6 AND " + prec + "<=0.9)"
                + "OR (prumSrazky>0.9 AND prumSrazky<=1.5 AND " + prec + ">0.9 AND " + prec + "<=1.5)"
                + "OR (prumSrazky>1.5 AND prumSrazky<=2.9 AND " + prec + ">1.5 AND " + prec + "<=2.9)"
                + "OR (prumSrazky>2.9 AND prumSrazky<=4.9 AND " + prec + ">2.9 AND " + prec + "<=4.9)"
                + "OR (prumSrazky>4.9 AND " + prec + ">4.9 AND " + prec + "<=prumSrazky+5)"
                + ")) ";

        String coordinates = "AND (souradniceX<(" + (cX + coordinatesRange) + ") AND souradniceX>(" + (cX - coordinatesRange) + ") AND "
                + "souradniceY<(" + (cY + coordinatesRange) + ") AND souradniceY>(" + (cY - coordinatesRange) + ")) ";

        String altitudeCommand = "AND (SQRT(SQUARE(nadmorskaVyska-" + altitude + "))<=" + altitudeRange + ")";

        command += temp + precString + coordinates + altitudeCommand;

        Statement stm = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);

        return stm.executeQuery(command);

    }

    public ResultSet getRecords(double maxTemp, double minTemp, double prec, double altitude, int altitudeRange) throws SQLException {

        String command = "SELECT * FROM udajeNehody WHERE ((";

        String temp = "NOT((maxPrumTeplota>0.4 AND " + maxTemp + "<=0.4) OR (minPrumTeplota>0.4 AND " + minTemp + "<=0.4 )))"
                + "AND (maxPrumTeplota>=(" + (maxTemp - 5) + ") AND maxPrumTeplota<=" + maxTemp + ") AND "
                + "(minPrumTeplota>=(" + (minTemp - 5) + ")  AND minPrumTeplota<=" + minTemp + ") AND ";

        String precString = "((prumSrazky=0 AND " + prec + "=0) OR (prumSrazky>0 AND prumSrazky<=0.3 AND " + prec + ">0 AND " + prec + "<=0.3 )"
                + "OR (prumSrazky>0.3 AND prumSrazky<=0.6 AND " + prec + ">0.3 AND " + prec + "<=0.6)"
                + "OR (prumSrazky>0.6 AND prumSrazky<=0.9 AND " + prec + ">0.6 AND " + prec + "<=0.9)"
                + "OR (prumSrazky>0.9 AND prumSrazky<=1.5 AND " + prec + ">0.9 AND " + prec + "<=1.5)"
                + "OR (prumSrazky>1.5 AND prumSrazky<=2.9 AND " + prec + ">1.5 AND " + prec + "<=2.9)"
                + "OR (prumSrazky>2.9 AND prumSrazky<=4.9 AND " + prec + ">2.9 AND " + prec + "<=4.9)"
                + "OR (prumSrazky>4.9 AND " + prec + ">4.9 AND " + prec + "<=prumSrazky+5)"
                + ")) ";

        String altitudeCommand = "AND (SQRT(SQUARE(nadmorskaVyska-" + altitude + "))<=" + altitudeRange + ")";

        command += temp + precString + altitudeCommand;

        Statement stm = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);

        return stm.executeQuery(command);

    }

    public List<String[]> loadFromDatabase(List<String> headerRow, boolean required) throws SQLException {
        List<String[]> data = new LinkedList<>();

        ResultSet rs = selectAll();

        while (rs.next()) {
            String[] row = fillRow(headerRow, required, rs);

            if (row != null) {
                data.add(row);
            }
        }

        return data;
    }

    public String[] fillRow(List<String> headerRow, boolean required, ResultSet rs) throws SQLException {
        String value;
        String[] row = new String[headerRow.size()];

        for (int column = 0; column < headerRow.size(); column++) {
            value = getColumnValue(headerRow.get(column), rs);

            if (value != null) {
                if (!value.equals("454")) {
                    row[column] = value;
                } else {
                    return null;
                }
            } else {
                if (required) {
                    return null;
                }
                row[column] = "null";
            }

        }
        return row;
    }

    public boolean checkCoordinates(double x, double y) {
        return x != 0 && x != 0;
    }

    private String getColumnValue(String headerValue, ResultSet rs) throws SQLException {

        switch (headerValue) {
            case "id":
                return String.valueOf(rs.getLong(headerValue));

            case "datum":
                return rs.getDate(headerValue).toLocalDate().format(DateManager.baseDf);

            case "hodina", "hustotaProvozu":
                return String.valueOf(rs.getInt(headerValue));

            case "kraj":
                return String.valueOf(rs.getInt(headerValue));

            case "maxPrumTeplota", "minPrumTeplota", "PrumSrazky", "MaxPrumSrazky", "MinPrumSrazky":
                return String.valueOf(rs.getDouble(headerValue));

            case "souradniceX", "souradniceY", "nadmorskaVyska", "wgsX", "wgsY":
                String value = String.valueOf(rs.getDouble(headerValue));
                if (value.equals("0.0")) {
                    return "454";
                }
                return value;

            default:
                return rs.getString(headerValue);

        }
    }

    public List<List<String[]>> getDataToAccidentExecution(List<String> headerRow, int range) throws SQLException {
        List<List<String[]>> data = new LinkedList<>();
        List<Long> idList = new LinkedList<>();
        ResultSet rsAll = selectAll("ORDER BY maxPrumTeplota DESC");
        int counter = 0;

        while (rsAll.next()) {//&& counter < 170
            //counter++;
            if (!idList.contains(rsAll.getLong("id"))) {

                if (checkCoordinates(rsAll.getDouble("souradniceX"), rsAll.getDouble("souradniceY"))) {
                    ResultSet rsAccident = getRecords(rsAll.getDouble("maxPrumTeplota"), rsAll.getDouble("minPrumTeplota"), rsAll.getDouble("prumSrazky"),
                            rsAll.getDouble("souradniceX"), rsAll.getDouble("souradniceY"), range);

                    List<String[]> localData = new LinkedList<>();
                    String[] row;
                    while (rsAccident.next()) {

                        if (!idList.contains(rsAccident.getLong("id"))) {
                            //System.out.println(idList.contains(rsAccident.getLong("id")));
                            idList.add(rsAccident.getLong("id"));
                            //System.out.println(rsAccident.getLong("id") + " " + rsAll.getInt("hodina") + " " + rsAccident.getInt("hodina"));
                            //System.out.println(idList.contains(rsAccident.getLong("id")));

                            row = fillRow(headerRow, true, rsAccident);

                            if (row != null) {

                                localData.add(row);

                            }
                        }
                    }

                    if (!localData.isEmpty()) {
                        data.add(localData);

                    }
                }

            }
        }
        /* for (int a = 0; a < data.size(); a++) {
            for (int b = 0; b < data.get(a).size(); b++) {
               
                 if (data.get(a).get(b)[22].equals("20106220806")) {
                        System.out.println("data " +a+" "+data.get(a).size());
                        System.out.println("tadyyyy 20106220806" + " " + a);
                    }
               
            }
        }*/

 /*  for (int b = 0; b < data.get(70).size(); b++) {

            if (data.get(70).get(b)[22].equals("20106220757")) {
                System.out.println("data 70 " + data.get(70).size());
                System.out.println("tadyyyy 20106220757" + " " + 70);
            }

        }
        System.out.println();
        for (int b = 0; b < data.get(94).size(); b++) {

            if (data.get(94).get(b)[22].equals("20106220757")) {
                System.out.println("data 94 " + data.get(94).size());
                System.out.println("tadyyyy 20106220757" + " " + 94);
            }

        }*/
        return data;
    }

    public List<List<String[]>> getDataToAccidentExecutionn(List<String> headerRow, int range) throws SQLException {
        List<List<String[]>> data = new LinkedList<>();
        List<Long> idList = new LinkedList<>();
        ResultSet rsAll = selectAll("ORDER BY maxPrumTeplota DESC");
        int counter = 0;

        while (rsAll.next()) {//&& counter < 170
            //counter++;
            if (!idList.contains(rsAll.getLong("id"))) {

                if (checkCoordinates(rsAll.getDouble("souradniceX"), rsAll.getDouble("souradniceY"))) {
                    ResultSet rsAccident = getRecords(rsAll.getDouble("maxPrumTeplota"), rsAll.getDouble("minPrumTeplota"), rsAll.getDouble("prumSrazky"),
                            rsAll.getDouble("souradniceX"), rsAll.getDouble("souradniceY"), range);

                    List<String[]> localData = new LinkedList<>();
                    String[] row;
                    while (rsAccident.next()) {

                        if (!idList.contains(rsAccident.getLong("id"))) {
                            //System.out.println(idList.contains(rsAccident.getLong("id")));
                            idList.add(rsAccident.getLong("id"));
                            //System.out.println(rsAccident.getLong("id") + " " + rsAll.getInt("hodina") + " " + rsAccident.getInt("hodina"));
                            //System.out.println(idList.contains(rsAccident.getLong("id")));

                            row = fillRow(headerRow, true, rsAccident);

                            if (row != null) {

                                row[headerRow.indexOf("maxPrumTeplota")] = String.valueOf(rsAll.getDouble("maxPrumTeplota"));
                                row[headerRow.indexOf("minPrumTeplota")] = String.valueOf(rsAll.getDouble("minPrumTeplota"));
                                row[headerRow.indexOf("prumSrazky")] = String.valueOf(rsAll.getDouble("prumSrazky"));
                                row[headerRow.indexOf("souradniceX")] = String.valueOf(rsAll.getDouble("souradniceX"));
                                row[headerRow.indexOf("souradniceY")] = String.valueOf(rsAll.getDouble("souradniceY"));
                                localData.add(row);
                                //263150
                                //195

                            }
                        }
                    }

                    if (!localData.isEmpty()) {
                        data.add(localData);

                    }
                }

            }
        }
        /* for (int a = 0; a < data.size(); a++) {
            for (int b = 0; b < data.get(a).size(); b++) {
               
                 if (data.get(a).get(b)[22].equals("20106220806")) {
                        System.out.println("data " +a+" "+data.get(a).size());
                        System.out.println("tadyyyy 20106220806" + " " + a);
                    }
               
            }
        }*/

 /*  for (int b = 0; b < data.get(70).size(); b++) {

            if (data.get(70).get(b)[22].equals("20106220757")) {
                System.out.println("data 70 " + data.get(70).size());
                System.out.println("tadyyyy 20106220757" + " " + 70);
            }

        }
        System.out.println();
        for (int b = 0; b < data.get(94).size(); b++) {

            if (data.get(94).get(b)[22].equals("20106220757")) {
                System.out.println("data 94 " + data.get(94).size());
                System.out.println("tadyyyy 20106220757" + " " + 94);
            }

        }*/
        return data;
    }

    public List<List<String[]>> getDataToAccidentExecutionAltitude(List<String> headerRow, int altitudeRange) throws SQLException {
        List<List<String[]>> data = new LinkedList<>();
        List<Long> idList = new LinkedList<>();
        ResultSet rsAll = selectAll("WHERE nadmorskaVyska IS NOT NULL ORDER BY maxPrumTeplota DESC");
        int counter = 0;

        while (rsAll.next()) {//&& counter < 170
            counter++;
            if (!idList.contains(rsAll.getLong("id"))) {

                ResultSet rsAccident = getRecords(rsAll.getDouble("maxPrumTeplota"), rsAll.getDouble("minPrumTeplota"), rsAll.getDouble("prumSrazky"), rsAll.getDouble("nadmorskaVyska"), altitudeRange);

                List<String[]> localData = new LinkedList<>();
                String[] row;
                while (rsAccident.next()) {

                    if (!idList.contains(rsAccident.getLong("id"))) {
                        //System.out.println(idList.contains(rsAccident.getLong("id")));
                        idList.add(rsAccident.getLong("id"));
                        //System.out.println(rsAccident.getLong("id") + " " + rsAll.getInt("hodina") + " " + rsAccident.getInt("hodina"));
                        //System.out.println(idList.contains(rsAccident.getLong("id")));

                        row = fillRow(headerRow, true, rsAccident);

                        if (row != null) {

                            row[headerRow.indexOf("maxPrumTeplota")] = String.valueOf(rsAll.getDouble("maxPrumTeplota"));
                            row[headerRow.indexOf("minPrumTeplota")] = String.valueOf(rsAll.getDouble("minPrumTeplota"));
                            row[headerRow.indexOf("prumSrazky")] = String.valueOf(rsAll.getDouble("prumSrazky"));
                            row[headerRow.indexOf("nadmorskaVyska")] = String.valueOf(rsAll.getDouble("nadmorskaVyska"));
                            localData.add(row);

                        }
                    }
                }

                if (!localData.isEmpty()) {
                    data.add(localData);

                }

            }
        }
        /* for (int a = 0; a < data.size(); a++) {
            for (int b = 0; b < data.get(a).size(); b++) {
                System.out.println(a);
                System.out.println(data.get(a).get(b)[17]);
                System.out.println(data.get(a).get(b)[18]);
               System.out.println(data.get(a).get(b)[22]);
                 System.out.println(data.get(a).get(b)[19]);
                 System.out.println(data.get(a).get(b)[20]);
                 System.out.println(data.get(a).get(b)[21]);
                 /*if (data.get(a).get(b)[22].equals("20106220806")) {
                        System.out.println("data " +a+" "+data.get(a).size());
                        System.out.println("tadyyyy 20106220806" + " " + a);
                    }*/

        //}
        // System.out.println();
        //}

        /*  for (int b = 0; b < data.get(70).size(); b++) {

            if (data.get(70).get(b)[22].equals("20106220757")) {
                System.out.println("data 70 " + data.get(70).size());
                System.out.println("tadyyyy 20106220757" + " " + 70);
            }

        }
        System.out.println();
        for (int b = 0; b < data.get(94).size(); b++) {

            if (data.get(94).get(b)[22].equals("20106220757")) {
                System.out.println("data 94 " + data.get(94).size());
                System.out.println("tadyyyy 20106220757" + " " + 94);
            }

        }*/
        return data;
    }

    public void loadAllToDatabase(Map<String, Integer> headerMap, List<String[]> csvData) throws SQLException {
        AccidentRecord.updateHeader(csvData.get(0));
        ResultSet rs = selectAll();
        for (int row = 1; row < csvData.size(); row++) {

            String[] actualRow = csvData.get(row);
            for (int column = 0; column < actualRow.length; column++) {
                addValueToDatabase(actualRow[column], AccidentRecord.fileHeaderRow.get(column), rs);
            }
        }
        rs.close();
    }

    public void updateByID(String ID, String columnName, String value) throws SQLException {
        ResultSet rs = getById(Long.parseLong(ID));
        if (rs.next()) {
            rs.updateString(columnName, value);
            rs.updateRow();
        }
        rs.close();


    }

    public void addValueToDatabase(String value, String headerColumnName, ResultSet rs) throws SQLException {

        if (value != null && value.length() != 0) {
            rs.moveToInsertRow();
            switch (headerColumnName) {
                case "id":
                    rs.updateLong(headerColumnName, Long.parseLong(value));
                    break;
                case "datum":
                    rs.updateDate(headerColumnName, Date.valueOf(DateManager.getDefaultDate(value)));
                    break;
                case "kraj", "hodina", "hustotaProvozu", "usmrcenoOsob", "tezceZranenoOsob", "lehceZranenoOsob",
                     "celkovaCastka":
                    rs.updateInt(headerColumnName, Integer.parseInt(value));
                    break;

                case "souradniceX", "souradniceY", "nadmorskaVyska", "wgsX", "wgsY", "maxPrumTeplota", "minPrumTeplota",
                     "PrumSrazky", "MaxPrumSrazky", "MinPrumSrazky":
                    rs.updateDouble(headerColumnName, Double.parseDouble(value));
                    break;

                default:
                    rs.updateString(headerColumnName, value);

            }
            rs.updateRow();
        }
    }

    public ResultSet insertt() throws SQLException {
       return selectAll("pocasi",1);
    }
}

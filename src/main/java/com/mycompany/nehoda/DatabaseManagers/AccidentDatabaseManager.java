/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.nehoda.DatabaseManagers;

import com.mycompany.nehoda.DateManager;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author dorgu
 */
public class AccidentDatabaseManager extends DatabaseManager {

    public ResultSet selectAll() throws SQLException {
        String command = "SELECT * FROM udajeNehody";
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

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.nehoda.DatabaseManagers;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author dorgu
 */
public class VehicleDatabaseManager extends DatabaseManager{
    
    public ResultSet selectAll() throws SQLException
    {
      return  connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE).executeQuery("SELECT * FROM vozidla");
    }
    
    public void insert(ResultSet rs,String [] values) throws SQLException
    {
        rs.moveToInsertRow();
        rs.updateLong("id", Long.parseLong(values[0]));
        rs.updateString("typVozidla", values[2]);
        rs.insertRow();
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.nehoda;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author dorgu
 */
public class DatabaseManager {
    Connection connection;
    
    public void connect() throws SQLException
    {
        String c=
                "jdbc:sqlserver://P-PC.database.windows.net:1433;"
                        + "database=nehody;"
                        + "user=;"
                        + "password=;"
                        + "encrypt=true;"
                        + "trustServerCertificate=false;"
                        + "loginTimeout=30;";
        try (Connection connection = DriverManager.getConnection(c);) {
         System.out.println("pripojeno");
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

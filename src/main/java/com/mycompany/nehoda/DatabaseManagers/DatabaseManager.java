/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.nehoda.DatabaseManagers;

import com.mycompany.nehoda.DateManager;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author dorgu
 */
public class DatabaseManager {

    private String connectionString = "jdbc:sqlserver://P-PC;"
            + "database=nehody;"
            + "integratedSecurity=true;"
            + "encrypt=true;"
            + "trustServerCertificate=true;"
            + "loginTimeout=30;";
    protected Connection connection;

    public void close() throws SQLException {
        connection.close();
    }

    public void open() throws SQLException, ClassNotFoundException {
        connection = DriverManager.getConnection(connectionString);
    }

}

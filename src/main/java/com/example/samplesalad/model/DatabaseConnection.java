package com.example.samplesalad.model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DatabaseConnection {
    private static Connection instance = null;

    private DatabaseConnection() {
        String url = "jdbc:mysql://127.0.0.1:3306/SampleSaladDB";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            instance = DriverManager.getConnection(url, "SampleSaladConnector", "SampleSaladPass");
        } catch (Exception e)
            { System.out.println(e);}
    }

    public static Connection getInstance() {
        if (instance == null) {
            new DatabaseConnection();
        }
        return instance;
    }
}
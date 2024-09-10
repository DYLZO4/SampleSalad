package com.example.samplesalad.model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MockDatabaseConnection {
    private static Connection instance = null;

    private MockDatabaseConnection() {
        String url = "jdbc:sqlite:SampleSalad.db";
        try {
            instance = DriverManager.getConnection(url);
        } catch (SQLException sqlEx) {
            System.err.println(sqlEx);
        }
    }

    public static Connection getInstance() {
        if (instance == null) {
            new MockDatabaseConnection();
        }
        return instance;
    }
}

package com.example.samplesalad.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The {@code MockDatabaseConnection} class provides a singleton pattern implementation
 * for establishing a connection to a SQLite database.
 * <p>
 * This class ensures that only one instance of the database connection is created
 * and provides a global point of access to that connection.
 * </p>
 */
public class MockDatabaseConnection {
    private static Connection instance = null;

    /**
     * Private constructor to prevent instantiation from outside the class.
     * Establishes a connection to the SQLite database.
     */
    private MockDatabaseConnection() {
        String url = "jdbc:sqlite:SampleSalad.db";
        try {
            instance = DriverManager.getConnection(url);
        } catch (SQLException sqlEx) {
            System.err.println(sqlEx);
        }
    }

    /**
     * Provides access to the singleton instance of the database connection.
     * <p>
     * If the connection instance does not exist, it is created and initialized.
     * </p>
     *
     * @return the singleton instance of the {@code Connection}
     */
    public static Connection getInstance() {
        if (instance == null) {
            new MockDatabaseConnection();
        }
        return instance;
    }
}

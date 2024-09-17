package com.example.samplesalad.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The {@code DatabaseConnection} class provides a singleton connection to a MySQL database.
 * <p>
 * This class ensures that only one instance of the database connection is created
 * and provides a method to access this instance.
 * </p>
 */
public class DatabaseConnection {
    private static Connection instance = null;

    /**
     * Private constructor to initialize the database connection.
     * <p>
     * Connects to the MySQL database using the JDBC URL, username, and password.
     * </p>
     */
    private DatabaseConnection() {    }

    /**
     * Returns the singleton instance of the database connection.
     * <p>
     * If the instance does not exist, it is created by calling the private constructor.
     * </p>
     *
     * @return the singleton {@code Connection} instance
     */
    public static Connection getInstance() {
        if (instance == null) {
            try {
                String url = "jdbc:mysql://127.0.0.1:3306/SampleSaladDB";
                Class.forName("com.mysql.cj.jdbc.Driver");
                instance = DriverManager.getConnection(url, "root", "Asdf1Aqw2ARIMAK!@#");
                instance.setAutoCommit(true); // This is usually the default setting
                if (instance != null) {
                    System.out.println("Connected to the database successfully!");
                }
            } catch (ClassNotFoundException e) {
                System.out.println("MySQL JDBC Driver not found.");
                e.printStackTrace();
            } catch (SQLException e) {
                System.out.println("Connection failed.");
                e.printStackTrace();
            }
        }
        return instance;
    }

}

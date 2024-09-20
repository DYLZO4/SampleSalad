package com.example.samplesalad.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for interacting with the Sample table in the database.
 * This class provides methods for CRUD operations: create, read, update, and delete samples.
 */
public class SampleDAO implements ISampleSaladDAO<Sample> {
    private Connection connection;

    /**
     * Constructor that initializes the SampleDAO with a database connection.
     *
     * @param connection the database connection to be used by the DAO
     */
    public SampleDAO(Connection connection) {
        connection = DatabaseConnection.getInstance();
        createTable();
    }

    /**
     * Creates the table for storing samples if it doesn't already exist in the database.
     */
    private void createTable() {
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS samples ("
                    + "SampleID INT AUTO_INCREMENT PRIMARY KEY, "
                    + "filePath VARCHAR(255) NOT NULL, "
                    + "pitch DOUBLE, "
                    + "volume DOUBLE, "
                    + "startTime DOUBLE, "
                    + "endTime DOUBLE"
                    + ")";
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Inserts a new sample into the database.
     *
     * @param sample the sample object to be added to the database
     */
    @Override
    public void add(Sample sample) {
        String query = "INSERT INTO Samples (filePath, pitch, volume, startTime, endTime) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, sample.getFilePath());
            stmt.setDouble(2, sample.getPitch());
            stmt.setDouble(3, sample.getVolume());
            stmt.setDouble(4, sample.getStartTime());
            stmt.setDouble(5, sample.getEndTime());

            int affectedRows = stmt.executeUpdate();

            // Retrieve the generated SampleID and set it in the Sample object
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        sample.setSampleID(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates an existing sample in the database.
     *
     * @param sample the sample object with updated values to be saved in the database
     */
    @Override
    public void update(Sample sample) {
        String query = "UPDATE Samples SET filePath = ?, pitch = ?, volume = ?, startTime = ?, endTime = ? WHERE SampleID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, sample.getFilePath());
            stmt.setDouble(2, sample.getPitch());
            stmt.setDouble(3, sample.getVolume());
            stmt.setDouble(4, sample.getStartTime());
            stmt.setDouble(5, sample.getEndTime());
            stmt.setInt(6, sample.getSampleID());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Sample updated successfully.");
            } else {
                System.out.println("No sample found with the given ID.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a sample from the database.
     *
     * @param sample the sample object to be deleted from the database
     */
    @Override
    public void delete(Sample sample) {
        String query = "DELETE FROM Samples WHERE SampleID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, sample.getSampleID());
            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Sample deleted successfully.");
            } else {
                System.out.println("No sample found with the given ID.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves a sample from the database by its ID.
     *
     * @param id the ID of the sample to retrieve
     * @return the sample object corresponding to the specified ID, or null if not found
     */
    @Override
    public Sample get(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM samples WHERE id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Integer sampleID = resultSet.getInt("sampleID");
                String filePath = resultSet.getString("filePath");
                Double volume = resultSet.getDouble("volume");
                Double pitch = resultSet.getDouble("pitch");
                Double startTime = resultSet.getDouble("startTime");
                Double endTime = resultSet.getDouble("endTime");
                return new Sample(sampleID, filePath, pitch, volume, startTime, endTime);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves all samples from the database.
     *
     * @return a list of all sample objects in the database
     */
    @Override
    public List<Sample> getAll() {
        List<Sample> samples = new ArrayList<>();
        String query = "SELECT * FROM Samples";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Integer sampleID = resultSet.getInt("SampleID");
                String filePath = resultSet.getString("filePath");
                Double pitch = resultSet.getDouble("pitch");
                Double volume = resultSet.getDouble("volume");
                Double startTime = resultSet.getDouble("startTime");
                Double endTime = resultSet.getDouble("endTime");

                filePath = resultSet.wasNull() ? null : filePath;
                pitch = resultSet.wasNull() ? null : pitch;
                volume = resultSet.wasNull() ? null : volume;
                startTime = resultSet.wasNull() ? null : startTime;
                endTime = resultSet.wasNull() ? null : endTime;

                samples.add(new Sample(sampleID, filePath, pitch, volume, startTime, endTime));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return samples;
    }
}

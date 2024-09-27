package com.example.samplesalad.model.DAO;

import com.example.samplesalad.model.DatabaseConnection;
import com.example.samplesalad.model.Sample;

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
     */
    public SampleDAO() {
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
                    + "sampleName VARCHAR(255), "
                    + "sampleArtist VARCHAR(255), "
                    + "sampleGenre VARCHAR(255), "
                    + "pitch DOUBLE, "
                    + "volume DOUBLE, "
                    + "startTime DOUBLE, "
                    + "endTime DOUBLE, "
                    + "dateAdded TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                    + "duration DOUBLE"
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
        String query = "INSERT INTO Samples (filePath, sampleName, sampleArtist, sampleGenre, pitch, volume, startTime, endTime, dateAdded, duration) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, sample.getFilePath());
            stmt.setString(2, sample.getSampleName());
            stmt.setString(3, sample.getSampleArtist());
            stmt.setString(4, sample.getSampleGenre());
            stmt.setDouble(5, sample.getPitch());
            stmt.setDouble(6, sample.getVolume());
            stmt.setDouble(7, sample.getStartTime());
            stmt.setDouble(8, sample.getEndTime());
            stmt.setTimestamp(9, new Timestamp(sample.getDateAdded().getTime()));
            stmt.setDouble(10, sample.getDuration());
            int affectedRows = stmt.executeUpdate();

            // Check if the insert was successful
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
        String query = "UPDATE Samples SET filePath = ?, sampleName = ?, sampleArtist = ?, sampleGenre = ?, pitch = ?, volume = ?, startTime = ?, endTime = ?, dateAdded = ? WHERE SampleID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, sample.getFilePath());
            stmt.setString(2, sample.getSampleName());
            stmt.setString(3, sample.getSampleArtist());
            stmt.setString(4, sample.getSampleGenre());
            stmt.setDouble(5, sample.getPitch());
            stmt.setDouble(6, sample.getVolume());
            stmt.setDouble(7, sample.getStartTime());
            stmt.setDouble(8, sample.getEndTime());
            stmt.setTimestamp(9, new Timestamp(sample.getDateAdded().getTime()));
            stmt.setInt(10, sample.getSampleID());

            int affectedRows = stmt.executeUpdate();

            // Optionally check if the update was successful
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
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM samples WHERE SampleID = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Integer sampleID = resultSet.getInt("SampleID");
                String filePath = resultSet.getString("filePath");
                String sampleName = resultSet.getString("sampleName");
                String sampleArtist = resultSet.getString("sampleArtist");
                String sampleGenre = resultSet.getString("sampleGenre");
                Double volume = resultSet.getDouble("volume");
                Double pitch = resultSet.getDouble("pitch");
                Double startTime = resultSet.getDouble("startTime");
                Double endTime = resultSet.getDouble("endTime");
                Timestamp dateAdded = resultSet.getTimestamp("dateAdded");
                Double duration = resultSet.getDouble("duration");
                return new Sample(sampleID, filePath, sampleName, sampleArtist, sampleGenre, pitch, volume, startTime, endTime, dateAdded, duration);
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
                String sampleName = resultSet.getString("sampleName");
                String sampleArtist = resultSet.getString("sampleArtist");
                String sampleGenre = resultSet.getString("sampleGenre");
                Double pitch = resultSet.getDouble("pitch");
                Double volume = resultSet.getDouble("volume");
                Double startTime = resultSet.getDouble("startTime");
                Double endTime = resultSet.getDouble("endTime");
                Timestamp dateAdded = resultSet.getTimestamp("dateAdded");
                Double duration = resultSet.getDouble("duration");

                // Create a Sample object and add it to the list
                samples.add(new Sample(sampleID, filePath, sampleName, sampleArtist, sampleGenre, pitch, volume, startTime, endTime, dateAdded, duration));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return samples;
    }
}
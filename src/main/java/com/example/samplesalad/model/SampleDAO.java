package com.example.samplesalad.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SampleDAO implements ISampleSaladDAO<Sample> {
    private Connection connection;

    public SampleDAO(Connection connection) {
        connection = DatabaseConnection.getInstance();
        createTable();
    }

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

    @Override
    public void add(Sample sample) {
        String query = "INSERT INTO Samples (filePath, pitch, volume, startTime, endTime) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) { // Specify that generated keys should be returned
            stmt.setString(1, sample.getFilePath());
            stmt.setDouble(2, sample.getPitch());
            stmt.setDouble(3, sample.getVolume());
            stmt.setDouble(4, sample.getStartTime());
            stmt.setDouble(5, sample.getEndTime());

            int affectedRows = stmt.executeUpdate();

            // Check if the insert was successful
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        sample.setSampleID(generatedKeys.getInt(1)); // Set the generated SampleID in your object
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Sample sample) {
        String query = "UPDATE Samples SET filePath = ?, pitch = ?, volume = ?, startTime = ?, endTime = ? WHERE SampleID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, sample.getFilePath());
            stmt.setDouble(2, sample.getPitch());
            stmt.setDouble(3, sample.getVolume());
            stmt.setDouble(4, sample.getStartTime());
            stmt.setDouble(5, sample.getEndTime());
            stmt.setInt(6, sample.getSampleID()); // Make sure to set SampleID for the WHERE clause

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

    @Override
    public void delete(Sample sample) {
        String query = "DELETE FROM Samples WHERE SampleID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, sample.getSampleID()); // Set the SampleID to delete
            int affectedRows = stmt.executeUpdate();
            // Optionally check if delete was successful
            if (affectedRows > 0) {
                System.out.println("Sample deleted successfully.");
            } else {
                System.out.println("No sample found with the given ID.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

                    // Handle default values for nullable columns if needed
                    filePath = resultSet.wasNull() ? null : filePath;
                    pitch = resultSet.wasNull() ? null : pitch;
                    volume = resultSet.wasNull() ? null : volume;
                    startTime = resultSet.wasNull() ? null : startTime;
                    endTime = resultSet.wasNull() ? null : endTime;

                    // Create a Sample object and add it to the list
                    samples.add(new Sample(sampleID, filePath, pitch, volume, startTime, endTime));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return samples;
    }
}

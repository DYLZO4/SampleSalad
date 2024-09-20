package com.example.samplesalad.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ProjectSequenceDAO {
    private Connection connection;

    public ProjectSequenceDAO(Connection connection) {
        this.connection = connection;
        createTable();
    }

    private void createTable() {
        try {
            Statement statement = connection.createStatement();

            // Create a table for projectSequencers, ensuring valid foreign keys
            String query = "CREATE TABLE IF NOT EXISTS projectSequencers ("
                    + "projectId INT, "
                    + "sequencerId INT, "
                    + "FOREIGN KEY (projectId) REFERENCES projects(projectId), "
                    + "FOREIGN KEY (sequencerId) REFERENCES sequencers(sequencerId))";
            statement.execute(query);
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Add a sequencer to a project
    public void add(Sequencer sequencer, Project project) {
        String query = "INSERT INTO projectSequencers (projectId, sequencerId) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, project.getProjectID());
            statement.setInt(2, sequencer.getSequencerID());
            statement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update a sequencer entry for a project
    public void update(Sequencer sequencer, Project project) {
        String query = "UPDATE projectSequencers SET sequencerId = ? WHERE projectId = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, sequencer.getSequencerID());
            statement.setInt(2, project.getProjectID());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete a sequencer from the projectSequencers table
    public void delete(Sequencer sequencer) {
        String query = "DELETE FROM projectSequencers WHERE sequencerId = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, sequencer.getSequencerID());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get a specific sequencer ID by its ID
    public int get(int sequencerId) {
        String query = "SELECT sequencerId FROM projectSequencers WHERE sequencerId = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, sequencerId);
            ResultSet results = statement.executeQuery();
            if (results.next()) {
                return results.getInt("sequencerId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;  // Return 0 if the sequencer is not found
    }

    // Get all sequencer IDs from the projectSequencers table
    public List<Integer> getAll() {
        List<Integer> sequencerIds = new ArrayList<>();
        String query = "SELECT sequencerId FROM projectSequencers";
        try (Statement statement = connection.createStatement()) {
            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                sequencerIds.add(results.getInt("sequencerId"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sequencerIds;
    }

    public List<Integer> getSequencerIdsByProjectId(int projectId) {
        List<Integer> sequencerIds = new ArrayList<>();
        String query = "SELECT sequencerId FROM projectSequencers WHERE projectId = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, projectId);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                sequencerIds.add(results.getInt("sequencerId"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sequencerIds;
    }
}





package com.example.samplesalad.model.DAO;

import com.example.samplesalad.model.Project;
import com.example.samplesalad.model.Sequencer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code ProjectSequenceDAO} class provides data access methods for managing
 * the relationships between projects and sequencers in the database.
 * It handles adding, updating, and deleting sequencers associated with projects,
 * and retrieving sequencer IDs based on project or sequencer IDs.
 */
public class ProjectSequenceDAO {
    private Connection connection;

    /**
     * Constructs a {@code ProjectSequenceDAO} object with the given database connection.
     *
     */
    public ProjectSequenceDAO() {
    }

    /**
     * Creates the `projectSequencers` table in the database if it doesn't already exist.
     * This table stores the relationships between projects and sequencers, using
     * foreign keys to reference the `projects` and `sequencers` tables.
     */
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
            e.printStackTrace(); // Handle exceptions appropriately in your application
        }
    }

    /**
     * Adds a sequencer to a project by inserting a new record into the
     * `projectSequencers` table.
     *
     * @param sequencer The {@link Sequencer} to be associated with the project.
     * @param project   The {@link Project} to which the sequencer is added.
     */
    public void add(Sequencer sequencer, Project project) {
        String query = "INSERT INTO projectSequencers (projectId, sequencerId) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, project.getProjectID());
            statement.setInt(2, sequencer.getSequencerID());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions
        }
    }

    /**
     * Updates the sequencer associated with a project in the `projectSequencers` table.
     *
     * @param sequencer The {@link Sequencer} to be updated.
     * @param project   The {@link Project} for which the sequencer is updated.
     */
    public void update(Sequencer sequencer, Project project) {
        String query = "UPDATE projectSequencers SET sequencerId = ? WHERE projectId = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, sequencer.getSequencerID());
            statement.setInt(2, project.getProjectID());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions
        }
    }

    /**
     * Deletes a sequencer from the `projectSequencers` table. This removes the
     * association between the sequencer and the project.
     *
     * @param sequencer The {@link Sequencer} to be deleted from the project.
     */
    public void delete(Sequencer sequencer) {
        String query = "DELETE FROM projectSequencers WHERE sequencerId = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, sequencer.getSequencerID());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions
        }
    }

    /**
     * Retrieves the ID of a specific sequencer from the `projectSequencers` table.
     *
     * @param sequencerId The ID of the sequencer to retrieve.
     * @return The ID of the sequencer, or 0 if the sequencer is not found.
     */
    public int get(int sequencerId) {
        String query = "SELECT sequencerId FROM projectSequencers WHERE sequencerId = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, sequencerId);
            ResultSet results = statement.executeQuery();
            if (results.next()) {
                return results.getInt("sequencerId");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions
        }
        return 0;  // Return 0 if the sequencer is not found
    }

    /**
     * Retrieves all sequencer IDs from the `projectSequencers` table.
     *
     * @return A list of all sequencer IDs in the table.
     */
    public List<Integer> getAll() {
        List<Integer> sequencerIds = new ArrayList<>();
        String query = "SELECT sequencerId FROM projectSequencers";
        try (Statement statement = connection.createStatement()) {
            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                sequencerIds.add(results.getInt("sequencerId"));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions
        }
        return sequencerIds;
    }

    /**
     * Retrieves a list of sequencer IDs associated with a specific project ID from
     * the `projectSequencers` table.
     *
     * @param projectId The ID of the project.
     * @return A list of sequencer IDs associated with the project.
     */
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
            e.printStackTrace(); // Handle exceptions
        }
        return sequencerIds;
    }
}
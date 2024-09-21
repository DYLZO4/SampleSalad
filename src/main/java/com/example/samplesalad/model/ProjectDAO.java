package com.example.samplesalad.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for interacting with the Project table in the database.
 * This class provides methods for CRUD operations: create, read, update, and delete projects.
 */
public class ProjectDAO implements ISampleSaladDAO<Project> {

    private Connection connection;

    /**
     * Constructor that initializes the ProjectDAO with a database connection.
     *
     */
    public ProjectDAO() {
        connection = DatabaseConnection.getInstance();
        createTable();
    }

    /**
     * Creates the table for storing projects if it doesn't already exist in the database.
     */
    private void createTable() {
        try (Statement statement = connection.createStatement()) {
            String query = "CREATE TABLE IF NOT EXISTS projects ("
                    + "projectId INT PRIMARY KEY AUTO_INCREMENT, "
                    + "projectName VARCHAR(255), "
                    + "projectBPM INT, "
                    + "filePath VARCHAR(255))";
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inserts a new project into the database.
     *
     * @param project the project object to be added to the database
     */
    @Override
    public void add(Project project) {
        String query = "INSERT INTO projects (projectName, projectBPM, filePath) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, project.getProjectName());
            statement.setInt(2, project.getProjectBPM());
            statement.setString(3, project.getFilePath());
            statement.executeUpdate();

            // Retrieve the generated project ID and set it in the Project object
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                project.setProjectID(generatedKeys.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates an existing project in the database.
     *
     * @param project the project object with updated values to be saved in the database
     */
    @Override
    public void update(Project project) {
        String query = "UPDATE projects SET projectName = ?, projectBPM = ?, filePath = ? WHERE projectId = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, project.getProjectName());
            statement.setInt(2, project.getProjectBPM());
            statement.setString(3, project.getFilePath());
            statement.setInt(4, project.getProjectID());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a project from the database.
     *
     * @param project the project object to be deleted from the database
     */
    @Override
    public void delete(Project project) {
        String query = "DELETE FROM projects WHERE projectId = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, project.getProjectID());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves a project from the database by its ID.
     *
     * @param id the ID of the project to retrieve
     * @return the project object corresponding to the specified ID, or null if not found
     */
    @Override
    public Project get(int id) {
        String query = "SELECT * FROM projects WHERE projectId = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet results = statement.executeQuery();
            if (results.next()) {
                Project project = new Project(
                        results.getString("projectName"),
                        results.getInt("projectBPM")
                );
                project.setProjectID(results.getInt("projectId"));
                project.setFilePath(results.getString("filePath"));
                return project;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves all projects from the database.
     *
     * @return a list of all project objects in the database
     */
    @Override
    public List<Project> getAll() {
        List<Project> projects = new ArrayList<>();
        String query = "SELECT * FROM projects";
        try (Statement statement = connection.createStatement()) {
            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                Project project = new Project(
                        results.getString("projectName"),
                        results.getInt("projectBPM")
                );
                project.setProjectID(results.getInt("projectId"));
                project.setFilePath(results.getString("filePath"));
                projects.add(project);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }
}

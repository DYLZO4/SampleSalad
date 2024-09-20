package com.example.samplesalad.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectDAO implements ISampleSaladDAO<Project> {

    private Connection connection;

    public ProjectDAO(Connection connection) {
        this.connection = connection;
        createTable();
    }

    // Create the table for storing projects if it doesn't already exist
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

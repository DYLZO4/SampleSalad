package com.example.samplesalad.model.DAO;

import com.example.samplesalad.model.DatabaseConnection;
import com.google.gson.Gson;
import javafx.scene.input.KeyCode;
import com.example.samplesalad.model.Pad;
import com.example.samplesalad.model.DrumKit;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * The {@code KeyBindingDAO} class is responsible for performing CRUD operations
 * (Create, Read, Update, Delete) on key bindings in a database.
 */
public class KeyBindingDAO {
    private Connection connection;
    private Gson gson = new Gson();

    /**
     * Constructor for {@code KeyBindingDAO} class.
     * Initializes the connection and creates the key_bindings table if it doesn't exist.
     */
    public KeyBindingDAO() {
//        this.connection = DatabaseConnection.getInstance();
        createTable();
    }

    /**
     * Creates the key_bindings table in the database if it does not exist.
     */
    private void createTable() {
//        try {
//            Statement statement = connection.createStatement();
//            String query = "CREATE TABLE IF NOT EXISTS key_bindings ("
//                    + "user_id INT, "
//                    + "bindings JSON, "
//                    + "FOREIGN KEY (user_id) REFERENCES users(UserID))";
//            statement.execute(query);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    public String loadKeyBindings(int userId) {
        String query = "SELECT bindings FROM key_bindings WHERE user_id = ?";
        String jsonBindings = null;

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                jsonBindings = rs.getString("bindings");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return jsonBindings;
    }

    public void updateKeyBindings(int userId, Map<String, Integer> keyBindings) {
        String query = "UPDATE key_bindings SET bindings = ? WHERE user_id = ?";
        String jsonBindings = gson.toJson(keyBindings);

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, jsonBindings);
            pstmt.setInt(2, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertKeyBindings(int userId, Map<String, Integer> keyBindings) {
        String query = "INSERT INTO key_bindings (user_id, bindings) VALUES (?, ?)";
        String jsonBindings = gson.toJson(keyBindings);

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, jsonBindings);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
package com.example.samplesalad.model.DAO;

import com.example.samplesalad.model.DatabaseConnection;
import com.example.samplesalad.model.user.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code UserDAO} class provides data access methods for interacting with user records in a database.
 * It implements the {@code ISampleSaladDAO} interface for CRUD operations on the {@code User} object.
 * This class uses JDBC for database operations.
 */
public class UserDAO implements ISampleSaladDAO<User> {

    /**
     * A list to hold users locally. This is primarily for demonstration purposes,
     * as the data is typically retrieved and stored in a database.
     */
    public static final ArrayList<User> users = new ArrayList<>();

    private Connection connection;

    /**
     * Constructs a new {@code UserDAO} object and initializes the database connection.
     * It also ensures the users table exists by invoking the {@code createTable()} method.
     */
    public UserDAO() {
        connection = DatabaseConnection.getInstance();
        createTable();
    }

    /**
     * Creates the "users" table in the database if it doesn't already exist.
     * The table includes columns for user ID, first name, last name, password, phone, and email.
     */
    private void createTable() {
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS users ("
                    + "id INTEGER PRIMARY KEY AUTO_INCREMENT,"
                    + "firstName VARCHAR(255) NOT NULL,"
                    + "lastName VARCHAR(255) NOT NULL,"
                    + "password VARCHAR(255) NOT NULL,"
                    + "phone VARCHAR(255) NOT NULL,"
                    + "email VARCHAR(255) NOT NULL"
                    + ")";
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a new {@code User} to the database by inserting the user’s details into the "users" table.
     * The user’s ID is automatically generated and set after the insertion.
     *
     * @param user the {@code User} object to be added to the database
     */
    @Override
    public void add(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO users (firstName, lastName, password, phone, email) VALUES (?, ?, ?, ?, ?)",
                         Statement.RETURN_GENERATED_KEYS
            );
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getHashedPassword());
            statement.setString(4, user.getPhone());
            statement.setString(5, user.getEmail());
            statement.executeUpdate();
            // Set the ID of the newly added user
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates an existing {@code User} in the database.
     * The user's first name, last name, password, phone, and email are updated based on the user’s ID.
     *
     * @param user the {@code User} object containing the updated information
     */
    public void update(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE users SET firstName = ?, lastName = ?, password = ?, phone = ?, email = ? WHERE id = ?"
            );
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getHashedPassword());
            statement.setString(4, user.getPhone());
            statement.setString(5, user.getEmail());
            statement.setInt(6, user.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a {@code User} from the database based on the user's ID.
     *
     * @param user the {@code User} object to be deleted
     */
    public void delete(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE id = ?");
            statement.setInt(1, user.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves a {@code User} from the database by the user's email address.
     *
     * @param email the email of the user to be retrieved
     * @return the {@code User} object corresponding to the specified email, or {@code null} if not found
     */
    public User getByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String password = resultSet.getString("password"); // Password is hashed
                String phone = resultSet.getString("phone");
                return new User(firstName, lastName, password, phone, email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves a {@code User} from the database by the user's ID.
     *
     * @param id the ID of the user to be retrieved
     * @return the {@code User} object corresponding to the specified ID, or {@code null} if not found
     */
    @Override
    public User get(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String hashedPassword = resultSet.getString("hashedPassword");
                String phone = resultSet.getString("phone");
                String email = resultSet.getString("email");
                return new User(firstName, lastName, hashedPassword, phone, email);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves all {@code User} objects from the database.
     *
     * @return a {@code List} of all users in the database
     */
    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM users";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String hashedPassword = resultSet.getString("hashedPassword");
                String phone = resultSet.getString("phone");
                String email = resultSet.getString("email");
                User user = new User(firstName, lastName, hashedPassword, phone, email);
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }
}

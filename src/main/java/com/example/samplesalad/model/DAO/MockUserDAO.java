package com.example.samplesalad.model.DAO;

import com.example.samplesalad.model.MockDatabaseConnection;
import com.example.samplesalad.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code MockUserDAO} class provides an implementation of the {@code ISampleSaladDAO}
 * interface for managing {@link User} objects using a mock database connection.
 * This class includes methods for creating, updating, deleting, and retrieving users
 * from the database.
 */
public class MockUserDAO implements ISampleSaladDAO<User> {

    public static final ArrayList<User> users = new ArrayList<>();

    private Connection connection;

    /**
     * Constructor for {@code MockUserDAO}.
     * Initializes the database connection and creates the users table if it doesn't exist.
     */
    public MockUserDAO() {
        connection = MockDatabaseConnection.getInstance();
        createTable();
    }

    /**
     * Creates the users table in the database if it does not already exist.
     * The table includes columns for user id, first name, last name, password, phone, and email.
     */
    private void createTable() {
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS users ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "firstName VARCHAR NOT NULL,"
                    + "lastName VARCHAR NOT NULL,"
                    + "password VARCHAR NOT NULL,"
                    + "phone VARCHAR NOT NULL,"
                    + "email VARCHAR NOT NULL"
                    + ")";
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a new {@link User} to the database.
     *
     * @param user the user to add to the database
     */
    @Override
    public void add(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO contacts (firstName, lastName, hashedPassword, phone, email) VALUES (?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getHashedPassword());
            statement.setString(4, user.getPhone());
            statement.setString(5, user.getEmail());
            statement.executeUpdate();

            // Set the id of the new user
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates an existing {@link User} in the database.
     *
     * @param user the user whose information is to be updated
     */
    public void update(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE contacts SET firstName = ?, lastName = ?, password = ?, phone = ?, email = ? WHERE id = ?"
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
     * Deletes a {@link User} from the database.
     *
     * @param user the user to delete from the database
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
     * Retrieves a {@link User} from the database by email address.
     *
     * @param email the email address of the user to retrieve
     * @return the {@link User} object with the specified email address, or {@code null} if not found
     */
    public User getByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String password = resultSet.getString("password");
                String phone = resultSet.getString("phone");
                return new User(firstName, lastName, password, phone, email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves a {@link User} from the database by ID.
     *
     * @param id the ID of the user to retrieve
     * @return the {@link User} object with the specified ID, or {@code null} if not found
     */
    @Override
    public User get(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM contacts WHERE id = ?");
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
     * Retrieves a list of all {@link User} objects in the database.
     *
     * @return a list of all users in the database
     */
    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM contacts";
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

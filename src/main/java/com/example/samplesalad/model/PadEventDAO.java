package com.example.samplesalad.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code PadEventDAO} class handles CRUD operations for {@link PadEvent} objects,
 * storing and retrieving pad events from a SQL database. This class implements the {@link ISampleSaladDAO} interface.
 */
public class PadEventDAO implements ISampleSaladDAO<PadEvent> {
    private Connection connection;

    /**
     * Constructor for the {@code PadEventDAO} class.
     * Initializes the database connection and creates the pad events table if it does not exist.
     *
     * @param connection the SQL database connection to be used for DAO operations
     */
    public PadEventDAO(Connection connection) {
        this.connection = connection;
        createTable();
    }

    /**
     * Creates the padevents table in the database if it does not exist.
     */
    private void createTable() {
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS padevents ("
                    + "eventID INT AUTO_INCREMENT PRIMARY KEY, "
                    + "padID INT, "
                    + "timeStamp DOUBLE, "
                    + "FOREIGN KEY (padID) REFERENCES pads(padID))";
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a new {@link PadEvent} to the padevents table in the database.
     *
     * @param padEvent the {@code PadEvent} object to be added
     */
    @Override
    public void add(PadEvent padEvent) {
        String query = "INSERT INTO padevents (padID, timeStamp) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, padEvent.getPadID());
            stmt.setDouble(2, padEvent.getTimeStamp());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates an existing {@code PadEvent} in the padevents table.
     *
     * @param padEvent the {@code PadEvent} object to be updated
     */
    @Override
    public void update(PadEvent padEvent) {
        String query = "UPDATE padevents SET timeStamp = ? WHERE eventID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDouble(1, padEvent.getTimeStamp());
            stmt.setInt(2, padEvent.getPadID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a {@code PadEvent} from the padevents table in the database.
     *
     * @param padEvent the {@code PadEvent} object to be deleted
     */
    @Override
    public void delete(PadEvent padEvent) {
        String query = "DELETE FROM padevents WHERE eventID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, padEvent.getPadID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves a {@code PadEvent} from the padevents table based on the pad ID.
     *
     * @param padID the ID of the {@code Pad} associated with the event to be retrieved
     * @return the {@code PadEvent} object with the specified ID, or {@code null} if not found
     */
    @Override
    public PadEvent get(int padID) {
        String query = "SELECT * FROM padevents WHERE eventID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, padID);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                int padId = resultSet.getInt("padID");
                double timeStamp = resultSet.getDouble("timeStamp");

                // Retrieve the associated Pad object
                PadDAO padDAO = new PadDAO(connection);
                Pad pad = padDAO.get(padId);

                // Create and return the PadEvent
                PadEvent padEvent = new PadEvent(pad);
                padEvent.triggerEvent(); // Record the current event time
                return padEvent;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves a list of {@code PadEvent} objects associated with a specific pattern ID.
     *
     * @param patternId the ID of the pattern associated with the events to retrieve
     * @return a {@code List} of {@code PadEvent} objects associated with the given pattern ID
     */
    public List<PadEvent> getPadEventsByPatternId(int patternId) {
        List<PadEvent> padEvents = new ArrayList<>();
        String query = "SELECT * FROM pattern_events WHERE patternId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, patternId);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                int padId = resultSet.getInt("padId");
                double timeStamp = resultSet.getDouble("timeStamp");

                // Fetch the associated Pad
                PadDAO padDAO = new PadDAO(connection);
                Pad pad = padDAO.get(padId);

                PadEvent event = new PadEvent(pad);
                event.triggerEvent(); // Sets the timestamp (if needed)
                padEvents.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return padEvents;
    }

    /**
     * Retrieves all {@code PadEvent} objects. Currently returns an empty list as it is not implemented.
     *
     * @return a {@code List} of {@code PadEvent} objects, currently an empty list
     */
    @Override
    public List<PadEvent> getAll() {
        return List.of(); // Return an empty list for now
    }
}
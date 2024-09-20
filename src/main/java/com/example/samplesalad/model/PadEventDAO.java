package com.example.samplesalad.model;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PadEventDAO implements ISampleSaladDAO<PadEvent>{
    private Connection connection;

    public PadEventDAO(Connection connection) {
        this.connection = connection;
        createTable();
    }

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
            e.printStackTrace(); // Handle exceptions
        }
        return padEvents;
    }

    @Override
    public List getAll() {
        return List.of();
    }
}

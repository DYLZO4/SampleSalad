package com.example.samplesalad.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatternDAO implements ISampleSaladDAO<Pattern> {
    private Connection connection;

    public PatternDAO(Connection connection) {
        this.connection = connection;
        createTable();
    }

    private void createTable() {
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS patterns ("
                    + "patternId INT AUTO_INCREMENT PRIMARY KEY, "
                    + "length INT)";
            statement.execute(query);

            String eventsTableQuery = "CREATE TABLE IF NOT EXISTS pattern_events ("
                    + "eventId INT AUTO_INCREMENT PRIMARY KEY, "
                    + "patternId INT, "
                    + "padId INT, "
                    + "timeStamp DOUBLE, "
                    + "FOREIGN KEY (patternId) REFERENCES patterns(patternId), "
                    + "FOREIGN KEY (padId) REFERENCES pads(padId))";
            statement.execute(eventsTableQuery);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(Pattern pattern) {
        String query = "INSERT INTO patterns (length) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, pattern.getLength());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int patternId = generatedKeys.getInt(1);
                        addPatternEvents(patternId, pattern.getPadEvents());
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addPatternEvents(int patternId, List<PadEvent> padEvents) {
        String query = "INSERT INTO pattern_events (patternId, padId, timeStamp) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            for (PadEvent event : padEvents) {
                stmt.setInt(1, patternId);
                stmt.setInt(2, event.getPadID());
                stmt.setDouble(3, event.getTimeStamp());
                stmt.addBatch();
            }
            stmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Pattern pattern) {
        String query = "UPDATE patterns SET length = ? WHERE patternId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, pattern.getLength());
            stmt.setInt(2, pattern.getPatternID()); // Assuming the Pattern class has a getId() method.
            stmt.executeUpdate();

            // Update pattern events
            deletePatternEvents(pattern.getPatternID());
            addPatternEvents(pattern.getPatternID(), pattern.getPadEvents());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deletePatternEvents(int patternId) {
        String query = "DELETE FROM pattern_events WHERE patternId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, patternId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Pattern pattern) {
        String query = "DELETE FROM patterns WHERE patternId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, pattern.getPatternID()); // Assuming the Pattern class has a getId() method.
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                deletePatternEvents(pattern.getPatternID());
                System.out.println("Pattern and its events deleted successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Pattern get(int id) {
        String query = "SELECT * FROM patterns WHERE patternId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                int patternId = resultSet.getInt("patternId");
                int length = resultSet.getInt("length");

                List<PadEvent> padEvents = getPadEvents(patternId);
                Pattern pattern = new Pattern(length);
                padEvents.forEach(pattern::addPadEvent);

                return pattern;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<PadEvent> getPadEvents(int patternId) {
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
                event.triggerEvent(); // Sets the timestamp
                padEvents.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return padEvents;
    }

    @Override
    public List<Pattern> getAll() {
        List<Pattern> patterns = new ArrayList<>();
        String query = "SELECT * FROM patterns";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet resultSet = stmt.executeQuery()) {

            while (resultSet.next()) {
                int patternId = resultSet.getInt("patternId");
                int length = resultSet.getInt("length");

                List<PadEvent> padEvents = getPadEvents(patternId);
                Pattern pattern = new Pattern(length);
                padEvents.forEach(pattern::addPadEvent);
                patterns.add(pattern);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patterns;
    }

    public List<Pattern> getPatternsBySequencerId(int sequencerId) {
        List<Pattern> patterns = new ArrayList<>();
        String query = "SELECT patternId FROM sequencer_patterns WHERE sequencerId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, sequencerId);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                int patternId = resultSet.getInt("patternId");
                Pattern pattern = get(patternId); // Reuse your existing get(id) method
                if (pattern != null) {
                    patterns.add(pattern);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patterns;
    }
}
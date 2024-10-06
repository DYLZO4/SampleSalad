package com.example.samplesalad.model.DAO;

import com.example.samplesalad.model.DatabaseConnection;
import com.example.samplesalad.model.Pattern;
import com.example.samplesalad.model.Sequencer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The SequencerDAO class is responsible for performing CRUD operations
 * on Sequencer objects in the database.
 */
public class SequencerDAO implements ISampleSaladDAO<Sequencer> {
    private Connection connection;

    /**
     * Constructs a SequencerDAO with a provided database connection.
     *
     */
    public SequencerDAO() {
        connection = DatabaseConnection.getInstance();
        createTable();
    }

    /**
     * Creates the necessary tables for sequencers and their associated patterns.
     */
    private void createTable() {
        try {
            Statement statement = connection.createStatement();

            // Create a table for sequencers
            String query = "CREATE TABLE IF NOT EXISTS sequencers ("
                    + "sequencerId INT AUTO_INCREMENT PRIMARY KEY, "
                    + "tempo INT, "
                    + "timeSignatureNumerator INT, "
                    + "timeSignatureDenominator INT, "
                    + "isPlaying BOOLEAN)";
            statement.execute(query);

            // Create a table for patterns associated with a sequencer
            String patternsQuery = "CREATE TABLE IF NOT EXISTS sequencer_patterns ("
                    + "sequencerId INT, "
                    + "patternId INT, "
                    + "FOREIGN KEY (sequencerId) REFERENCES sequencers(sequencerId), "
                    + "FOREIGN KEY (patternId) REFERENCES patterns(patternId))";
            statement.execute(patternsQuery);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a Sequencer to the database.
     *
     * @param sequencer the Sequencer object to be added
     */
    @Override
    public void add(Sequencer sequencer) {
        String query = "INSERT INTO sequencers (tempo, timeSignatureNumerator, timeSignatureDenominator, isPlaying) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, sequencer.getTempo());
            stmt.setInt(2, sequencer.getTimeSignatureNumerator());
            stmt.setInt(3, sequencer.getTimeSignatureDenominator());
            stmt.setBoolean(4, sequencer.isPlaying());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int sequencerId = generatedKeys.getInt(1);
                        addSequencerPatterns(sequencerId, sequencer.getPatterns());
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds the patterns associated with a sequencer to the database.
     *
     * @param sequencerId the ID of the Sequencer
     * @param patterns    the list of patterns associated with the Sequencer
     */
    private void addSequencerPatterns(int sequencerId, List<Pattern> patterns) {
        String query = "INSERT INTO sequencer_patterns (sequencerId, patternId) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            for (Pattern pattern : patterns) {
                stmt.setInt(1, sequencerId);
                stmt.setInt(2, pattern.getPatternID());
                stmt.addBatch();
            }
            stmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates an existing Sequencer in the database.
     *
     * @param sequencer the Sequencer object to be updated
     */
    @Override
    public void update(Sequencer sequencer) {
        String query = "UPDATE sequencers SET tempo = ?, timeSignatureNumerator = ?, timeSignatureDenominator = ?, isPlaying = ? WHERE sequencerId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, sequencer.getTempo());
            stmt.setInt(2, sequencer.getTimeSignatureNumerator());
            stmt.setInt(3, sequencer.getTimeSignatureDenominator());
            stmt.setBoolean(4, sequencer.isPlaying());
            stmt.setInt(5, sequencer.getSequencerID());

            stmt.executeUpdate();

            // Update sequencer patterns
            deleteSequencerPatterns(sequencer.getSequencerID());
            addSequencerPatterns(sequencer.getSequencerID(), sequencer.getPatterns());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes the patterns associated with a Sequencer from the database.
     *
     * @param sequencerId the ID of the Sequencer
     */
    private void deleteSequencerPatterns(int sequencerId) {
        String query = "DELETE FROM sequencer_patterns WHERE sequencerId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, sequencerId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a Sequencer from the database.
     *
     * @param sequencer the Sequencer object to be deleted
     */
    @Override
    public void delete(Sequencer sequencer) {
        String query = "DELETE FROM sequencers WHERE sequencerId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, sequencer.getSequencerID());
            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                deleteSequencerPatterns(sequencer.getSequencerID());
                System.out.println("Sequencer and its patterns deleted successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves a Sequencer from the database by its ID.
     *
     * @param id the ID of the Sequencer
     * @return the Sequencer object, or null if not found
     */
    @Override
    public Sequencer get(int id) {
        String query = "SELECT * FROM sequencers WHERE sequencerId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                int sequencerId = resultSet.getInt("sequencerId");
                int tempo = resultSet.getInt("tempo");
                int numerator = resultSet.getInt("timeSignatureNumerator");
                int denominator = resultSet.getInt("timeSignatureDenominator");
                boolean isPlaying = resultSet.getBoolean("isPlaying");

                Sequencer sequencer = new Sequencer(tempo, numerator, denominator);
                if (isPlaying) {
                    sequencer.playSequence();
                }
                List<Pattern> patterns = getSequencerPatterns(sequencerId);
                for (Pattern pattern : patterns) {
                    sequencer.addPattern(pattern);
                }

                return sequencer;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves all patterns associated with a Sequencer.
     *
     * @param sequencerId the ID of the Sequencer
     * @return a list of Pattern objects
     */
    private List<Pattern> getSequencerPatterns(int sequencerId) {
        List<Pattern> patterns = new ArrayList<>();
        String query = "SELECT patternId FROM sequencer_patterns WHERE sequencerId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, sequencerId);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                int patternId = resultSet.getInt("patternId");

                PatternDAO patternDAO = new PatternDAO();
                Pattern pattern = patternDAO.get(patternId);
                if (pattern != null) {
                    patterns.add(pattern);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patterns;
    }

    /**
     * Retrieves all Sequencers from the database.
     *
     * @return a list of Sequencer objects
     */
    @Override
    public List<Sequencer> getAll() {
        List<Sequencer> sequencers = new ArrayList<>();
        String query = "SELECT * FROM sequencers";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet resultSet = stmt.executeQuery()) {

            while (resultSet.next()) {
                int sequencerId = resultSet.getInt("sequencerId");
                int tempo = resultSet.getInt("tempo");
                int numerator = resultSet.getInt("timeSignatureNumerator");
                int denominator = resultSet.getInt("timeSignatureDenominator");
                boolean isPlaying = resultSet.getBoolean("isPlaying");

                Sequencer sequencer = new Sequencer(tempo, numerator, denominator);
                if (isPlaying) {
                    sequencer.playSequence();
                }

                List<Pattern> patterns = getSequencerPatterns(sequencerId);
                for (Pattern pattern : patterns) {
                    sequencer.addPattern(pattern);
                }

                sequencers.add(sequencer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sequencers;
    }
}

package com.example.samplesalad.model.DAO;

import com.example.samplesalad.model.DatabaseConnection;
import com.example.samplesalad.model.Pad;
import com.example.samplesalad.model.Sample;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code PadDAO} class handles CRUD operations (Create, Read, Update, Delete) for {@link Pad} objects in a database.
 * This class implements the {@link ISampleSaladDAO} interface, providing specific operations for the pads.
 */
public class PadDAO implements ISampleSaladDAO<Pad> {
    private Connection connection;

    /**
     * Constructor for the {@code PadDAO} class.
     * Initializes the database connection and creates the pads table if it does not exist.
     *
     */
    public PadDAO() {
//        connection = DatabaseConnection.getInstance();
        createTable();
    }

    /**
     * Creates the pads table in the database if it does not exist.
     */
    private void createTable() {
//        try {
//            Statement statement = connection.createStatement();
//            String query = "CREATE TABLE IF NOT EXISTS pads ("
//                    + "padID INT AUTO_INCREMENT PRIMARY KEY, "
//                    + "kitID INT, "
//                    + "sampleID INT, "
//                    + "volume DOUBLE, "
//                    + "keybind VARCHAR(255), "
//                    + "FOREIGN KEY (kitID) REFERENCES drumkits(kitID), "
//                    + "FOREIGN KEY (sampleID) REFERENCES samples(sampleID))";
//            statement.execute(query);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * Adds a new {@link Pad} to the pads table in the database.
     *
     * @param pad the {@code Pad} object to be added
     */
    @Override
    public void add(Pad pad) {
        String query = "INSERT INTO pads (sampleId, volume, keybind) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, pad.getSample().getSampleID());
            stmt.setDouble(2, pad.getVolume());
            stmt.setString(3, pad.getKeybind());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        pad.setPadId(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates an existing {@code Pad} in the pads table.
     *
     * @param pad the {@code Pad} object to be updated
     */
    @Override
    public void update(Pad pad) {
        String query = "UPDATE pads SET sampleId = ?, volume = ?, keybind = ? WHERE padId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, pad.getSample().getSampleID());
            stmt.setDouble(2, pad.getVolume());
            stmt.setString(3, pad.getKeybind());
            stmt.setInt(4, pad.getPadId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Pad updated successfully.");
            } else {
                System.out.println("No pad found with the given ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a {@code Pad} from the pads table in the database.
     *
     * @param pad the {@code Pad} object to be deleted
     */
    @Override
    public void delete(Pad pad) {
        String query = "DELETE FROM pads WHERE padId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, pad.getPadId());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Pad deleted successfully.");
            } else {
                System.out.println("No pad found with the given ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves a {@code Pad} from the pads table based on the pad ID.
     *
     * @param id the ID of the {@code Pad} to be retrieved
     * @return the {@code Pad} object with the specified ID, or {@code null} if not found
     */
    @Override
    public Pad get(int id) {
        String query = "SELECT * FROM pads WHERE padId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                int padId = resultSet.getInt("padId");
                int sampleId = resultSet.getInt("sampleId");
                double volume = resultSet.getDouble("volume");
                String keybind = resultSet.getString("keybind");

                // Fetch the associated sample
                SampleDAO sampleDAO = new SampleDAO();
                Sample sample = sampleDAO.get(sampleId);

                Pad pad = new Pad(sample);
                pad.setPadId(padId);
                pad.setVolume(volume);
                pad.setKeybind(keybind);
                return pad;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves all {@code Pad} objects from the pads table.
     *
     * @return a {@code List} of all {@code Pad} objects in the database
     */
    @Override
    public List<Pad> getAll() {
        List<Pad> pads = new ArrayList<>();
        String query = "SELECT * FROM pads";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet resultSet = stmt.executeQuery()) {

            while (resultSet.next()) {
                int padId = resultSet.getInt("padId");
                int sampleId = resultSet.getInt("sampleId");
                double volume = resultSet.getDouble("volume");
                String keybind = resultSet.getString("keybind");

                // Fetch the associated sample
                SampleDAO sampleDAO = new SampleDAO();
                Sample sample = sampleDAO.get(sampleId);

                Pad pad = new Pad(sample);
                pad.setPadId(padId);
                pad.setVolume(volume);
                pad.setKeybind(keybind);
                pads.add(pad);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pads;
    }
}

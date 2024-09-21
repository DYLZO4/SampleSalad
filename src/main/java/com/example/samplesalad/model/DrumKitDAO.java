package com.example.samplesalad.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code DrumKitDAO} class is responsible for performing CRUD operations
 * (Create, Read, Update, Delete) on {@link DrumKit} objects in a database.
 * This class implements the {@link ISampleSaladDAO} interface to provide
 * specific database management for drum kits and their associated pads.
 */
public class DrumKitDAO implements ISampleSaladDAO<DrumKit> {
    private Connection connection;

    /**
     * Constructor for {@code DrumKitDAO} class.
     * Initializes the connection and creates the drumkits and pads tables if they don't exist.
     *
     * @param connection the SQL database connection to be used for the DAO operations
     */
    public DrumKitDAO(Connection connection) {
        this.connection = connection;
        createTable();
    }

    /**
     * Creates the drumkits and pads tables in the database if they do not exist.
     */
    private void createTable() {
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS drumkits ("
                    + "kitID INT AUTO_INCREMENT PRIMARY KEY, "
                    + "kitName VARCHAR(255) NOT NULL)";
            statement.execute(query);

            String padTableQuery = "CREATE TABLE IF NOT EXISTS pads ("
                    + "padID INT AUTO_INCREMENT PRIMARY KEY, "
                    + "kitID INT, "
                    + "sampleID INT, "
                    + "volume DOUBLE, "
                    + "keybind VARCHAR(255), "
                    + "FOREIGN KEY (kitID) REFERENCES drumkits(kitID), "
                    + "FOREIGN KEY (sampleID) REFERENCES samples(sampleID))";
            statement.execute(padTableQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a new {@code DrumKit} to the drumkits table in the database and its pads to the pads table.
     *
     * @param drumKit the {@code DrumKit} to be added to the database
     */
    @Override
    public void add(DrumKit drumKit) {
        String query = "INSERT INTO drumkits (kitName) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, drumKit.getKitName());
            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int kitID = generatedKeys.getInt(1);
                        // Now, insert each pad in the kit
                        for (Pad pad : drumKit.getPads()) {
                            addPad(pad, kitID);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a new {@code Pad} to the pads table, associating it with a drumkit.
     *
     * @param pad the {@code Pad} to be added
     * @param kitID the ID of the drumkit to which this pad belongs
     */
    private void addPad(Pad pad, int kitID) {
        String query = "INSERT INTO pads (kitID, sampleID, volume, keybind) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, kitID);
            stmt.setInt(2, pad.getSample().getSampleID());
            stmt.setDouble(3, pad.getVolume());
            stmt.setString(4, pad.getKeybind());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates an existing {@code DrumKit} and its associated pads in the database.
     *
     * @param drumKit the {@code DrumKit} to be updated in the database
     */
    @Override
    public void update(DrumKit drumKit) {
        String query = "UPDATE drumkits SET kitName = ? WHERE kitID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, drumKit.getKitName());
            stmt.setInt(2, getKitIDByName(drumKit.getKitName()));
            stmt.executeUpdate();

            for (Pad pad : drumKit.getPads()) {
                updatePad(pad, getKitIDByName(drumKit.getKitName()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates a {@code Pad} in the pads table.
     *
     * @param pad the {@code Pad} to be updated
     * @param kitID the ID of the drumkit associated with the pad
     */
    private void updatePad(Pad pad, int kitID) {
        String query = "UPDATE pads SET sampleID = ?, volume = ?, keybind = ? WHERE padID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, pad.getSample().getSampleID());
            stmt.setDouble(2, pad.getVolume());
            stmt.setString(3, pad.getKeybind());
            stmt.setInt(4, pad.getPadId());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a {@code DrumKit} and its associated pads from the database.
     *
     * @param drumKit the {@code DrumKit} to be deleted from the database
     */
    @Override
    public void delete(DrumKit drumKit) {
        String query = "DELETE FROM drumkits WHERE kitID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            int kitID = getKitIDByName(drumKit.getKitName());
            stmt.setInt(1, kitID);
            stmt.executeUpdate();

            // Delete pads associated with this kit
            deletePadsByKitID(kitID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes all pads associated with a specific drumkit from the pads table.
     *
     * @param kitID the ID of the drumkit whose pads are to be deleted
     */
    private void deletePadsByKitID(int kitID) {
        String query = "DELETE FROM pads WHERE kitID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, kitID);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves a {@code DrumKit} from the database based on the given kit ID.
     *
     * @param kitID the ID of the drumkit to retrieve
     * @return the {@code DrumKit} object with the specified ID, or {@code null} if not found
     */
    @Override
    public DrumKit get(int kitID) {
        DrumKit drumKit = null;
        String query = "SELECT * FROM drumkits WHERE kitID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, kitID);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                String kitName = resultSet.getString("kitName");
                drumKit = new DrumKit(kitName);

                // Get pads associated with this kit
                List<Pad> pads = getPadsByKitID(kitID);
                for (Pad pad : pads) {
                    drumKit.addPad(pad.getSample());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return drumKit;
    }

    /**
     * Retrieves all pads associated with a specific drumkit.
     *
     * @param kitID the ID of the drumkit whose pads are to be retrieved
     * @return a {@code List} of {@code Pad} objects associated with the drumkit
     */
    private List<Pad> getPadsByKitID(int kitID) {
        List<Pad> pads = new ArrayList<>();
        String query = "SELECT * FROM pads WHERE kitID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, kitID);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                int padID = resultSet.getInt("padID");
                int sampleID = resultSet.getInt("sampleID");
                double volume = resultSet.getDouble("volume");
                String keybind = resultSet.getString("keybind");

                // Fetch the sample object for this pad
                SampleDAO sampleDAO = new SampleDAO();
                Sample sample = sampleDAO.get(sampleID);

                Pad pad = new Pad(sample);
                pad.setPadId(padID);
                pad.setVolume(volume);
                pad.setKeybind(keybind);

                pads.add(pad);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pads;
    }

    /**
     * Retrieves the kit ID for a drumkit based on its name.
     *
     * @param kitName the name of the drumkit
     * @return the ID of the drumkit with the specified name, or -1 if not found
     */
    private int getKitIDByName(String kitName) {
        String query = "SELECT kitID FROM drumkits WHERE kitName = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, kitName);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("kitID");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1; // Return an invalid ID if not found
    }

    /**
     * Retrieves all {@code DrumKit} objects from the drumkits table in the database.
     *
     * @return a {@code List} of all {@code DrumKit} objects in the database
     */
    @Override
    public List<DrumKit> getAll() {
        List<DrumKit> drumKits = new ArrayList<>();
        String query = "SELECT * FROM drumkits";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet resultSet = stmt.executeQuery()) {

            while (resultSet.next()) {
                int kitID = resultSet.getInt("kitID");
                String kitName = resultSet.getString("kitName");

                DrumKit drumKit = new DrumKit(kitName);

                // Get the pads for this drum kit
                List<Pad> pads = getPadsByKitID(kitID);
                for (Pad pad : pads) {
                    drumKit.addPad(pad.getSample());
                }

                drumKits.add(drumKit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return drumKits;
    }
}

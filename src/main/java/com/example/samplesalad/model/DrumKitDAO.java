package com.example.samplesalad.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DrumKitDAO implements ISampleSaladDAO<DrumKit> {
    private Connection connection;

    public DrumKitDAO(Connection connection) {
        this.connection = connection;
        createTable();
    }

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

    private void deletePadsByKitID(int kitID) {
        String query = "DELETE FROM pads WHERE kitID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, kitID);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
                SampleDAO sampleDAO = new SampleDAO(connection);
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
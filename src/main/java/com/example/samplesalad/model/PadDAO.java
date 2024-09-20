package com.example.samplesalad.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PadDAO implements ISampleSaladDAO<Pad>{
    private Connection connection;

    public PadDAO(Connection connection) {
        this.connection = connection;
        createTable();
    }

    private void createTable() {
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS pads ("
                    + "padId INT AUTO_INCREMENT PRIMARY KEY, "
                    + "sampleId INT, "
                    + "volume DOUBLE, "
                    + "keybind VARCHAR(50), "
                    + "FOREIGN KEY (sampleId) REFERENCES samples(sampleID)"
                    + ")";
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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
                SampleDAO sampleDAO = new SampleDAO(connection);
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
                SampleDAO sampleDAO = new SampleDAO(connection);
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

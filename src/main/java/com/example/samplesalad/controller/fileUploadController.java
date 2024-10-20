package com.example.samplesalad.controller;

import com.example.samplesalad.model.AudioClip;
import com.example.samplesalad.model.Sample;
import com.example.samplesalad.model.DAO.SampleDAO;
import com.example.samplesalad.model.service.DurationDetector;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.Optional;

/**
 * Controller class for managing the sample upload pop-up, allowing users
 * to add new music to the application.  Handles file selection, BPM
 * detection, genre selection, and adding samples to the database.
 */
public class fileUploadController {

    private SampleDAO sampleDAO;
    private Connection connection;

    public Spinner<Integer> BPM; // BPM input spinner
    public Button Tap;  // Button for tapping BPM
    public Button Preview; // Button to preview the selected sample
    private long[] tapTimes = new long[4]; // Stores tap times for BPM calculation
    private int tapCount = 0; // Counts the number of taps

    public ChoiceBox<String> genreChoiceBox; // ChoiceBox for selecting genre
    public TextField sampleName; // TextField for sample name
    public TextField sampleArtist; // TextField for sample artist
    public TextField startTime;  //  TextField for sample start time (currently unused)
    public TextField endTime;    // TextField for sample end time (currently unused)

    private AudioClip clip; // AudioClip for previewing the sample

    Stage stage;

    private String filePath; // Path of the selected audio file

    public fileUploadController() {
    }

    /**
     * Initializes the controller. Sets up the BPM spinner, tap button listener, preview button listener,
     * and the genre choice box listener for adding custom genres.
     */
    public void initialize() {
        sampleDAO = new SampleDAO();
        BPM.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 120)); // Default BPM 120
        Tap.setOnMouseClicked(this::tapBPM);
        Preview.setOnMouseClicked(this::previewSample);
        genreChoiceBox.getSelectionModel().selectedItemProperty().addListener((observableValue, previousVal, newVal) -> {
            if ("Add genre".equals(newVal)) {
                addCustomGenre();
            }
        });
    }

    /**
     * Previews the selected sample.
     * Loads and plays the audio file associated with the selected `filePath`.
     *
     * @param mouseEvent The mouse event triggering the preview.
     */
    private void previewSample(MouseEvent mouseEvent) {
        if (filePath != null) {
            stopPlayback();
            try {
                clip = new AudioClip(filePath);
                clip.loadFile();
                clip.playAudio();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                System.err.println("Error playing audio: " + e.getMessage());
                // Handle the error appropriately (e.g., display an error message to the user)
            }
        }
    }

    /**
     * Stops any currently playing audio preview.
     */
    private void stopPlayback() {
        try {
            if (clip != null) {
                clip.closeStream();
            }
        } catch (IOException ignored) {
        }
    }


    /**
     * Calculates the BPM based on user taps.
     * Records four tap times and calculates the BPM based on the average duration between taps.
     *
     * @param mouseEvent The mouse event triggering the BPM tap.
     */
    private void tapBPM(MouseEvent mouseEvent) {
        long currentTime = System.nanoTime();
        tapTimes[tapCount] = currentTime;
        tapCount++;

        if (tapCount == 4) {
            // Calculate BPM
            long totalTimeNanos = tapTimes[3] - tapTimes[0];
            double averageBeatDurationMillis = (double) totalTimeNanos / 3 / 1_000_000.0; // 3 intervals between 4 taps
            int bpm = (int) Math.round(60_000.0 / averageBeatDurationMillis);

            BPM.getValueFactory().setValue(bpm);

            tapCount = 0; // Reset for next BPM tap sequence
        }
    }

    /**
     * Allows the user to add a custom genre to the `genreChoiceBox`.
     * Opens a dialog to input the new genre and adds it to the choice box.
     * The added genre is not persistent across sessions (database integration needed).
     */
    private void addCustomGenre() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Your Own Genre");
        dialog.setHeaderText("Enter your custom genre:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(customGenre -> {
            // TODO: Connect with the database to make the new genre persistent.
            genreChoiceBox.getItems().add(customGenre);
            genreChoiceBox.getSelectionModel().select(customGenre);
        });
    }

    /**
     * Opens a file chooser dialog to allow the user to select an audio file.
     * Sets the `filePath` to the path of the selected file.
     *
     * @param mouseEvent The mouse event triggering the file selection.
     */
    public void openFileExplorer(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();

        // Set extension filters for supported audio files
        fileChooser.getExtensionFilters().clear();  // Clear any existing filters to avoid duplicates
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Supported Audio Files", "*.mp3", "*.mp4", "*.wav"),
                new FileChooser.ExtensionFilter("MP3 Files", "*.mp3"),
                new FileChooser.ExtensionFilter("MP4 Files", "*.mp4"),
                new FileChooser.ExtensionFilter("WAV Files", "*.wav"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        fileChooser.setTitle("Select music");


        Stage fileStage = (Stage) ((Button) mouseEvent.getSource()).getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(fileStage);


        if (selectedFile != null) {
            filePath = selectedFile.getAbsolutePath();
            System.out.println("Selected file: " + filePath);
        } else {
            System.out.println("File selection was cancelled.");
        }
    }

    /**
     * Adds the selected sample to the database.
     * Creates a new `Sample` object with the provided details and adds it using the `SampleDAO`.
     * Closes the upload popup after adding the sample.
     *
     * @param mouseEvent The mouse event triggered by clicking the "Add music" button.
     * @throws UnsupportedAudioFileException If the audio file format is not supported.
     * @throws IOException                  If an I/O error occurs during file processing.
     */
    public void addSampleToDB(MouseEvent mouseEvent) throws UnsupportedAudioFileException, IOException {
        Sample sample = new Sample(filePath, sampleName.getText(), sampleArtist.getText(), genreChoiceBox.getValue(), BPM.getValue(), DurationDetector.getAudioDurationInSeconds(filePath));
        ((Stage) (((Button) mouseEvent.getSource()).getScene().getWindow())).close();
        sampleDAO.add(sample);
        stopPlayback(); // Stop any preview playback
    }
}

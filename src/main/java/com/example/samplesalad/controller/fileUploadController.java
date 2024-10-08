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
 * Controller class for managing the pop-up that allows users
 * to upload new music to use in the app.
 */
public class fileUploadController {

    private SampleDAO sampleDAO;
    private Connection connection;

    public Spinner<Integer> BPM; // Make sure this is correctly linked to your FXML
    public Button Tap;
    public Button Preview;
    private long[] tapTimes = new long[4];
    private int tapCount = 0;

    public ChoiceBox <String>genreChoiceBox;
    public TextField sampleName;
    public TextField sampleArtist;
    public TextField startTime;
    public TextField endTime;

    private AudioClip clip;

    Stage stage;
    FileChooser.ExtensionFilter mp3 = new FileChooser.ExtensionFilter("mp3 Files", "*.mp3");
    FileChooser.ExtensionFilter mp4 = new FileChooser.ExtensionFilter("mp4 Files", "*.mp4");
    FileChooser.ExtensionFilter wav = new FileChooser.ExtensionFilter("wav Files", "*.wav");
    FileChooser.ExtensionFilter allFiles = new FileChooser.ExtensionFilter("All Files", "*.*");

    private String filePath;
    public fileUploadController(){}

    /**
     * Initialises fileUploadController by adding a listened to the genre drop down menu.
     * This allows users to add new genres when they select the "Add genre" option.
     */
    public void initialize()  {
        sampleDAO = new SampleDAO();
        BPM.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 120)); // Default BPM
        Tap.setOnMouseClicked(this::tapBPM);
        Preview.setOnMouseClicked(this::previewSample);
        genreChoiceBox.getSelectionModel().selectedItemProperty().addListener((observableValue, previousVal, newVal) -> {
            if ("Add genre".equals(newVal)){
                addCustomGenre();
            }
        });
    }

    private void previewSample(MouseEvent mouseEvent)  {
        if (filePath!=null) {
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

    private void stopPlayback() {
        try {
            if (clip!=null){clip.closeStream();}
        } catch (IOException ignored){}
    }

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
     * This function handles the implementation behind adding a genre
     */
    private void addCustomGenre(){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Your Own Genre");
        dialog.setHeaderText("Enter your custom genre:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(customGenre -> {
            // TODO: connect with database so the data is persistent
            genreChoiceBox.getItems().add(customGenre);
            genreChoiceBox.getSelectionModel().select(customGenre);
        });
    }

    /**
     * This function opens file explorer so the user can search for audio files on their computer
     * @param mouseEvent the event triggered by clicking the button for uploading files
     */
    public void openFileExplorer(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(mp3, mp4, wav, allFiles);
        fileChooser.setTitle("Select music");

        File selectedFile = fileChooser.showOpenDialog(stage);
        filePath = selectedFile.getAbsolutePath();
    }

    /**
     * This function handles adding the new sample to the database when the "Add music" button is pressed
     * The pop-up will close and the new song will be in the database
     * @param mouseEvent the event triggered by clicking the button for adding samples
     */
    public void addSampleToDB(MouseEvent mouseEvent) throws UnsupportedAudioFileException, IOException {
        Sample sample = new Sample(filePath, sampleName.getText(), sampleArtist.getText(), genreChoiceBox.getValue(), BPM.getValue(), DurationDetector.getAudioDurationInSeconds(filePath));
        ((Stage)(((Button)mouseEvent.getSource()).getScene().getWindow())).close();
        sampleDAO.add(sample);
        stopPlayback();
    }
}

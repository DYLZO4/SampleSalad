package com.example.samplesalad.controller;

import com.example.samplesalad.model.DatabaseConnection;
import com.example.samplesalad.model.Sample;
import com.example.samplesalad.model.SampleDAO;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.sql.Connection;
import java.util.Optional;

/**
 * Controller class for managing the pop-up that allows users
 * to upload new music to use in the app.
 */
public class fileUploadController {
    private SampleDAO sampleDAO;
    private Connection connection;

    public ChoiceBox <String>genreChoiceBox;
    public TextField sampleName;
    public TextField sampleArtist;

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
    public void initialize(){
        sampleDAO = new SampleDAO();
        genreChoiceBox.getSelectionModel().selectedItemProperty().addListener((observableValue, previousVal, newVal) -> {
            if ("Add genre".equals(newVal)){
                addCustomGenre();
            }
        });
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
    public void addSampleToDB(MouseEvent mouseEvent) {
        Sample sample = new Sample(1, filePath, sampleName.getText(), sampleArtist.getText(), genreChoiceBox.getValue());
        ((Stage)(((Button)mouseEvent.getSource()).getScene().getWindow())).close();
        sampleDAO.add(sample);
    }
}

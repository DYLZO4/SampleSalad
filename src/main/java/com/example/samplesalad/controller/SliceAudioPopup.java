package com.example.samplesalad.controller;

import com.example.samplesalad.model.DAO.SampleDAO;
import com.example.samplesalad.model.Pad;
import com.example.samplesalad.model.Sample;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

/**
 * The SliceAudioPopup class is responsible for handling the popup that allows users to select which range of audio will be used when pressing a pad.
 */
public class SliceAudioPopup {
    public TextField newFileName;
    public ImageView playIcon;
    public ImageView pauseIcon;
    @FXML
    private Canvas rangeCanvas;

    @FXML
    private Label lowerValueLabel;

    @FXML
    private Label upperValueLabel;

    private int minValue = 0;
    private int maxValue = 100;
    private int lowerValue = 20;
    private int upperValue = 80;

    private Sample currentSample;
    private SampleDAO sampleDAO;
    private Pad currentPad;
    private MainController mainController;

    /**
     * sets the sample associated with the selected pad to edit in this menu
     * @param selectedSample the sample associated with the selected pad
     */
    public void setCurrentSample(Sample selectedSample){ currentSample = selectedSample;
        if (currentSample != null) {
            // Initialize slider values based on the sample's duration
            double sampleDuration = currentSample.getDuration(); // Duration in seconds
            minValue = 0; // Minimum value (0 seconds)
            maxValue = (int) (sampleDuration * 1000); // Maximum value (duration in milliseconds)
            lowerValue = 20; // Start value (0%)
            upperValue = 80; // End value (100%)
            drawSlider();
            updateLabels();
        }
    }

    /**
     * sets the current pad that the user has selected for this menu to edit
     * @param selectedPad the current pad selected by the user
     */
    public void setCurrentPad(Pad selectedPad){ currentPad = selectedPad; }

    /**
     * sets the current instance of the {@code MainController} class associated with the program
     * @param controller the current instance of the {@code MainController} class
     */
    public void setMainController(MainController controller){ mainController = controller; }


    /**
     * Initialises the SliceAudioPopup class.
     * Sets up the UI and loads the associated audio clip with the pad.
     * @throws UnsupportedAudioFileException if the stored audio format is unsupported
     * @throws LineUnavailableException if the system cannot open the audio line.
     * @throws IOException if an I/O error occurs while loading the file.
     */
    @FXML
    public void initialize() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        drawSlider();
        updateLabels();
        //currentPad.getAudioClip().loadFile();
        sampleDAO = new SampleDAO();

        rangeCanvas.setOnMousePressed(this::handleMouse);
        rangeCanvas.setOnMouseDragged(this::handleMouse);
    }

    /**
     * Handles logic behind dragging the thumbs on the range slider.
     * @param event the mouse event triggered by clicking a thumb.
     */
    private void handleMouse(MouseEvent event) {
        int mouseX = (int) event.getX();
        int newValue = (int) ((double) mouseX / rangeCanvas.getWidth() * 100);

        if (event.isPrimaryButtonDown()) {
            if (Math.abs(mouseX - getThumbLocation(lowerValue)) < 10) {
                if (Math.min(newValue, upperValue - 1) >= 0){
                    lowerValue = Math.min(newValue, upperValue - 1);
                }
            } else if (Math.abs(mouseX - getThumbLocation(upperValue)) < 10) {
                if (Math.max(newValue, lowerValue + 1) <= 100) {
                    upperValue = Math.max(newValue, lowerValue + 1);
                }
            }
        }

        drawSlider();
        updateLabels();
    }

    /**
     * Handles the logic for redrawing the slider when the user drags a thumb across the slider.
     */
    private void drawSlider() {

        GraphicsContext gc = rangeCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, rangeCanvas.getWidth(), rangeCanvas.getHeight());

        gc.setFill(javafx.scene.paint.Color.LIGHTGRAY);
        gc.fillRect(0, 25, rangeCanvas.getWidth(), 10);

        int lowerX = getThumbLocation(lowerValue);
        int upperX = getThumbLocation(upperValue);
        gc.setFill(Color.DARKGRAY);
        gc.fillRect(lowerX, 25, upperX - lowerX, 10);

        gc.setFill(Color.DIMGRAY);
        gc.fillOval(lowerX - 5, 20, 20, 20);
        gc.fillOval(upperX - 5, 20, 20, 20);
//        xPoints = new double[]{lowerValue, lowerValue + 10, lowerValue - 10};
//        gc.fillPolygon(xPoints, yPoints, nPoints);
    }

    /**
     * Returns the location of the slider thumb.
     * @param value the position of the slider.
     * @return int value of where the thumb lies on the canvas.
     */
    private int getThumbLocation(int value) {
        return (int) ((double) value / 100 * rangeCanvas.getWidth());
    }

    /**
     * Updates labels which show what duration  has been selected using the range slider.
     */
    private void updateLabels() {
        if (currentSample == null){
            lowerValueLabel.setText("Lower Value: " + lowerValue);
            upperValueLabel.setText("Upper Value: " + upperValue);
            System.out.println("current pad is null");
        } else {
            double sampleDuration = currentSample.getDuration();
            lowerValueLabel.setText("Lower Value: " + (lowerValue*sampleDuration/100) + ":" + (lowerValue*sampleDuration/100));
            upperValueLabel.setText("Upper Value: " + (upperValue*sampleDuration/100) + ":" + (upperValue*sampleDuration/100));
            System.out.println("current pad is NOT null");
        }
    }

    /**
     * Creates a new sample based off what the user selected for their sample.
     * @param mouseEvent The mouse event triggered by clicking the confirm button.
     */
    public void onConfirmButtonClicked(MouseEvent mouseEvent) {
        if (currentSample != null){
            double sampleDuration = currentSample.getDuration();
            int startTime = (int) (lowerValue* sampleDuration*10);
            int endTime = (int) (upperValue*sampleDuration*10);
            System.out.println(startTime);
            System.out.println(endTime);
            Sample newSample = new Sample(currentSample.getFilePath(), newFileName.getText(), currentSample.getSampleArtist(), currentSample.getSampleGenre(), currentSample.getPitch(), currentSample.getBPM(),startTime, endTime);
            mainController.setAssignedSampleValue(newSample);
            currentPad.setSample(newSample);
            sampleDAO.add(newSample);
        } else {
            mainController.setAssignedSampleValue(new Sample("home/", newFileName.getText(), "me", "the cool one", lowerValue, upperValue));
        }

        ((Stage)(((Button)mouseEvent.getSource()).getScene().getWindow())).close();
    }

    /**
     * Handles the use of the pause/play button which switches images when clicked.
     * Allows the user to play the audio.
     * @param mouseEvent The mouse event triggered by clicking the pause/play button.
     * @throws IOException if an I/O error occurs while playing the audio or closing the stream.
     * @throws UnsupportedAudioFileException if the stored audio format is unsupported.
     * @throws LineUnavailableException if the system cannot open to audio line.
     */
    public void togglePausePlay(MouseEvent mouseEvent) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
//        currentPad.getAudioClip().loadFile();
        if (playIcon.getImage().getUrl().contains("play.png")){
            // start playing music
            playIcon.setImage(new Image(String.valueOf(getClass().getResource("/com/example/samplesalad/images/pause.png"))));
            currentPad.getAudioClip().playAudio();
        } else if (!playIcon.getImage().getUrl().contains("play.png")){
            // stop playing music
            playIcon.setImage(new Image(String.valueOf(getClass().getResource("/com/example/samplesalad/images/play.png"))));

            currentPad.getAudioClip().closeStream();
        }
    }
}
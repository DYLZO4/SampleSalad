package com.example.samplesalad.controller;

import com.example.samplesalad.model.AudioClip;
import com.example.samplesalad.model.Pad;
import com.example.samplesalad.model.Sample;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class RangeSliderController {
    public TextField newFileName;
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
    private Pad currentPad;
    private MainController mainController;

    public void setCurrentSample(Sample selectedSample){ currentSample = selectedSample; }
    public void setCurrentPad(Pad selectedPad){ currentPad = selectedPad; }

    public void setMainController(MainController controller){ mainController = controller; }

//    private int durationValue = 60;

//    double[] xPoints = {lowerValue, lowerValue + 10, lowerValue - 10}; // X coordinates of the vertices
//    double[] yPoints = {40, 60, 60}; // Y coordinates of the vertices
//    int nPoints = 3; // Number of points

//    private SampleDAO sampleDAO;

    @FXML
    public void initialize() {
        drawSlider();
        updateLabels();

        rangeCanvas.setOnMousePressed(this::handleMouse);
        rangeCanvas.setOnMouseDragged(this::handleMouse);
    }

    private void handleMouse(MouseEvent event) {
        int mouseX = (int) event.getX();
        int newValue = (int) ((double) mouseX / rangeCanvas.getWidth() * (maxValue - minValue) + minValue);

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

    private void drawSlider() {
        GraphicsContext gc = rangeCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, rangeCanvas.getWidth(), rangeCanvas.getHeight());

        // Draw the track
        gc.setFill(javafx.scene.paint.Color.LIGHTGRAY);
        gc.fillRect(0, 25, rangeCanvas.getWidth(), 10);

        // Draw the selected range
        int lowerX = getThumbLocation(lowerValue);
        int upperX = getThumbLocation(upperValue);
        gc.setFill(Color.DARKGRAY);
        gc.fillRect(lowerX, 25, upperX - lowerX, 10);

        // Draw thumbs
        gc.setFill(Color.DIMGRAY);
        gc.fillOval(lowerX - 5, 20, 20, 20);
        gc.fillOval(upperX - 5, 20, 20, 20);
//        xPoints = new double[]{lowerValue, lowerValue + 10, lowerValue - 10};
//        gc.fillPolygon(xPoints, yPoints, nPoints);
    }

    private int getThumbLocation(int value) {
        return (int) ((double) (value - minValue) / (maxValue - minValue) * rangeCanvas.getWidth());
    }

    private void updateLabels() {
        if (currentSample == null){
            lowerValueLabel.setText("Lower Value: " + lowerValue);
            upperValueLabel.setText("Upper Value: " + upperValue);
            System.out.println("current pad is null");
        } else {
            double sampleDuration = currentSample.getDuration();
            lowerValueLabel.setText("Lower Value: " + (lowerValue*sampleDuration/100)/60 + ":" + (lowerValue*sampleDuration/100)%60);
            upperValueLabel.setText("Upper Value: " + (upperValue*sampleDuration/100)/60 + ":" + (upperValue*sampleDuration/100)%60);
            System.out.println("current pad is NOT null");
        }
    }

    public void onConfirmButtonClicked(MouseEvent mouseEvent) {
        if (currentSample != null){
            double sampleDuration = currentSample.getDuration();
            int startTime = (int) (lowerValue* sampleDuration/100);
            int endTime = (int) (upperValue*sampleDuration/100);
            // Create new audio clip
            currentPad.getAudioClip().copyAudio(currentSample.getSampleName(),newFileName.getText(), startTime, endTime);
            // Show changes on drop down on main page
            mainController.setAssignedSampleValue(new Sample(currentSample.getFilePath(), newFileName.getText(), currentSample.getSampleArtist(), currentSample.getSampleGenre(), startTime, endTime));
        } else {
            mainController.setAssignedSampleValue(new Sample("home/", newFileName.getText(), "me", "the cool one", lowerValue, upperValue));
        }

        ((Stage)(((Button)mouseEvent.getSource()).getScene().getWindow())).close();
    }
}
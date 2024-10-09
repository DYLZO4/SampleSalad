package com.example.samplesalad.controller;

import com.example.samplesalad.model.*;
import com.example.samplesalad.model.DAO.SampleDAO;
import com.example.samplesalad.model.DAO.UserDAO;
import com.example.samplesalad.model.service.UserService;
import com.example.samplesalad.model.user.User;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import javafx.util.StringConverter;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller class for handling user interactions and scene transitions.
 * Implements {@link Initializable} to initialize UI components after they are loaded.
 */
public class MainController implements Initializable {

    private Map<KeyCode, Pad> keyBindings = new HashMap<>();

    @FXML
    private ImageView exit, menu;

    @FXML
    private AnchorPane pane1, pane2, contentPane, editPane;

    @FXML
    private BorderPane bp;

    @FXML
    private RadioButton playSwitch, editSwitch;

    @FXML
    private GridPane gridPane;

    @FXML
    private ChoiceBox<Sample> assignedSample; // Change to ChoiceBox<Sample>

    @FXML
    private Spinner<Double> Pitch;

    @FXML
    private Slider Volume;

    @FXML
    private Spinner<Integer> BPM;

    @FXML
    private Button signInButton, playButton, recordButton;

    private UserDAO userDAO;
    private SampleDAO sampleDAO;
    private UserService userService;
    private UserController userController;

    private Pad selectedPad; // Store the currently selected Pad
    private boolean isRecording = false;

    private boolean isAnimating = false;

    private StringProperty buttonText;
    private EventHandler<MouseEvent> buttonAction;
    private Pattern pattern;

    /**
     * Default constructor for the {@code HelloController} class.
     */
    public MainController() {
        userDAO = new UserDAO();
        sampleDAO = new SampleDAO();
        userService = new UserService(userDAO);
        userController = new UserController(userService);
        buttonText = new SimpleStringProperty("Sign in");
        buttonAction = this::login;
        pattern = new Pattern(16);
    }

    /**
     * Initializes the controller class. Sets up event handlers and transitions.
     * This method is called after the FXML file has been loaded.
     *
     * @param url            The location used to resolve relative paths for the root object, or {@code null} if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or {@code null} if the root object is not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DrumKit drumKit = DrumKit.getInstance();
        keyBindings.put(KeyCode.DIGIT1, drumKit.getPad(0));
        keyBindings.put(KeyCode.DIGIT2, drumKit.getPad(1));
        keyBindings.put(KeyCode.DIGIT3, drumKit.getPad(2));
        keyBindings.put(KeyCode.DIGIT4, drumKit.getPad(3));
        keyBindings.put(KeyCode.Q, drumKit.getPad(4));
        keyBindings.put(KeyCode.W, drumKit.getPad(5));
        keyBindings.put(KeyCode.E, drumKit.getPad(6));
        keyBindings.put(KeyCode.R, drumKit.getPad(7));
        keyBindings.put(KeyCode.A, drumKit.getPad(8));
        keyBindings.put(KeyCode.S, drumKit.getPad(9));
        keyBindings.put(KeyCode.D, drumKit.getPad(10));
        keyBindings.put(KeyCode.F, drumKit.getPad(11));
        keyBindings.put(KeyCode.Z, drumKit.getPad(12));
        keyBindings.put(KeyCode.X, drumKit.getPad(13));
        keyBindings.put(KeyCode.C, drumKit.getPad(14));
        keyBindings.put(KeyCode.V, drumKit.getPad(15));
        // Exit button handler
        exit.setOnMouseClicked(event -> {
            System.exit(0);
        });

        // Set playSwitch as selected by default
        playSwitch.setSelected(true);
        editSwitch.setSelected(false);
        editPane.setVisible(false); // Initially hide editPane


        // Initially set pane1 to be hidden and non-interactive
        pane1.setVisible(false);
        pane1.setMouseTransparent(true);

        Pitch.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 127, 60)); // Example for pitch
        BPM.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(60, 240, 120));

        // Create and play the fade-out transition for pane1
        FadeTransition fadeOutTransition = new FadeTransition(Duration.seconds(0.5), pane1);
        fadeOutTransition.setFromValue(1);
        fadeOutTransition.setToValue(0);
        fadeOutTransition.play();

        // Create and play the slide transition for pane2
        TranslateTransition slideOutTransition = new TranslateTransition(Duration.seconds(0.5), pane2);
        slideOutTransition.setByX(-600);
        slideOutTransition.play();

        // Menu button click event to show pane1
        menu.setOnMouseClicked(event -> {
            if (!isAnimating) { // Check if an animation is currently running
                isAnimating = true; // Set the flag to true

                // Ensure pane1 is visible and interactive
                pane1.setVisible(true);
                pane1.setMouseTransparent(false);

                // Create and play the fade-in transition for pane1
                FadeTransition fadeInTransition = new FadeTransition(Duration.seconds(0.5), pane1);
                fadeInTransition.setFromValue(0);
                fadeInTransition.setToValue(1);

                fadeInTransition.setOnFinished(event1 -> {
                    isAnimating = false; // Reset the flag when the animation finishes
                });
                fadeInTransition.play();

                // Create and play the slide transition for pane2
                TranslateTransition slideInTransition = new TranslateTransition(Duration.seconds(0.5), pane2);
                slideInTransition.setByX(600);
                slideInTransition.play();
            }
        });

        // Pane1 click event to hide it
        pane1.setOnMouseClicked(event -> {
            if (!isAnimating) { // Check if an animation is currently running
                isAnimating = true; // Set the flag to true

                // Create and play the fade-out transition for pane1
                FadeTransition fadeOutTransition1 = new FadeTransition(Duration.seconds(0.5), pane1);
                fadeOutTransition1.setFromValue(1);
                fadeOutTransition1.setToValue(0);

                // Set an action to run after the fade-out transition finishes
                fadeOutTransition1.setOnFinished(event1 -> {
                    pane1.setVisible(false);
                    pane1.setMouseTransparent(true); // Make pane1 non-interactive again
                    isAnimating = false; // Reset the flag when the animation finishes
                });
                fadeOutTransition1.play();

                // Create and play the slide transition for pane2
                TranslateTransition slideOutTransition1 = new TranslateTransition(Duration.seconds(0.5), pane2);
                slideOutTransition1.setByX(-600);
                slideOutTransition1.play();
            }
        });

        // Set up event handlers for radio buttons
        playSwitch.setOnAction(event -> {
            if (playSwitch.isSelected()) {
                editSwitch.setSelected(false);
                editPane.setVisible(false); // Hide editPane when Play is selected
                playSwitch.setDisable(true);
                editSwitch.setDisable(false);
            }
        });

        editSwitch.setOnAction(event -> {
            if (editSwitch.isSelected()) {
                playSwitch.setSelected(false);
                editPane.setVisible(true); // Show editPane when Edit is selected
                playSwitch.setDisable(false);
                editSwitch.setDisable(true);
            } else {
                editPane.setVisible(false); // Hide if deselected
            }
        });

        playButton.setOnMouseClicked(mouseEvent -> {
            try {
                pattern.startPattern();
                pattern.playPattern();

            } catch (UnsupportedAudioFileException e) {
                throw new RuntimeException(e);
            } catch (LineUnavailableException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        recordButton.setOnMouseClicked(mouseEvent -> {
            pattern.startRecordPattern();
            if(!isRecording){
                isRecording = true;
                pattern.startRecordPattern();
            } else {
                isRecording=false;
                pattern.endRecordPattern();
            }
        });

        signInButton.textProperty().bind(buttonText);
        signInButton.setOnMouseClicked(event -> buttonAction.handle(event));

        if (userController.isUserLoggedIn()) {
            buttonText.set("Account");
            buttonAction = this::account;

            List<Sample> samples = sampleDAO.getSamplesByUserId(userController.getLoggedInUser());
            for (Sample sample : samples) {
                assignedSample.getItems().add(sample);
            }

            assignedSample.setConverter(new StringConverter<>() {
                @Override
                public String toString(Sample sample) {
                    return sample != null ? sample.getSampleName() : null; // Display sample name
                }

                @Override
                public Sample fromString(String string) {
                    return null;
                }
            });


        }
        //listener on sample selection to fetch properties
        assignedSample.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Sample>() {
            @Override
            public void changed(ObservableValue<? extends Sample> observable, Sample oldValue, Sample newValue) {
                if (newValue != null) {
                    // Update the BPM Spinner with the new sample's BPM
                    BPM.getValueFactory().setValue((int)newValue.getBPM());
                    Pitch.getValueFactory().setValue(newValue.getPitch());
                    //TODO: volume stuff
                    try {
                        selectedPad.setAudioClip(new AudioClip(newValue.getFilePath()));

                    } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
                        throw new RuntimeException(e);
                    }
                }


            }


        });

        for (Node node : gridPane.getChildren()) {
            if (node instanceof Button) {
                Button padButton = (Button) node;
                padButton.setOnAction(event -> handlePadClick(padButton));
            }
        }

        gridPane.setOnKeyPressed(event -> handleKeyPress(event.getCode()));


    }

    private void handleKeyPress(KeyCode keyCode) {
        if (playSwitch.isSelected()) { // Only in play mode
            Pad pad = keyBindings.get(keyCode);
            if (pad != null) {
                Button padButton = getButtonFromPad(pad); // Get the corresponding button for the pad
                if (padButton != null) {
                    playAudio(pad);

                    // Apply the glow effect using the CSS class
                    String glowClass = getGlowClassForPad(pad);
                    padButton.getStyleClass().add(glowClass); // Add the glow class

                    // Remove the glow class after a short duration
                    PauseTransition pause = new PauseTransition(Duration.seconds(0.3));
                    pause.setOnFinished(event -> padButton.getStyleClass().remove(glowClass)); // Remove the glow effect
                    pause.play();
                }
            }
        }
    }

    private String getGlowClassForPad(Pad pad) {
        // Get the button associated with the pad
        Button padButton = getButtonFromPad(pad);
        if (padButton != null) {
            // Retrieve the row index of the button in the grid
            Integer rowIndex = GridPane.getRowIndex(padButton);

            // Return the glow class based on the row index
            if (rowIndex != null) {
                return switch (rowIndex) {
                    case 0 -> "row0-glow";
                    case 1 -> "row1-glow";
                    case 2 -> "row2-glow";
                    case 3 -> "row3-glow";
                    default -> ""; // No glow for rows beyond the defined range
                };
            }
        }
        return ""; // Return no glow if the padButton is not found or rowIndex is null
    }

    private void handlePadClick(Button padButton) {
        if (editSwitch.isSelected()) {
            selectedPad = getPadFromButton(padButton);

            if (selectedPad != null) {
                // Display the pad's properties in the edit pane UI elements
                assignedSample.setValue(selectedPad.getSample());
                BPM.getValueFactory().setValue(selectedPad.getBPM());
                Pitch.getValueFactory().setValue(selectedPad.getPitch());
                //TODO: add volume
            }
        } else if (playSwitch.isSelected()) {
            Pad pad = getPadFromButton(padButton);
            playAudio(pad);
        }
    }

    private Button getButtonFromPad(Pad pad) {
        for (Node node : gridPane.getChildren()) {
            if (node instanceof Button) {
                Button padButton = (Button) node;
                Pad mappedPad = getPadFromButton(padButton);
                if (mappedPad != null && mappedPad.equals(pad)) {
                    return padButton; // Return the button associated with the given pad
                }
            }
        }
        return null; // If no matching button found
    }

    private Pad getPadFromButton(Button padButton) {
        int rowIndex = GridPane.getRowIndex(padButton);
        int columnIndex = GridPane.getColumnIndex(padButton);

        // Assuming your DrumKit instance is accessible (e.g., through a Singleton)
        DrumKit drumKit = DrumKit.getInstance();
        return drumKit.getPad(rowIndex * gridPane.getColumnCount() + columnIndex);
    }

    /**
         * Handles the settings button click event. Loads settings page
         * @param event The mouse event triggered by clicking the settings button
         */
    @FXML
    private void openSettings(MouseEvent event){
        loadPage("settings");
    }

    /**
     * Handles the login button click event. Loads the login page.
     *
     * @param event The mouse event triggered by clicking the login button.
     */
    @FXML
    private void login(MouseEvent event) {
        loadPage("login");
    }

    /**
     * Handles the register button click event. Loads the registration page.
     *
     * @param event The mouse event triggered by clicking the register button.
     */
    @FXML
    private void account(MouseEvent event) {
        loadPage("account");
    }

    /**
     * Handles the condensed library button click event. Loads the condensed library page.
     *
     * @param event The mouse event triggered by clicking the condensed library button.
     */
    @FXML
    private void openLibrary(MouseEvent event) {
        loadPage("library-expanded");
    }

    /**
     * Handles the go to main button click event. Loads the main page.
     *
     * @param event The mouse event triggered by clicking the go to main button.
     */
    @FXML
    private void goToMain(MouseEvent event) {
        loadPage("main-view");
    }

    /**
     * Loads the specified FXML page and sets it as the content of the main UI elements.
     *
     * @param page The name of the FXML file to load, excluding the ".fxml" extension.
     */
    public void loadPage(String page) {
        try {
            URL fxmlLocation = getClass().getResource("/com/example/samplesalad/" + page + ".fxml");
            if (fxmlLocation == null) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, "FXML file not found: " + page + ".fxml");
                return;
            }
            Logger.getLogger(MainController.class.getName()).log(Level.INFO, "Loading FXML file: " + fxmlLocation.toString());
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Parent root = loader.load();
            if (page.equals("main-view")) {
                bp.setCenter(root);
            } else {
                contentPane.getChildren().setAll(root);
            }

            editPane.setVisible(false);
            playSwitch.setSelected(true); // Set playSwitch as selected
            editSwitch.setSelected(false);

        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void register(MouseEvent mouseEvent) {

    }

    @FXML
    private void applyPadChanges() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        if (selectedPad != null) {
            // Get the updated properties from the edit pane UI elements
            Sample newSample = assignedSample.getValue();
            // newVolume = Volume.getValue();
            int newBPM = BPM.getValue();
            double newPitch = BPM.getValue();

            // Apply the changes to the selectedPad
            selectedPad.setSample(newSample);
            //selectedPad.setVolume(newVolume);
            selectedPad.setBPM(newBPM);
            selectedPad.setPitch(newPitch);
            selectedPad.setAudioClip(new AudioClip(newSample.getFilePath()));
            // ... apply other properties as needed
        }
    }

    private void playAudio(Pad pad){
            if (pad != null) {
                if (pad.getAudioClip() != null) {
                    try {
                        pad.getAudioClip().loadFile();
                        pad.getAudioClip().playAudio();
                        if (isRecording){
                            pattern.addPadEvent(new PadEvent(pad));
                            System.out.println(pattern.getPadEvents());
                        }
                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                        // ... (error handling)
                    }
                }
            }
    }
}
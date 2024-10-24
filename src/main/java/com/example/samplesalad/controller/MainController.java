package com.example.samplesalad.controller;

import com.example.samplesalad.model.*;
import com.example.samplesalad.model.DAO.KeyBindingDAO;
import com.example.samplesalad.model.DAO.SampleDAO;
import com.example.samplesalad.model.DAO.UserDAO;
import com.example.samplesalad.model.service.UserService;
import com.example.samplesalad.model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.StringConverter;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main controller for the application, handling user interactions, scene transitions,
 * audio playback, and pad/sample management.  Implements `Initializable` to set
 * up UI components and event handlers after the FXML file is loaded.
 */
public class MainController implements Initializable {

    public Text warningMessage;


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
    private Spinner<Integer> BPM,metroBPM, patternLength;

    @FXML
    private Button signInButton, playButton, recordButton, metroStart;

    private UserDAO userDAO;
    private SampleDAO sampleDAO;
    private UserService userService;
    private UserController userController;

    private Pad selectedPad; // Store the currently selected Pad
    private KeyBindingDAO keyBindingDAO;



    private boolean isAnimating = false;

    private StringProperty buttonText;
    private EventHandler<MouseEvent> buttonAction;
    private Pattern pattern;

    private Metronome metronome;
    private Gson gson = new Gson();

    private int preRecordBeats = 4; // Number of metronome beats before recording



    /**
     * Initializes DAOs, services, controllers, UI properties, and the pattern object.
     */
    public MainController() {
        userDAO = new UserDAO();
        sampleDAO = new SampleDAO();
        keyBindingDAO = new KeyBindingDAO();
        userService = new UserService(userDAO);
        userController = new UserController(userService);
        buttonText = new SimpleStringProperty("Sign in");
        buttonAction = this::login;
        pattern = new Pattern(1, 120);
        metronome = new Metronome();
    }

    /**
     * Initializes the controller after the FXML file is loaded. Sets up key bindings, UI elements,
     * event handlers, animations, and loads user-specific data if logged in.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
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
        User loggedInUser = userController.getLoggedInUser();
        // Exit button handler
        exit.setOnMouseClicked(event -> {
            System.exit(0);
        });

        // Set playSwitch as selected by default
        playSwitch.setSelected(true);
        editSwitch.setSelected(false);
        editPane.setVisible(false); // Initially hide editPane

        pane1.setVisible(true);
        pane1.setMouseTransparent(false);

        pane1.setVisible(false);
        pane1.setMouseTransparent(true);

        Pitch.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 127, 60)); // Example for pitch
        BPM.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(60, 240, 120));
        metroBPM.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(60, 240, 120));
        patternLength.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,4 , 1));
        // Create and play the fade-out transition for pane1
        FadeTransition fadeOutTransition = new FadeTransition(Duration.seconds(0.5), pane1);
        fadeOutTransition.setFromValue(1);
        fadeOutTransition.setToValue(0);
        fadeOutTransition.play();

        // Create and play the slide transition for pane2
        TranslateTransition slideOutTransition = new TranslateTransition(Duration.seconds(0.5), pane2);
        slideOutTransition.setByX(-600);
        slideOutTransition.play();

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
        slideInTransition.setByX(1);
        slideInTransition.play();

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
                warningMessage.setVisible(false);
            } else {
                editPane.setVisible(false); // Hide if deselected
            }
        });

        playButton.setOnMouseClicked(mouseEvent -> {
            if (!pattern.getIsPlaying()){
                pattern.startPattern();
                pattern.playPattern();

            } else{ pattern.stopPattern();}
        });

        metroStart.setOnMouseClicked(mouseEvent -> {
            if (!metronome.isPlaying){
                pattern.setPatternLength(patternLength.getValueFactory().getValue());
                metronome.startMetronome(metroBPM.getValueFactory().getValue());

            } else{ metronome.stop();}
        });


        recordButton.setOnMouseClicked(mouseEvent -> {
            if (!pattern.isRecording()) {
                pattern.setLength(patternLength.getValueFactory().getValue());
                startRecordingWithMetronomeDelay();
            } else {
                pattern.endRecordPattern();
                metronome.stop(); // Stop the metronome when recording stops
            }
        });

        signInButton.textProperty().bind(buttonText);
        signInButton.setOnMouseClicked(event -> buttonAction.handle(event));

        if (userController.isUserLoggedIn()) {
            buttonText.set("Account");
            buttonAction = this::account;

            int userId = loggedInUser.getId();
            String jsonBindings = keyBindingDAO.loadKeyBindings(userId);
            keyBindings = convertJsonToKeyBindings(jsonBindings);
            System.out.println("Key bindings: " + keyBindings);

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

    /**
     * Starts recording a pattern after a delay determined by the metronome's BPM and pre-record beats.
     * The delay allows the user to hear a few metronome clicks before recording begins.
     */
    private void startRecordingWithMetronomeDelay() {
        int bpm = metroBPM.getValueFactory().getValue(); // Get BPM from your UI
        long delay = ((60000 / bpm) * preRecordBeats); // Calculate delay for pre-record beats
        pattern.setBPM(bpm);
        new Thread(() -> {
            try {

                metronome.startMetronome(bpm); // Start the metronome immediately

                Thread.sleep(delay); // Wait for the pre-record beats

                Platform.runLater(() -> { // Ensure pattern recording starts on JavaFX Application Thread
                    pattern.startRecordPattern();
                    pattern.playPattern();
                });

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();


    }

    /**
     * Converts a JSON string to a map of key bindings.
     *
     * @param jsonBindings The JSON string representing the key bindings.
     * @return A map where keys are `KeyCode`s and values are `Pad` objects.
     */
    private Map<KeyCode, Pad> convertJsonToKeyBindings(String jsonBindings) {
        Map<KeyCode, Pad> keyBindings = new HashMap<>();
        if (jsonBindings != null && !jsonBindings.isEmpty()) {
            Type type = new TypeToken<Map<String, Integer>>() {}.getType();
            Map<String, Integer> bindingsMap = gson.fromJson(jsonBindings, type);
            for (Map.Entry<String, Integer> entry : bindingsMap.entrySet()) {
                KeyCode keyCode = KeyCode.valueOf(entry.getKey());
                int padIndex = entry.getValue();
                DrumKit drumKit = DrumKit.getInstance();
                keyBindings.put(keyCode, drumKit.getPad(padIndex));
            }
        }
        return keyBindings;
    }


    /**
     * Handles key presses for triggering pads in play mode.  Plays the assigned sample
     * and applies a visual "glow" effect to the corresponding pad button.
     *
     * @param keyCode The key code of the pressed key.
     */
    private void handleKeyPress(KeyCode keyCode) {
        if (playSwitch.isSelected()) { // Only in play mode
            Pad pad = keyBindings.get(keyCode);
            Button padButton = getButtonFromPad(pad); // Get the corresponding button for the pad

            try {

                pad.getAudioClip().loadFile(); // Load the audio file (if not already loaded)
                playAudio(pad);
                applyGlowEffect(padButton, pad); // Apply the glow effect

            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                System.err.println("Error playing audio: " + e.getMessage());
            }
        }
    }

    /**
     * Handles pad button clicks.  In edit mode, selects the clicked pad and
     * displays its properties.  In play mode, triggers the pad's sample and
     * applies a visual glow effect.
     *
     * @param padButton The button that was clicked.
     */
    private void handlePadClick(Button padButton) {
        if (editSwitch.isSelected()) {
            selectedPad = getPadFromButton(padButton);

            if (selectedPad != null) {
                // Display the pad's properties in the edit pane UI elements
                assignedSample.setValue(selectedPad.getSample());
                BPM.getValueFactory().setValue(selectedPad.getBPM());
                Pitch.getValueFactory().setValue(selectedPad.getPitch());
            }
        } else if (playSwitch.isSelected()) {
            Pad pad = getPadFromButton(padButton);
            padButton = getButtonFromPad(pad); // Get the corresponding button for the pad

            try {
                pad.getAudioClip().loadFile(); // Load the audio file (if not already loaded)
                playAudio(pad);
                applyGlowEffect(padButton, pad); // Apply the glow effect on click
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                System.err.println("Error playing audio: " + e.getMessage());
                // Handle the error appropriately (e.g., display an error message to the user)
            }
        }
    }

    /**
     * Applies a visual glow effect to the given pad button.  The glow effect is
     * implemented using CSS classes and a `PauseTransition` to control the
     * duration of the glow.
     *
     * @param padButton The button to apply the glow effect to.
     * @param pad       The Pad object associated with the button (used to determine the correct glow color/class).
     */
    private void applyGlowEffect(Button padButton, Pad pad) {
        // Apply the glow effect using the CSS class
        String glowClass = getGlowClassForPad(pad);
        padButton.getStyleClass().add(glowClass); // Add the glow class
        padButton.getStyleClass().add("buttonWait");

        // Remove the glow class after a short duration
        PauseTransition pause = new PauseTransition(Duration.seconds(0.2));
        pause.setOnFinished(event -> {
            padButton.getStyleClass().remove("buttonWait");
            padButton.getStyleClass().remove(glowClass);
        }); // Remove the glow effect and active button effect
        pause.play();
    }

    /**
     * Gets the appropriate CSS class name for the glow effect based on the
     * row index of the pad button in the grid.
     *
     * @param pad The Pad object associated with the button.
     * @return The CSS class name for the glow effect.
     */
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

    /**
     * Retrieves the Button associated with a given Pad from the GridPane.
     *
     * @param pad The pad object whose button is needed.
     * @return The Button associated with the Pad, or null if not found.
     */
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

    /**
     * Retrieves the Pad object corresponding to the clicked button in the grid pane.
     *
     * @param padButton The button that was clicked.
     * @return The corresponding `Pad` object, or `null` if the button is not associated with a pad.
     */
    private Pad getPadFromButton(Button padButton) {
        int rowIndex = GridPane.getRowIndex(padButton);
        int columnIndex = GridPane.getColumnIndex(padButton);

        DrumKit drumKit = DrumKit.getInstance();
        return drumKit.getPad(rowIndex * gridPane.getColumnCount() + columnIndex);
    }

    /**
     * Opens the settings page.
     *
     * @param event The mouse event triggered by clicking the settings button.
     */
    @FXML
    private void openSettings(MouseEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/samplesalad/settings.fxml"));
            Parent root = loader.load();
            SettingsController settingsController = loader.getController();
            contentPane.getChildren().setAll(root);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    /**
     * Applies changes made in the edit pane to the currently selected pad.
     * Updates the pad's sample, BPM, and pitch.
     * @throws UnsupportedAudioFileException If the audio file format is not supported.
     * @throws LineUnavailableException If an audio line cannot be opened.
     * @throws IOException If an I/O error occurs.
     */
    @FXML
    private void applyPadChanges() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        if (selectedPad != null) {
            Sample newSample = assignedSample.getValue();
            int newBPM = BPM.getValue();
            double newPitch = BPM.getValue();

            selectedPad.setSample(newSample);
            selectedPad.setBPM(newBPM);
            selectedPad.setPitch(newPitch);
            selectedPad.setAudioClip(new AudioClip(newSample.getFilePath()));
        }
    }


    /**
     * Opens the sample split editing popup, allowing the user to adjust the start and end times
     * of the sample assigned to the selected pad.
     *
     * @param actionEvent The action event that triggers the popup.
     * @throws IOException If there's an error loading the popup FXML file.
     */
    public void editSampleSplit(ActionEvent actionEvent) throws IOException {
        if(assignedSample.getValue() == null){
            warningMessage.setVisible(true);
        } else {
            URL popupURL = getClass().getResource("/com/example/samplesalad/slice-audio-popup.fxml");
            if (popupURL == null) {
                Logger.getLogger(LibraryController.class.getName()).log(Level.SEVERE, "FXML file not found: slice-audio-popup.fxml");
                return;
            }
            Logger.getLogger(LibraryController.class.getName()).log(Level.INFO, "Loading FXML file: " + popupURL.toString());
            FXMLLoader popupLoader = new FXMLLoader(popupURL);

            Scene scene = popupLoader.load();
            SliceAudioPopup controller = popupLoader.getController();
            controller.setMainController(this);

            controller.setCurrentSample(assignedSample.getValue());
            controller.setCurrentPad(selectedPad);


            Stage popup = new Stage();
            popup.setTitle("Edit split from audio");
            popup.setScene(scene);
            popup.initModality(Modality.APPLICATION_MODAL);
            popup.showAndWait();
        }
    }

    /**
     * Sets the value of the assigned sample in the UI.
     *
     * @param sample The sample to assign.
     */
    public void setAssignedSampleValue(Sample sample){
        assignedSample.setValue(sample);
    }

    /**
     * Plays the audio associated with the given Pad. Handles loading the file and
     * playing either the full sample or a specified segment.  Also adds a
     * PadEvent to the current pattern if recording is active.
     *
     * @param pad The Pad whose audio should be played.
     */
    private void playAudio(Pad pad){
        if (pad != null) {
            if (pad.getAudioClip() != null) {
                try {
                    pad.getAudioClip().loadFile();
                    if (pad.getSample().getEndTime() == 0) {
                        pad.getAudioClip().playAudio();

                    } else {
                        pad.getAudioClip().playAudio(pad.getSample().getStartTime(), pad.getSample().getEndTime());

                    }
                    if (pattern.isRecording()){
                        pattern.addPadEvent(new PadEvent(pad, pattern.getStartTime()));
                        System.out.println(pattern.getPadEvents());
                    }

                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                }
            }
        }
    }
}
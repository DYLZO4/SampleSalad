package com.example.samplesalad.controller;

import com.example.samplesalad.model.DAO.KeyBindingDAO;
import com.example.samplesalad.model.DAO.UserDAO;
import com.example.samplesalad.model.Pad;
import com.example.samplesalad.model.User;
import com.example.samplesalad.model.service.UserService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.lang.reflect.Type;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Logger;

/**
 * Controller for the settings view, managing key bindings and other user preferences.
 * Implements {@link Initializable} to initialize components and load saved key bindings.
 */
public class SettingsController implements IController, Initializable {

    @FXML
    private Button keyBind;
    private Button sourceButton;
    private String keyPressed;

    @FXML
    private AnchorPane contentPane;

    @FXML
    private Button padNumber0, padNumber1, padNumber2, padNumber3, padNumber4, padNumber5, padNumber6, padNumber7,
            padNumber8, padNumber9, padNumber10, padNumber11, padNumber12, padNumber13, padNumber14, padNumber15;

    private KeyBindingDAO keyBindingDAO;
    private UserService userService;
    private UserController userController;

    private Gson gson = new Gson();
    private Map<KeyCode, Pad> keyBindings = new HashMap<>();

    /**
     * Initializes the controller. Loads saved key bindings from the database and updates
     * the pad buttons with the corresponding key assignments.  If no saved bindings
     * are found, default bindings are used and saved to the database.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        keyBindingDAO = new KeyBindingDAO();
        userService = new UserService(new UserDAO());
        userController = new UserController(userService);

        User loggedInUser = userController.getLoggedInUser();

        if (loggedInUser != null) {
            String jsonBindings = keyBindingDAO.loadKeyBindings(loggedInUser.getId());
            if (jsonBindings == null) {
                // Default key bindings from FXML
                Map<String, Integer> defaultBindings = new HashMap<>();
                for (int i = 0; i <= 15; i++) {
                    try {
                        Button button = (Button) getClass().getDeclaredField("padNumber" + i).get(this);
                        String text = button.getText();
                        if (text.matches("\\d")) { // Check if the text is a single digit
                            text = "DIGIT" + text;
                        }
                        defaultBindings.put(text, i);
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                keyBindingDAO.insertKeyBindings(loggedInUser.getId(), defaultBindings);
            } else {
                Type type = new TypeToken<Map<String, Integer>>() {}.getType();
                Map<String, Integer> bindings = gson.fromJson(jsonBindings, type);

                // Map of button fx:id to button instances
                Map<Integer, Button> padButtons = new HashMap<>();
                for (int i = 0; i <= 15; i++) {
                    try {
                        Button button = (Button) getClass().getDeclaredField("padNumber" + i).get(this);
                        padButtons.put(i, button);
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }

                // Set button text based on key bindings
                for (Map.Entry<String, Integer> entry : bindings.entrySet()) {
                    String key = entry.getKey();
                    Integer padNumber = entry.getValue();
                    Button button = padButtons.get(padNumber);
                    if (button != null) {
                        button.setText(key);
                    }
                }
            }
        }
    }


    /**
     * Loads the specified FXML page into the content pane.
     *
     * @param page The name of the FXML file to load (without the .fxml extension).
     */
    public void loadPage(String page) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/samplesalad/" + page + ".fxml"));
            Parent root = loader.load();
            AnchorPane newContentPane = (AnchorPane) root.lookup("#contentPane");
            contentPane.getChildren().setAll(newContentPane.getChildren());
        } catch (java.io.IOException ex) {
            Logger.getLogger(SettingsController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    /**
     * Placeholder for loading a popup. Currently not implemented.
     * @param page The page to load
     */
    public void loadPopup(String page) {
        // Implement the logic to load the popup
    }

    /**
     * Opens the key bind popup for the clicked pad button.
     *
     * @param mouseEvent The mouse event triggering the popup.
     */
    public void openPopup(MouseEvent mouseEvent) {
        try {
            sourceButton = (Button) mouseEvent.getSource();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/samplesalad/set-key-bind-popup.fxml"));
            Parent parent = fxmlLoader.load();
            SettingsController popupController = fxmlLoader.getController();
            popupController.setSourceButton(sourceButton);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Set Key Bind");
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the source button that triggered the key bind popup.
     *
     * @param sourceButton The button that triggered the popup.
     */
    public void setSourceButton(Button sourceButton) {
        this.sourceButton = sourceButton;
    }

    /**
     * Captures the key pressed by the user in the key bind popup.
     *
     * @param keyEvent The key event.
     */
    public void setKeyBind(KeyEvent keyEvent) {
        keyPressed = keyEvent.getCharacter().toUpperCase();
        keyBind.setText(keyPressed);
    }

    /**
     * Applies the selected key bind to the source button and closes the popup.
     */
    @FXML
    public void applyKeyBind() {
        if (sourceButton != null && keyPressed != null) {
            sourceButton.setText(keyPressed);
        }
        Stage stage = (Stage) keyBind.getScene().getWindow();
        stage.close();
    }

    /**
     * Converts the current key bindings to a JSON string.
     * This method retrieves the text of each pad button (representing the assigned key),
     * creates a map of pad number to key, converts it to JSON using Gson, and returns the JSON string.
     * Also updates the key bindings in the database for the logged-in user.
     * @return A JSON string representing the current key bindings.
     */
    public String getButtonTextsAsJson() {
        Map<String, String> buttonTexts = new HashMap<>();
        Map<String, Integer> keyBindings = new HashMap<>();
        for (int i = 0; i <= 15; i++) {
            try {
                Button button = (Button) getClass().getDeclaredField("padNumber" + i).get(this);
                String text = button.getText();
                if (text.matches("\\d")) { // Check if the text is a single digit
                    text = "DIGIT" + text;
                }
                buttonTexts.put("padNumber" + i, text);
                keyBindings.put(text, i); // Add to keyBindings map
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        String json = gson.toJson(buttonTexts);
        System.out.println(json); // Print the JSON output

        // Update key bindings in the database
        User loggedInUser = userController.getLoggedInUser();
        if (loggedInUser != null) {
            keyBindingDAO.updateKeyBindings(loggedInUser.getId(), keyBindings);
        }

        return json;
    }

    /**
     * Closes the key bind popup.
     *
     * @param mouseEvent The mouse event triggered by clicking the close button.
     */
    @FXML
    public void closePopup(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Button) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
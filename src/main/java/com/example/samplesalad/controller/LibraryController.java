package com.example.samplesalad.controller;

import com.example.samplesalad.model.DAO.UserDAO;
import com.example.samplesalad.model.Sample;
import com.example.samplesalad.model.DAO.SampleDAO;
import com.example.samplesalad.model.service.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Controller class for managing interactions in the library view.
 * This class handles loading sample data, displaying it in a table,
 * searching (not yet implemented), opening recent samples (not yet implemented),
 * and uploading new songs.
 */
public class LibraryController implements IController, Initializable {

    public AnchorPane contentPane;

    @FXML
    private TableView<Sample> sampleTable;
    @FXML
    private TableColumn<Sample, String> songNameColumn;
    @FXML
    private TableColumn<Sample, Timestamp> dateAddedColumn;
    @FXML
    private TableColumn<Sample, Double> durationColumn;
    @FXML
    private TableColumn<Sample, String> categoryColumn;

    private final ObservableList<Sample> sampleData = FXCollections.observableArrayList();
    private SampleDAO sampleDAO;
    private UserDAO userDAO;
    private UserService userService;
    private UserController userController;

    /**
     * Default constructor for the `LibraryController`.  Initializes DAOs and services.
     */
    public LibraryController() {
        userDAO = new UserDAO();
        userService = new UserService(userDAO);
        userController = new UserController(userService);
    }

    /**
     * Handles the event when text is entered into the search bar.
     * Placeholder implementation - search functionality is not yet implemented.
     *
     * @param actionEvent The action event triggered by entering text.
     */
    @FXML
    public void onTextEntered(ActionEvent actionEvent) {
        // TODO: Implement search functionality through table
    }

    /**
     * Loads the specified FXML page into the content pane.
     *
     * @param page The name of the FXML file to load (without the .fxml extension).
     */
    @Override
    public void loadPage(String page) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/samplesalad/" + page + ".fxml"));
            Parent root = loader.load();
            AnchorPane newContentPane = (AnchorPane) root.lookup("#contentPane");
            contentPane.getChildren().setAll(newContentPane.getChildren());
        } catch (IOException ex) {
            Logger.getLogger(LibraryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the "Recents" button click. Placeholder implementation - functionality for sorting by last opened/added is not yet implemented.
     *
     * @param mouseEvent The mouse event triggered by clicking the "Recents" button.
     */
    public void openRecents(MouseEvent mouseEvent) {
        System.out.println("openRecent called");
        // TODO: Implement view to sort by last opened/added
    }

    /**
     * Opens the file upload pop-up to allow the user to add a new song.
     *
     * @param mouseEvent The mouse event triggered by clicking the upload button.
     * @throws IOException If there is an error loading the FXML file for the pop-up.
     */
    @FXML
    public void uploadSong(MouseEvent mouseEvent) throws IOException {
        URL popupURL = getClass().getResource("/com/example/samplesalad/file-picker-dialog.fxml");
        if (popupURL == null) {
            Logger.getLogger(LibraryController.class.getName()).log(Level.SEVERE, "FXML file not found: file-picker-dialog.fxml");
            return;
        }
        Logger.getLogger(LibraryController.class.getName()).log(Level.INFO, "Loading FXML file: " + popupURL.toString());
        FXMLLoader popupLoader = new FXMLLoader(popupURL);

        Scene scene = popupLoader.load();

        Stage popup = new Stage();
        popup.setTitle("Upload new song");
        popup.setScene(scene);
        popup.initModality(Modality.APPLICATION_MODAL); // Ensures this window must be closed before returning to the main window
        popup.showAndWait();
    }


    /**
     * Initializes the controller.  Sets up the table columns, loads sample data, and formats the date column.
     *
     * @param location  The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sampleDAO = new SampleDAO();

        // Configure table columns
        songNameColumn.setCellValueFactory(new PropertyValueFactory<>("sampleName"));
        dateAddedColumn.setCellValueFactory(new PropertyValueFactory<>("dateAdded"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("sampleGenre"));

        // Format dateAdded column
        dateAddedColumn.setCellFactory(column -> {
            TableCell<Sample, Timestamp> cell = new TableCell<>() {
                private final DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT); // Make formatter final

                @Override
                protected void updateItem(Timestamp item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(formatter.format(item.toLocalDateTime()));
                    }
                }
            };
            return cell;
        });

        loadSampleData();
    }


    /**
     * Loads sample data into the table view. Retrieves samples associated with the logged-in user and adds them to the `sampleData` ObservableList.
     */
    private void loadSampleData() {
        if (userController.isUserLoggedIn()) {
            List<Sample> samples = sampleDAO.getSamplesByUserId(userController.getLoggedInUser());
            sampleData.addAll(samples);
            sampleTable.setItems(sampleData);
        }
    }
}

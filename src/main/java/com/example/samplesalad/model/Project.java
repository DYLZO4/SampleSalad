package com.example.samplesalad.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a music project containing multiple sequences (or a single sequence),
 * with global settings such as BPM and project metadata.  Supports adding, removing,
 * and managing sequences, as well as saving and loading project data.
 */
public class Project {

    private String projectName;
    private int projectID;
    private int projectBPM; // The overall BPM for the project
    private List<Sequencer> sequences; // A project can have multiple sequences
    private String filePath; // Path for saving/loading the project
    private User user;

    /**
     * Initializes a new Project with a name and BPM.
     *
     * @param projectName The name of the project.
     * @param projectBPM  The default BPM for the project.
     */
    public Project(String projectName, int projectBPM) {
        this.projectName = projectName;
        this.projectBPM = projectBPM;
        this.sequences = new ArrayList<>();
    }

    /**
     * Adds a sequence to the project.
     *
     * @param sequencer The sequence to add.
     */
    public void addSequence(Sequencer sequencer) {
        sequences.add(sequencer);
        System.out.println("Sequence added to project.");
    }

    /**
     * Removes a sequence from the project.
     *
     * @param sequencer The sequence to remove.
     */
    public void removeSequence(Sequencer sequencer) {
        sequences.remove(sequencer);
        System.out.println("Sequence removed from project.");
    }

    /**
     * Gets the name of the project.
     *
     * @return The project name.
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * Sets the name of the project.
     *
     * @param projectName The new project name.
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * Gets the project's BPM (Beats Per Minute).
     *
     * @return The project BPM.
     */
    public int getProjectBPM() {
        return projectBPM;
    }

    /**
     * Sets the project's BPM (Beats Per Minute).
     *
     * @param projectBPM The new project BPM.
     */
    public void setProjectBPM(int projectBPM) {
        this.projectBPM = projectBPM;
        System.out.println("Project BPM set to " + projectBPM + " bpm.");
    }

    /**
     * Gets the file path associated with this project (for saving/loading).
     *
     * @return The file path.
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Sets the file path for saving/loading the project.
     *
     * @param filePath The new file path.
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Gets the list of sequences in this project.
     *
     * @return The list of `Sequencer` objects.
     */
    public List<Sequencer> getSequences() {
        return sequences;
    }

    /**
     * Loads the project from the specified file path.  Currently prints a debug message.  File loading logic needs to be implemented.
     */
    public void loadProject() {
        // Logic to load the project from a file
        System.out.println("Project loaded from " + filePath);
    }

    /**
     * Saves the project to the specified file path.  Currently prints a debug message.  File saving logic needs to be implemented.
     */
    public void saveProject() {
        // Logic to save the project to a file
        System.out.println("Project saved to " + filePath);
    }

    /**
     * Gets the project ID.
     *
     * @return The project ID.
     */
    public int getProjectID() {
        return projectID;
    }

    /**
     * Sets the project ID.
     *
     * @param newProjectID The new project ID.
     */
    public void setProjectID(int newProjectID) {
        projectID = newProjectID;
    }

    /**
     * Gets the user associated with this project.
     *
     * @return The associated user.
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user associated with this project.
     *
     * @param newUser The user to associate.
     */
    public void setUser(User newUser) {
        user = newUser;
    }
}
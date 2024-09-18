package com.example.samplesalad.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a music project containing multiple sequences (or a single sequence),
 * with global settings such as BPM and project metadata.
 */
public class Project {

    private String projectName;
    private int projectBPM; // The overall BPM for the project
    private List<Sequencer> sequences; // A project can have multiple sequences
    private String filePath; // Path for saving/loading the project

    /**
     * Initializes the Project with a name and BPM.
     * @param projectName The name of the project.
     * @param projectBPM The default BPM for the project.
     */
    public Project(String projectName, int projectBPM) {
        this.projectName = projectName;
        this.projectBPM = projectBPM;
        this.sequences = new ArrayList<>();
    }

    /**
     * Adds a new sequence to the project.
     * @param sequencer The sequence to add.
     */
    public void addSequence(Sequencer sequencer) {
        sequences.add(sequencer);
        System.out.println("Sequence added to project.");
    }

    /**
     * Removes a sequence from the project.
     * @param sequencer The sequence to remove.
     */
    public void removeSequence(Sequencer sequencer) {
        sequences.remove(sequencer);
        System.out.println("Sequence removed from project.");
    }

    /**
     * Gets the project's name.
     * @return The project name.
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * Sets the project's name.
     * @param projectName The new project name.
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * Gets the project's BPM.
     * @return The project's BPM.
     */
    public int getProjectBPM() {
        return projectBPM;
    }

    /**
     * Sets the project's BPM.
     * @param projectBPM The new BPM.
     */
    public void setProjectBPM(int projectBPM) {
        this.projectBPM = projectBPM;
        // Potentially update each sequence's tempo based on project BPM
        System.out.println("Project BPM set to " + projectBPM + " bpm.");
    }

    /**
     * Gets the file path where the project is saved or loaded from.
     * @return The file path.
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Sets the file path for saving/loading the project.
     * @param filePath The file path.
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Gets the list of sequences in the project.
     * @return A list of sequences.
     */
    public List<Sequencer> getSequences() {
        return sequences;
    }

    /**
     * Loads the project from a file.
     */
    public void loadProject() {
        // Logic to load the project from a file
        System.out.println("Project loaded from " + filePath);
    }

    /**
     * Saves the project to a file.
     */
    public void saveProject() {
        // Logic to save the project to a file
        System.out.println("Project saved to " + filePath);
    }
}
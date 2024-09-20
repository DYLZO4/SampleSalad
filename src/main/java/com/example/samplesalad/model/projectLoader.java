package com.example.samplesalad.model;

import java.util.ArrayList;
import java.util.List;



public class projectLoader {
    /**
     * Main idea for this class:
     * Load project from DB with DAO
     * Iterate through proj sequencer IDs and get all sequences that correspond with that proj ID?
     * Get all corresponding Sequencers in an object list?
     * Iterate through sequencers and retrieve all dependant patterns?
     *
     */
    private ProjectDAO projDAO;
    private Project currProject;
    private ProjectSequenceDAO projSeqDAO;
    private List<Integer> projSequenceIDs = new ArrayList<>();

    public projectLoader() {

    }

    public void loadProject(int projID) {
        currProject= projDAO.get(projID);
        projSequenceIDs = projSeqDAO.getAll();

        for (int sequenceIDs : projSequenceIDs) {

        }
    }
}

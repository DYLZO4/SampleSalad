package com.example.samplesalad.model;

import com.example.samplesalad.model.Sample;
import com.example.samplesalad.model.DAO.SampleDAO;

import java.util.List;

/**
 * The {@code LibraryService} class manages the library of samples. It allows
 * adding new samples to the library and provides methods for retrieving
 * and removing samples.
 */
public class LibraryService {

    private SampleDAO sampleDAO;

    /**
     * Constructor that initializes the sample library and the SampleDAO.
     */
    public LibraryService() {
        this.sampleDAO = new SampleDAO(); // Initialize the DAO for database operations
    }

    /**
     * Adds a new sample to the database (library).
     *
     * @param sample the sample to add
     */
    public void addSample(Sample sample) {
        sampleDAO.add(sample); // Add sample to the database
        System.out.println("Sample added to the database: " + sample.getSampleName());
    }

    /**
     * Retrieves the list of all samples in the database (library).
     *
     * @return the list of samples in the database
     */
    public List<Sample> getSampleLibrary() {
        return sampleDAO.getAll();
    }

    /**
     * Removes a sample from the database by sample ID.
     *
     * @param sampleID the ID of the sample to remove
     */
    public void removeSample(int sampleID) {
        Sample sample = sampleDAO.get(sampleID);
        if (sample != null) {
            sampleDAO.delete(sample);
            System.out.println("Sample with ID " + sampleID + " removed from the library.");
        } else {
            System.out.println("Sample with ID " + sampleID + " not found.");
        }
    }
}

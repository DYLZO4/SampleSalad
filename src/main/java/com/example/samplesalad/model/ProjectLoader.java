package com.example.samplesalad.model;

import java.sql.Connection;
import java.util.List;

/**
 * The {@code ProjectLoader} class is responsible for loading a music project from
 * the database, including all its associated sequencers, patterns, and pad events.
 */
public class ProjectLoader {

    private ProjectDAO projectDAO;
    private ProjectSequenceDAO projectSequenceDAO;
    private SequencerDAO sequencerDAO;
    private PatternDAO patternDAO;
    private Connection connection;

    /**
     * Constructs a {@code ProjectLoader} object with the given data access objects
     * (DAOs) and database connection.
     *
     * @param projectDAO           The DAO for accessing project data.
     * @param projectSequenceDAO   The DAO for accessing project-sequencer relationships.
     * @param sequencerDAO         The DAO for accessing sequencer data.
     * @param patternDAO           The DAO for accessing pattern data.
     * @param connection           The database connection to use.
     */
    public ProjectLoader(ProjectDAO projectDAO, ProjectSequenceDAO projectSequenceDAO,
                         SequencerDAO sequencerDAO, PatternDAO patternDAO, Connection connection) {
        this.projectDAO = projectDAO;
        this.projectSequenceDAO = projectSequenceDAO;
        this.sequencerDAO = sequencerDAO;
        this.patternDAO = patternDAO;
        this.connection = connection;
    }

    /**
     * Loads a project from the database by its ID, including all its associated
     * sequencers, patterns, and pad events.
     *
     * @param projectId The ID of the project to load.
     * @return The loaded {@link Project} object, or {@code null} if the project is not found.
     */
    public Project loadProject(int projectId) {
        Project project = projectDAO.get(projectId);

        if (project != null) {
            List<Integer> sequencerIds = projectSequenceDAO.getSequencerIdsByProjectId(projectId);
            for (int sequencerId : sequencerIds) {
                Sequencer sequencer = sequencerDAO.get(sequencerId);
                if (sequencer != null) {
                    project.addSequence(sequencer);
                    loadPatternsForSequencer(sequencer);
                }
            }
        }

        return project;
    }

    /**
     * Loads the patterns associated with a given sequencer.
     *
     * @param sequencer The {@link Sequencer} for which to load patterns.
     */
    private void loadPatternsForSequencer(Sequencer sequencer) {
        List<Pattern> patterns = patternDAO.getPatternsBySequencerId(sequencer.getSequencerID());
        for (Pattern pattern : patterns) {
            sequencer.addPattern(pattern);
            loadPadEventsForPattern(pattern);
        }
    }

    /**
     * Loads the pad events associated with a given pattern.
     *
     * @param pattern The {@link Pattern} for which to load pad events.
     */
    private void loadPadEventsForPattern(Pattern pattern) {
        PadEventDAO padEventDAO = new PadEventDAO(connection);
        List<PadEvent> padEvents = padEventDAO.getPadEventsByPatternId(pattern.getPatternID());
        for (PadEvent padEvent : padEvents) {
            pattern.addPadEvent(padEvent);
        }
    }
}
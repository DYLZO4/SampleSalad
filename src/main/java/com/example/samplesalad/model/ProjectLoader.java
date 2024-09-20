package com.example.samplesalad.model;

import java.sql.Connection;
import java.util.List;

public class ProjectLoader {

    private ProjectDAO projectDAO;
    private ProjectSequenceDAO projectSequenceDAO;
    private SequencerDAO sequencerDAO;
    private PatternDAO patternDAO;
    private Connection connection;

    public ProjectLoader(ProjectDAO projectDAO, ProjectSequenceDAO projectSequenceDAO,
                         SequencerDAO sequencerDAO, PatternDAO patternDAO, Connection connection) {
        this.projectDAO = projectDAO;
        this.projectSequenceDAO = projectSequenceDAO;
        this.sequencerDAO = sequencerDAO;
        this.patternDAO = patternDAO;
        this.connection = connection;
    }

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

    private void loadPatternsForSequencer(Sequencer sequencer) {
        List<Pattern> patterns = patternDAO.getPatternsBySequencerId(sequencer.getSequencerID());
        for (Pattern pattern : patterns) {
            sequencer.addPattern(pattern);
            loadPadEventsForPattern(pattern);
        }
    }

    private void loadPadEventsForPattern(Pattern pattern) {
        PadEventDAO padEventDAO = new PadEventDAO(connection);
        List<PadEvent> padEvents = padEventDAO.getPadEventsByPatternId(pattern.getPatternID());
        for (PadEvent padEvent : padEvents) {
            pattern.addPadEvent(padEvent);
        }
    }
}
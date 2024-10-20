package com.example.samplesalad.model.service;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.io.jvm.AudioDispatcherFactory;
import be.tarsos.dsp.onsets.OnsetHandler;
import be.tarsos.dsp.onsets.PercussionOnsetDetector;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A service class for detecting the Beats Per Minute (BPM) of an audio file.
 */
public class BPMDetector {

    /**
     * Detects the BPM of an audio file.
     *
     * @param filePath The path to the audio file.
     * @return The detected BPM, or 0 if not enough beats were detected.
     * @throws IOException              If an I/O error occurs while reading the audio file.
     * @throws UnsupportedAudioFileException If the audio file format is not supported.
     */
    public static float detectBPM(String filePath) throws IOException, UnsupportedAudioFileException {
        File audioFile = new File(filePath);

        // Create an AudioDispatcher to read the audio file
        AudioDispatcher dispatcher = AudioDispatcherFactory.fromFile(audioFile, 2048, 1024);

        // List to store beat timestamps
        List<Double> beatTimestamps = new ArrayList<>();

        // Create an OnsetHandler to capture beats
        OnsetHandler handler = new OnsetHandler() {
            /**
             * Handles an onset event.
             *
             * @param time     The time of the onset in seconds.
             * @param salience The salience of the onset.
             */
            @Override
            public void handleOnset(double time, double salience) {
                System.out.println(time);
                beatTimestamps.add(time);  // Store onset timestamps (i.e., beats)
            }
        };

        // Create the PercussionOnsetDetector
        int sensitivity = 48;  // Adjust sensitivity (higher is more sensitive)
        int threshold = 14;     // Adjust threshold for detection
        PercussionOnsetDetector onsetDetector = new PercussionOnsetDetector(44100, 2048, handler, sensitivity, threshold);

        // Add the onset detector to the dispatcher
        dispatcher.addAudioProcessor(onsetDetector);

        // Run the dispatcher to process the audio and detect beats
        dispatcher.run();
        System.out.println(beatTimestamps);

        // Calculate BPM based on beat timestamps
        return calculateBPM(beatTimestamps);
    }

    /**
     * Calculates the BPM based on a list of beat timestamps.
     *
     * @param beatTimestamps A list of beat timestamps in seconds.
     * @return The calculated BPM, or 0 if not enough beats were detected.
     */
    private static float calculateBPM(List<Double> beatTimestamps) {
        if (beatTimestamps.size() < 2) {
            return 0;  // Not enough beats to calculate BPM
        }

        List<Double> intervals = new ArrayList<>();
        for (int i = 1; i < beatTimestamps.size(); i++) {
            intervals.add(beatTimestamps.get(i) - beatTimestamps.get(i - 1));
        }

        // Calculate the average interval and convert to BPM
        double averageInterval = intervals.stream().mapToDouble(Double::doubleValue).average().orElse(0);
        return (float) (60.0 / averageInterval);  // Convert interval (in seconds) to BPM
    }
}
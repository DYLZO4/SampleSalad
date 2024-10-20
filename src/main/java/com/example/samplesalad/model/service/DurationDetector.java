package com.example.samplesalad.model.service;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.jvm.AudioDispatcherFactory;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

/**
 * A service class for determining the duration of an audio file.
 */
public class DurationDetector {

    /**
     * Gets the duration of an audio file in seconds.
     *
     * @param filePath The path to the audio file.
     * @return The duration of the audio file in seconds.
     * @throws IOException              If an I/O error occurs while reading the audio file.
     * @throws UnsupportedAudioFileException If the audio file format is not supported.
     */
    public static double getAudioDurationInSeconds(String filePath) throws IOException, UnsupportedAudioFileException {
        File audioFile = new File(filePath);

        // Create an AudioDispatcher to read the audio file.  Buffer size and overlap are
        // set to reasonable values, but could be adjusted if needed.
        AudioDispatcher dispatcher = AudioDispatcherFactory.fromFile(audioFile, 1024, 512);

        // Use an array to hold the total processed samples because the value needs to be
        // modifiable inside the anonymous inner class.
        final double[] totalProcessedSamples = {0};

        // Get the sample rate of the audio.
        float sampleRate = dispatcher.getFormat().getSampleRate();

        // Create an AudioProcessor to count the total number of samples processed.
        AudioProcessor sampleCounter = new AudioProcessor() {
            /**
             * Processes an audio event. This method is called for each audio buffer.
             *
             * @param audioEvent The audio event containing the audio buffer.
             * @return True if the processing was successful, false otherwise.
             */
            @Override
            public boolean process(AudioEvent audioEvent) {
                // Increment the total number of processed samples.
                totalProcessedSamples[0] += audioEvent.getBufferSize();
                return true; // Continue processing
            }

            /**
             * Called when the audio processing is finished.
             */
            @Override
            public void processingFinished() {
                // No action needed here.
            }
        };

        // Add the sample counter processor to the dispatcher.
        dispatcher.addAudioProcessor(sampleCounter);

        // Run the dispatcher to process the entire audio file.
        dispatcher.run();

        // Calculate the total duration in seconds.
        double durationInSeconds = totalProcessedSamples[0] / sampleRate;

        return durationInSeconds;
    }
}
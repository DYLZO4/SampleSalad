package com.example.samplesalad.model.service;
import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.io.jvm.AudioDispatcherFactory;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class DurationDetector {

    public static double getAudioDurationInSeconds(String filePath) throws IOException, UnsupportedAudioFileException {
        File audioFile = new File(filePath);

        // Create an AudioDispatcher to read the audio file
        AudioDispatcher dispatcher = AudioDispatcherFactory.fromFile(audioFile, 1024, 512);

        // Total number of samples processed
        final double[] totalProcessedSamples = {0};

        // Sample rate of the audio file
        float sampleRate = dispatcher.getFormat().getSampleRate();

        // AudioProcessor to count the number of samples processed
        AudioProcessor sampleCounter = new AudioProcessor() {
            @Override
            public boolean process(AudioEvent audioEvent) {
                // Increment the number of samples processed
                totalProcessedSamples[0] += audioEvent.getBufferSize();
                return true;
            }

            @Override
            public void processingFinished() {
                // Nothing to do here
            }
        };

        // Add the sample counter processor to the dispatcher
        dispatcher.addAudioProcessor(sampleCounter);

        // Run the dispatcher to process the entire audio file
        dispatcher.run();

        // Calculate the total duration in seconds
        double durationInSeconds = totalProcessedSamples[0] / sampleRate;

        return durationInSeconds;
    }
}


import com.example.samplesalad.model.Effect;
import com.example.samplesalad.model.Sample;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class SampleTest {
    private Sample sample;

    @BeforeEach
    void setUp() {
        sample = new Sample(1, "path/to/sample.wav");
    }

    @Test
    void testLoadSample() {
        File file = new File("path/to/new_sample.wav");
        sample.loadSample(file);
        assertEquals("path\\to\\new_sample.wav", sample.getFilePath(), "Sample file path should be updated.");
    }

    @Test
    void testPlaySample() {
        // Simulating playback
        sample.playSample();
        // No return value, just ensure it doesn't throw exceptions.
    }

    @Test
    void testStopSample() {
        // Simulating stopping playback
        sample.stopSample();
        // No return value, just ensure it doesn't throw exceptions.
    }

    @Test
    void testSetPitch() {
        sample.setPitch(1.5);
        assertEquals(1.5, sample.getPitch(), "Pitch should be set to 1.5");
    }

    @Test
    void testSetVolume() {
        sample.setVolume(0.75);
        assertEquals(0.75, sample.getVolume(), "Volume should be set to 0.75");
    }

    @Test
    void testSetStartTime() {
        sample.setStartTime(2.5);
        assertEquals(2.5, sample.getStartTime(), "Start time should be set to 2.5");
    }

    @Test
    void testSetEndTime() {
        sample.setEndTime(5.0);
        assertEquals(5.0, sample.getEndTime(), "End time should be set to 5.0");
    }

    @Test
    void testApplyEffect() {
        Effect effect = new Effect("Reverb");
        sample.applyEffect(effect);
        assertTrue(sample.getAppliedEffects().contains(effect), "Effect should be added to the sample.");
    }

    @Test
    void testRemoveEffect() {
        Effect effect = new Effect("Delay");
        sample.applyEffect(effect);
        sample.removeEffect(effect);
        assertFalse(sample.getAppliedEffects().contains(effect), "Effect should be removed from the sample.");
    }
}

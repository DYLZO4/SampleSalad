import com.example.samplesalad.model.Effect;
import com.example.samplesalad.model.Sample;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link Sample}.
 * <p>
 * This class contains test cases for the methods of the {@link Sample} class,
 * including loading, playing, stopping samples, setting properties, and managing effects.
 * </p>
 */
class SampleTest {
    private Sample sample;

    /**
     * Sets up the test environment by creating a new {@link Sample} instance
     * with sample ID 1 and file path "path/to/sample.wav" before each test.
     */
    @BeforeEach
    void setUp() {
        sample = new Sample(1, "path/to/sample.wav");
    }

    /**
     * Tests the {@link Sample#loadSample(File)} method.
     * <p>
     * Verifies that the sample file path is updated correctly.
     * </p>
     */
    @Test
    void testLoadSample() {
        File file = new File("path/to/new_sample.wav");
        sample.loadSample(file);
        assertEquals("path\\to\\new_sample.wav", sample.getFilePath(), "Sample file path should be updated.");
    }

    /**
     * Tests the {@link Sample#playSample()} method.
     * <p>
     * Ensures that playback simulation does not throw any exceptions.
     * </p>
     */
    @Test
    void testPlaySample() {
        sample.playSample();
        // No return value, just ensure it doesn't throw exceptions.
    }

    /**
     * Tests the {@link Sample#stopSample()} method.
     * <p>
     * Ensures that stopping playback simulation does not throw any exceptions.
     * </p>
     */
    @Test
    void testStopSample() {
        sample.stopSample();
        // No return value, just ensure it doesn't throw exceptions.
    }

    /**
     * Tests the {@link Sample#setPitch(double)} method.
     * <p>
     * Verifies that the pitch is set correctly and retrieved.
     * </p>
     */
    @Test
    void testSetPitch() {
        sample.setPitch(1.5);
        assertEquals(1.5, sample.getPitch(), "Pitch should be set to 1.5");
    }

    /**
     * Tests the {@link Sample#setVolume(double)} method.
     * <p>
     * Verifies that the volume is set correctly and retrieved.
     * </p>
     */
    @Test
    void testSetVolume() {
        sample.setVolume(0.75);
        assertEquals(0.75, sample.getVolume(), "Volume should be set to 0.75");
    }

    /**
     * Tests the {@link Sample#setStartTime(double)} method.
     * <p>
     * Verifies that the start time is set correctly and retrieved.
     * </p>
     */
    @Test
    void testSetStartTime() {
        sample.setStartTime(2.5);
        assertEquals(2.5, sample.getStartTime(), "Start time should be set to 2.5");
    }

    /**
     * Tests the {@link Sample#setEndTime(double)} method.
     * <p>
     * Verifies that the end time is set correctly and retrieved.
     * </p>
     */
    @Test
    void testSetEndTime() {
        sample.setEndTime(5.0);
        assertEquals(5.0, sample.getEndTime(), "End time should be set to 5.0");
    }

    /**
     * Tests the {@link Sample#applyEffect(Effect)} method.
     * <p>
     * Verifies that an effect is added to the sample's list of applied effects.
     * </p>
     */
    @Test
    void testApplyEffect() {
        Effect effect = new Effect("Reverb");
        sample.applyEffect(effect);
        assertTrue(sample.getAppliedEffects().contains(effect), "Effect should be added to the sample.");
    }

    /**
     * Tests the {@link Sample#removeEffect(Effect)} method.
     * <p>
     * Verifies that an effect is removed from the sample's list of applied effects.
     * </p>
     */
    @Test
    void testRemoveEffect() {
        Effect effect = new Effect("Delay");
        sample.applyEffect(effect);
        sample.removeEffect(effect);
        assertFalse(sample.getAppliedEffects().contains(effect), "Effect should be removed from the sample.");
    }
}

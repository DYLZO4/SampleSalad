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
        sample = new Sample("path/to/sample.wav", "Dog", "Woof", "Bark", 0, 1);
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
     * Tests the {@link Sample#setBPM(double)} method.
     * <p>
     * Verifies that the volume is set correctly and retrieved.
     * </p>
     */
    @Test
    void testSetVolume() {
        sample.setBPM(0.75);
        assertEquals(0.75, sample.getBPM(), "Volume should be set to 0.75");
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

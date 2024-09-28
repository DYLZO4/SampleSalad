import com.example.samplesalad.model.Effect;
import com.example.samplesalad.model.Sample;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link Effect}.
 * <p>
 * This class contains test cases for the methods of the {@link Effect} class,
 * including setting and getting parameters, setting effect types, and applying effects.
 * </p>
 */
class EffectTest {
    private Effect effect;

    /**
     * Sets up the test environment by creating a new {@link Effect} instance
     * with the effect type "Reverb" before each test.
     */
    @BeforeEach
    void setUp() {
        effect = new Effect("Reverb");
    }

    /**
     * Tests the {@link Effect#setEffectType(String)} method.
     * <p>
     * Verifies that the effect type can be set correctly and retrieved.
     * </p>
     */
    @Test
    void testSetEffectType() {
        effect.setEffectType("Delay");
        assertEquals("Delay", effect.getEffectType(), "Effect type should be set to 'Delay'");
    }

    /**
     * Tests the {@link Effect#setParameter(String, float)} method.
     * <p>
     * Verifies that a parameter can be set and retrieved correctly.
     * </p>
     */
    @Test
    void testSetParameter() {
        effect.setParameter("decay", 0.5f);
        assertEquals(0.5f, effect.getParameter("decay"), "Decay parameter should be set to 0.5");
    }

    /**
     * Tests the {@link Effect#getParameter(String)} method when the parameter does not exist.
     * <p>
     * Verifies that a default value of 0.0f is returned if the parameter is not found.
     * </p>
     */
    @Test
    void testGetParameter_DefaultValue() {
        assertEquals(0.0f, effect.getParameter("nonexistent"), "Nonexistent parameter should return 0.0 by default.");
    }

    /**
     * Tests the {@link Effect#apply(Sample)} method.
     * <p>
     * Ensures that applying the effect to a {@link Sample} does not throw an exception.
     * </p>
     */
    @Test
    void testApplyEffect() {
        Sample sample = new Sample("path/to/sample.wav", "Dog", "Woof", "Bark", 0, 1);
        effect.apply(sample);
        // Ensure the method runs without throwing an exception
    }
}

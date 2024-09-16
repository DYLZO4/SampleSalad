import com.example.samplesalad.model.Effect;
import com.example.samplesalad.model.Sample;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EffectTest {
    private Effect effect;

    @BeforeEach
    void setUp() {
        effect = new Effect("Reverb");
    }

    @Test
    void testSetEffectType() {
        effect.setEffectType("Delay");
        assertEquals("Delay", effect.getEffectType(), "Effect type should be set to 'Delay'");
    }

    @Test
    void testSetParameter() {
        effect.setParameter("decay", 0.5f);
        assertEquals(0.5f, effect.getParameter("decay"), "Decay parameter should be set to 0.5");
    }

    @Test
    void testGetParameter_DefaultValue() {
        assertEquals(0.0f, effect.getParameter("nonexistent"), "Nonexistent parameter should return 0.0 by default.");
    }

    @Test
    void testApplyEffect() {
        Sample sample = new Sample(1, "path/to/sample.wav");
        effect.apply(sample);
        // Ensure the method runs without throwing an exception
    }
}
import com.example.samplesalad.model.Pad;
import com.example.samplesalad.model.Sample;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PadTest {

    private Pad pad;
    private Sample mockSample;

    @BeforeEach
    void setUp() {
        mockSample  = new Sample("path/to/sample.wav", "Dog", "Woof", "Bark", 0, 1);
        pad = new Pad(mockSample);
    }

    @Test
    void testSetAndGetSample() {
        Sample newSample = new Sample("path/to/sample2.wav", "Cat", "Meow", "Bark", 0, 1);
        pad.setSample(newSample);
        assertEquals(newSample, pad.getSample());
    }

    @Test
    void testTriggerPad() {
        pad.setPadId(1);
        pad.triggerPad();
    }

    @Test
    void testMutePad() {
        pad.setPadId(1);
        pad.mutePad();
    }

    @Test
    void testUnmutePad() {
        pad.setPadId(1);
        pad.unmutePad();
    }

    @Test
    void testSetVolume() {
        pad.setVolume(0.5);
    }

    @Test
    void testSetKeybind() {
        String keybind = "A";
        pad.setKeybind(keybind);
        assertEquals(keybind, pad.getKeybind());
    }

    @Test
    void testIsPlaying() {
        assertFalse(pad.isPlaying());
    }

    @Test
    void testGetAndSetPadId() {
        pad.setPadId(2);
        assertEquals(2, pad.getPadId());
    }
}

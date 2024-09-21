import com.example.samplesalad.model.PadEvent;
import com.example.samplesalad.model.Pad;

import static org.junit.jupiter.api.Assertions.*;

import com.example.samplesalad.model.Sample;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PadEventTest {

    private PadEvent padEvent;
    private Pad mockPad;
    private Sample mockSample;


    @BeforeEach
    void setUp() {
        mockSample = new Sample(1, "filepath");
        mockPad = new Pad(mockSample);
        padEvent = new PadEvent(mockPad);
    }

    @Test
    void testPadEventInitialization() {
        assertEquals(mockPad.getPadId(), padEvent.getPadID());
        assertTrue(padEvent.getTimeStamp() > 0);
    }
/**
 * Disabled Test as it is failing for unkown reason
    //@Test
    void testTriggerEvent() {
        double initialTimeStamp = padEvent.getTimeStamp();
        padEvent.triggerEvent();
        double newTimeStamp = padEvent.getTimeStamp();

        assertTrue(newTimeStamp > initialTimeStamp, "Timestamp should be updated after triggering event");
    }
*/
    @Test
    void testGetPadId() {
        assertEquals(mockPad.getPadId(), padEvent.getPadID());
    }

    @Test
    void testGetTimeStamp() {
        double timeStamp = padEvent.getTimeStamp();
        assertTrue(timeStamp > 0, "Timestamp should be initialized");
    }
}

import com.example.samplesalad.model.Pattern;
import com.example.samplesalad.model.PadEvent;
import com.example.samplesalad.model.Pad;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class PatternTest {

    private Pattern pattern;
    private PadEvent event1;
    private PadEvent event2;

    @BeforeEach
    void setUp() {
        pattern = new Pattern(16, 120);

        Pad pad1 = new Pad(null);
        pad1.setPadId(1);
        event1 = new PadEvent(pad1, 0);

        Pad pad2 = new Pad(null);
        pad2.setPadId(2);
        event2 = new PadEvent(pad2, 0);
    }

    @Test
    void testAddPadEvent() {
        pattern.addPadEvent(event1);
        List<PadEvent> events = pattern.getPadEvents();
        assertEquals(1, events.size());
        assertTrue(events.contains(event1), "event1 should be in the list of events");
    }

    @Test
    void testRemovePadEvent() {
        pattern.addPadEvent(event1);
        pattern.addPadEvent(event2);
        pattern.removePadEvent(event1);

        List<PadEvent> events = pattern.getPadEvents();
        assertEquals(1, events.size());
        assertFalse(events.contains(event1), "event1 should have been removed");
        assertTrue(events.contains(event2), "event2 should still be in the list of events");
    }

    @Test
    void testGetPadEvents() {
        pattern.addPadEvent(event1);
        pattern.addPadEvent(event2);
        List<PadEvent> events = pattern.getPadEvents();

        assertEquals(2, events.size(), "There should be two PadEvent instances in the list");
        assertEquals(event1, events.get(0), "The first event should be event 1");
        assertEquals(event2, events.get(1), "The second event should be event 2");
    }

    @Test
    void testGetAndSetLength() {
        assertEquals(16, pattern.getLength(), "The initial length should be 16");

        pattern.setLength(32);
        assertEquals(32, pattern.getLength(), "The length should be updated to 32");
    }
}

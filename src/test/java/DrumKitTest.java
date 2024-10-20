import com.example.samplesalad.model.DrumKit;
import com.example.samplesalad.model.Pad;
import com.example.samplesalad.model.Sample;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.ArrayList;

class DrumKitTest {

    private DrumKit drumKit;
    private Sample sample1;
    private Sample sample2;

    @BeforeEach
    void setUp() {
        // Initialize the DrumKit and Samples before each test
        drumKit = new DrumKit("Test Kit");

        // Create some sample objects
        sample1 = new Sample("path/to/sample.wav", "Dog", "Woof", "Bark", 0, 1);
        sample2 = new Sample("path/to/sample2.wav", "Cat", "Meow", "Bark", 0, 1);
    }

    @Test
    void testAddPad() {
        // Add a pad with sample1
        drumKit.addPad(sample1);

        // Verify that the pad was added correctly
        assertEquals(17, drumKit.getPads().size());
    }



    @Test
    void testGetPad_validPadNumber() {
        // Add a pad with sample1
        drumKit.addPad(sample1);

        // Verify that we can retrieve the pad
        Pad pad1 = drumKit.getPad(16);
        assertNotNull(pad1);

    }
    @Test
    void testGetPad_validPadNumberSample() {
        // Add a pad with sample1
        drumKit.addPad(sample1);

        // Verify that we can retrieve the pad
        Pad pad1 = drumKit.getPad(16);

        assertEquals(sample1, pad1.getSample());
    }

    @Test
    void testGetPad_invalidPadNumber() {
        // Try retrieving a pad from an invalid index
        assertThrows(IllegalArgumentException.class, () -> drumKit.getPad(1000));
    }


    @Test
    void testLoadKitPadSize() {
        // Create a list of samples
        List<Sample> samples = new ArrayList<>();
        samples.add(sample1);
        samples.add(sample2);

        // Load the samples into the drum kit
        drumKit.loadKit(samples);

        // Verify that the samples were assigned to the pads correctly
        assertEquals(18, drumKit.getPads().size());

    }
    @Test
    void testLoadKitPadSample1() {
        // Create a list of samples
        List<Sample> samples = new ArrayList<>();
        samples.add(sample1);
        samples.add(sample2);

        // Load the samples into the drum kit
        drumKit.loadKit(samples);


        assertEquals(sample1, drumKit.getPad(16).getSample());

    }
    @Test
    void testLoadKitPadSample2() {
        // Create a list of samples
        List<Sample> samples = new ArrayList<>();
        samples.add(sample1);
        samples.add(sample2);

        // Load the samples into the drum kit
        drumKit.loadKit(samples);

        assertEquals(sample2, drumKit.getPad(17).getSample());
    }

    @Test
    void testGetKitName() {
        // Verify the kit name
        assertEquals("Test Kit", drumKit.getKitName());
    }

    @Test
    void testSetKitName() {
        // Change the kit name and verify
        drumKit.setKitName("New Kit Name");
        assertEquals("New Kit Name", drumKit.getKitName());
    }
}
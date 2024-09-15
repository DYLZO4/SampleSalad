import com.example.samplesalad.model.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testUserCreation() {
        // Test User creation with valid data
        String firstName = "Dylan";
        String lastName = "Hessing";
        String password = "password123";
        String phone = "0450157926";
        String email = "dylanhessing@hotmail.com";

        User user = new User(firstName, lastName, password, email, phone);
        assertEquals(firstName, user.getFirstName());
        assertEquals(lastName, user.getLastName());
        assertEquals(phone, user.getPhone());
        assertEquals(email, user.getEmail());
        assertNotNull(user.getHashedPassword());
    }

    @Test
    void testPasswordHashing() {
        String firstName = "Dylan";
        String lastName = "Hessing";
        String password = "password123";
        String phone = "0450157926";
        String email = "dylanhessing@hotmail.com";
        User user = new User(firstName, lastName, password, email, phone);

        assertNotEquals(password, user.getHashedPassword(), "Hashed password should not be equal to the raw password.");
    }

    @Test
    void testHashingIsConsistent() {
        String password = "password123";

        String firstName = "Dylan";
        String lastName = "Hessing";
        String phone = "0450157926";
        String email = "dylanhessing@hotmail.com";

        String firstName1 = "Theo";
        String lastName1 = "Dela Cruz";
        String phone1 = "00000000";
        String email1 = "theodelacruz@hotmail.com";

        User user1 = new User(firstName, lastName, password, email, phone);
        User user2 = new User(firstName1, lastName1, password, email1, phone1);

        assertEquals(user1.getHashedPassword(), user2.getHashedPassword(), "Hashing should be consistent.");
    }

    @Test
    void testDifferentPasswordsProduceDifferentHashes() {
        String password = "password123";
        String password1 = "differentPassword";

        String firstName = "Dylan";
        String lastName = "Hessing";
        String phone = "0450157926";
        String email = "dylanhessing@hotmail.com";

        String firstName1 = "Theo";
        String lastName1 = "Dela Cruz";
        String phone1 = "00000000";
        String email1 = "theodelacruz@hotmail.com";

        User user1 = new User(firstName, lastName, password, email, phone);
        User user2 = new User(firstName1, lastName1, password1, email1, phone1);

        assertNotEquals(user1.getHashedPassword(), user2.getHashedPassword(), "Different passwords should have different hashes.");
    }
}

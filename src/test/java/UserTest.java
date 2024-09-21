import com.example.samplesalad.model.user.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link User}.
 * <p>
 * This class contains test cases for the methods of the {@link User} class,
 * including user creation, password hashing, and validation of hash consistency.
 * </p>
 */
class UserTest {

    /**
     * Tests user creation with valid data.
     * <p>
     * Verifies that a {@link User} object is created correctly with the provided details,
     * and the hashed password is not null.
     * </p>
     */
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

    /**
     * Tests that the password is hashed correctly.
     * <p>
     * Verifies that the raw password is not the same as the hashed password.
     * </p>
     */
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

    /**
     * Tests that hashing is consistent across different user instances.
     * <p>
     * Verifies that the same password produces the same hashed value for different users.
     * </p>
     */
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

    /**
     * Tests that different passwords produce different hashes.
     * <p>
     * Verifies that different passwords result in different hashed values.
     * </p>
     */
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

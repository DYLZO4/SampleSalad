import com.example.samplesalad.model.HashUtil;
import com.example.samplesalad.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link User}.
 */
class UserTest {

    private final String firstName = "Dylan";
    private final String lastName = "Hessing";
    private final String password = "password123";
    private final String phone = "0450157926";
    private final String email = "dylanhessing@hotmail.com";

    private final String differentPassword = "differentPassword";
    private final String firstName1 = "Theo";
    private final String lastName1 = "Dela Cruz";
    private final String phone1 = "00000000";
    private final String email1 = "theodelacruz@hotmail.com";


    @Test
    void testUserCreationFirstName() {
        User user = new User(firstName, lastName, password, email, phone);
        assertEquals(firstName, user.getFirstName());
    }

    @Test
    void testUserCreationLastName() {
        User user = new User(firstName, lastName, password, email, phone);
        assertEquals(lastName, user.getLastName());
    }


    @Test
    void testUserCreationPhone() {
        User user = new User(firstName, lastName, password, email, phone);
        assertEquals(phone, user.getPhone());
    }
    @Test
    void testUserCreationEmail() {
        User user = new User(firstName, lastName, password, email, phone);
        assertEquals(email, user.getEmail());
    }

    @Test
    void testUserCreationPassword() {
        User user = new User(firstName, lastName, password, email, phone);
        assertNotNull(user.getHashedPassword());
    }


    @Test
    void testPasswordHashing() {
        User user = new User(firstName, lastName, HashUtil.hashPassword(password), email, phone);
        assertNotEquals(password, user.getHashedPassword());
    }


    @Test
    void testHashingIsConsistent() {
        User user1 = new User(firstName, lastName, password, email, phone);
        User user2 = new User(firstName1, lastName1, password, email1, phone1);
        assertEquals(user1.getHashedPassword(), user2.getHashedPassword());
    }


    @Test
    void testDifferentPasswordsProduceDifferentHashes() {
        User user1 = new User(firstName, lastName, password, email, phone);
        User user2 = new User(firstName1, lastName1, differentPassword, email1, phone1);
        assertNotEquals(user1.getHashedPassword(), user2.getHashedPassword());
    }
}
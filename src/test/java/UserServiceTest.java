import com.example.samplesalad.model.FakeUserDAO;
import com.example.samplesalad.model.User;
import com.example.samplesalad.model.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    private FakeUserDAO userDAO;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userDAO = new FakeUserDAO();  // FakeUserDAO is used as the in-memory implementation of UserDAO
        userService = new UserService(userDAO);
    }
    @Test
    void testRegisterUser() {
        // Simulate registering a user
        String firstName = "Dylan";
        String lastName = "Hessing";
        String password = "password123";
        String email = "dylanhessing@hotmail.com";
        String phone = "0450157926";

        // Call the registerUser method
        userService.registerUser(firstName, lastName, password, email, phone);

        // Verify that the user was added to the FakeUserDAO
        User registeredUser = userDAO.getByEmail(email);
        assertNotNull(registeredUser, "User should be registered and present in the DAO.");
        assertEquals(email, registeredUser.getEmail());
        assertNotNull(registeredUser.getHashedPassword(), "User should have a hashed password.");
    }

    @Test
    void testLoginUserWithCorrectPassword() {
        // Simulate registering a user
        String firstName = "Dylan";
        String lastName = "Hessing";
        String password = "password123";
        String email = "dylanhessing@hotmail.com";
        String phone = "0450157926";

        // Register the user
        userService.registerUser(firstName, lastName, password, email, phone);

        // Test login with correct credentials
        assertTrue(userService.loginUser(email, password), "Login should succeed with correct password.");
    }

    @Test
    void testLoginUserWithIncorrectPassword() {
        // Simulate registering a user
        String firstName = "Dylan";
        String lastName = "Hessing";
        String password = "password123";
        String email = "dylanhessing@hotmail.com";
        String phone = "0450157926";

        // Register the user
        userService.registerUser(firstName, lastName, password, email, phone);

        // Test login with incorrect password
        String incorrectPassword = "wrongPassword";
    }
}


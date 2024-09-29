import com.example.samplesalad.model.DAO.FakeUserDAO;
import com.example.samplesalad.model.user.User;
import com.example.samplesalad.model.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link UserService}.
 * <p>
 * This class contains test cases for the methods of the {@link UserService} class,
 * including user registration and login functionality using a {@link FakeUserDAO} for in-memory testing.
 * </p>
 */
class UserServiceTest {
    private FakeUserDAO userDAO;
    private UserService userService;

    /**
     * Sets up the test environment by initializing {@link FakeUserDAO} and {@link UserService}
     * instances before each test.
     */
    @BeforeEach
    void setUp() {
        userDAO = new FakeUserDAO();  // FakeUserDAO is used as the in-memory implementation of UserDAO
        userService = new UserService(userDAO);
    }

    /**
     * Tests the {@link UserService#registerUser(String, String, String, String, String)} method.
     * <p>
     * Verifies that a user is registered and added to the {@link FakeUserDAO}.
     * </p>
     */
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

    /**
     * Tests the {@link UserService#loginUser(String, String)} method with correct credentials.
     * <p>
     * Verifies that login is successful when using the correct password.
     * </p>
     */
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

    /**
     * Tests the {@link UserService#loginUser(String, String)} method with incorrect credentials.
     * <p>
     * Verifies that login fails when using an incorrect password.
     * </p>
     */
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
        assertFalse(userService.loginUser(email, incorrectPassword), "Login should fail with incorrect password.");
    }
}

import com.example.samplesalad.model.DAO.FakeUserDAO;
import com.example.samplesalad.model.User;
import com.example.samplesalad.model.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link UserService}.
 */
class UserServiceTest {
    private FakeUserDAO userDAO;
    private UserService userService;
    private String firstName = "Dylan";
    private String lastName = "Hessing";
    private String password = "password123";
    private String email = "dylanhessing@hotmail.com";
    private String phone = "0450157926";



    @BeforeEach
    void setUp() {
        userDAO = new FakeUserDAO();
        userService = new UserService(userDAO);
    }


    @Test
    void testRegisterUserNotNull() {
        userService.registerUser(firstName, lastName, password, email, phone);
        assertNotNull(userDAO.getByEmail(email));
    }

    @Test
    void testRegisterUserEmail() {
        userService.registerUser(firstName, lastName, password, email, phone);
        assertEquals(email, userDAO.getByEmail(email).getEmail());
    }

    @Test
    void testRegisterUserPassword() {
        userService.registerUser(firstName, lastName, password, email, phone);
        assertNotNull(userDAO.getByEmail(email).getHashedPassword());
    }



    @Test
    void testLoginUserWithCorrectPassword() {
        userService.registerUser(firstName, lastName, password, email, phone);
        User user = new User(firstName, lastName, password, email, phone);
        assertEquals(userService.authenticate(email, password).getEmail(), user.getEmail()); // This assertion is fine as it's comparing essential parts of the objects.
    }


    @Test
    void testLoginUserWithIncorrectPassword() {
        userService.registerUser(firstName, lastName, password, email, phone);
        assertNull(userService.authenticate(email, "wrongPassword"));
    }
}
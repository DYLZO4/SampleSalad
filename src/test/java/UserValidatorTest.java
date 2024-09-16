import com.example.samplesalad.model.UserValidator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserValidatorTest {

    // Positive test cases
    @Test
    void validEmailShouldPass() {
        assertTrue(UserValidator.validateEmail("user@example.com"));
    }

    @Test
    void validPhoneNumberShouldPass() {
        assertTrue(UserValidator.validatePhoneNumber("+1234567890"));
    }

    @Test
    void validPasswordShouldPass() {
        assertTrue(UserValidator.validatePassword("Password1!"));
    }

    // Negative test cases
    @Test
    void invalidEmailShouldFail() {
        assertFalse(UserValidator.validateEmail("user@com"));
    }

    @Test
    void invalidPhoneNumberShouldFail() {
        assertFalse(UserValidator.validatePhoneNumber("123abc7890"));
    }

    @Test
    void passwordWithoutUppercaseShouldFail() {
        assertFalse(UserValidator.validatePassword("password1!"));
    }

    @Test
    void passwordWithoutNumberShouldFail() {
        assertFalse(UserValidator.validatePassword("Password!"));
    }

    @Test
    void passwordWithoutSpecialCharacterShouldFail() {
        assertFalse(UserValidator.validatePassword("Password1"));
    }

    // Edge cases
    @Test
    void emailWithSubdomainShouldPass() {
        assertTrue(UserValidator.validateEmail("user@mail.example.com"));
    }

    @Test
    void phoneNumberWithSpacesShouldPass() {
        assertTrue(UserValidator.validatePhoneNumber("123 456 7890"));
    }

    @Test
    void passwordExactly8CharactersShouldPass() {
        assertTrue(UserValidator.validatePassword("P@ssw0rd"));
    }
}
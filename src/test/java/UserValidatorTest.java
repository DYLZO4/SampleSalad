import com.example.samplesalad.model.service.UserValidator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link UserValidator}.
 * <p>
 * This class contains test cases to validate the functionality of the methods
 * in the {@link UserValidator} class. It includes tests for valid and invalid
 * email addresses, phone numbers, and passwords, as well as edge cases.
 * </p>
 */
class UserValidatorTest {

    // Positive test cases

    /**
     * Tests that a valid email address passes validation.
     */
    @Test
    void validEmailShouldPass() {
        assertTrue(UserValidator.validateEmail("user@example.com"));
    }

    /**
     * Tests that a valid phone number passes validation.
     */
    @Test
    void validPhoneNumberShouldPass() {
        assertTrue(UserValidator.validatePhoneNumber("+1234567890"));
    }

    /**
     * Tests that a valid password passes validation.
     * <p>
     * The password must include at least one uppercase letter, one digit,
     * and one special character, and be at least 8 characters long.
     * </p>
     */
    @Test
    void validPasswordShouldPass() {
        assertTrue(UserValidator.validatePassword("Password1!"));
    }

    // Negative test cases

    /**
     * Tests that an invalid email address fails validation.
     * <p>
     * This test verifies that an email address missing a domain fails.
     * </p>
     */
    @Test
    void invalidEmailShouldFail() {
        assertFalse(UserValidator.validateEmail("user@com"));
    }

    /**
     * Tests that an invalid phone number fails validation.
     * <p>
     * This test verifies that a phone number containing non-numeric characters fails.
     * </p>
     */
    @Test
    void invalidPhoneNumberShouldFail() {
        assertFalse(UserValidator.validatePhoneNumber("123abc7890"));
    }

    /**
     * Tests that a password without an uppercase letter fails validation.
     */
    @Test
    void passwordWithoutUppercaseShouldFail() {
        assertFalse(UserValidator.validatePassword("password1!"));
    }

    /**
     * Tests that a password without a digit fails validation.
     */
    @Test
    void passwordWithoutNumberShouldFail() {
        assertFalse(UserValidator.validatePassword("Password!"));
    }

    /**
     * Tests that a password without a special character fails validation.
     */
    @Test
    void passwordWithoutSpecialCharacterShouldFail() {
        assertFalse(UserValidator.validatePassword("Password1"));
    }

    // Edge cases

    /**
     * Tests that an email address with a subdomain passes validation.
     * <p>
     * This test verifies that emails with subdomains are accepted.
     * </p>
     */
    @Test
    void emailWithSubdomainShouldPass() {
        assertTrue(UserValidator.validateEmail("user@mail.example.com"));
    }

    /**
     * Tests that a phone number with spaces passes validation.
     * <p>
     * This test verifies that phone numbers with spaces are accepted.
     * </p>
     */
    @Test
    void phoneNumberWithSpacesShouldPass() {
        assertTrue(UserValidator.validatePhoneNumber("123 456 7890"));
    }

    /**
     * Tests that a password exactly 8 characters long passes validation.
     * <p>
     * This test verifies that the minimum length requirement is met.
     * </p>
     */
    @Test
    void passwordExactly8CharactersShouldPass() {
        assertTrue(UserValidator.validatePassword("P@ssw0rd"));
    }
}

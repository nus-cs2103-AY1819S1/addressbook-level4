package seedu.inventory.model.staff;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.Test;

import seedu.inventory.testutil.Assert;

public class PasswordTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Password(null));
    }

    @Test
    public void constructor_invalidPassword_throwsIllegalArgumentException() {
        String illegalPassword = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Password(illegalPassword));
    }

    @Test
    public void isValidPassword() {
        // null
        Assert.assertThrows(NullPointerException.class, () -> Password.isValidPassword(null));

        // invalid password
        assertFalse(Password.isValidPassword(""));
        assertFalse(Password.isValidPassword(" "));
        assertFalse(Password.isValidPassword("*****"));
        assertFalse(Password.isValidPassword("john dddd"));

        // valid password
        assertTrue(Password.isValidPassword("johndddd"));
        assertTrue(Password.isValidPassword("JohnDDD"));
        assertTrue(Password.isValidPassword("12341234"));
        assertTrue(Password.isValidPassword("johnddddddamyth12341234"));
    }
}

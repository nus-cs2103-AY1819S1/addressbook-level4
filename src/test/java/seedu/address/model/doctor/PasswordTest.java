package seedu.address.model.doctor;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import seedu.address.testutil.Assert;

//@@author jjlee050
public class PasswordTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Password(null));
    }

    @Test
    public void constructor_invalidPassword_throwsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Password(invalidName));
    }

    @Test
    public void isValidPassword() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> Password.isValidPassword(null));

        // invalid name
        assertFalse(Password.isValidPassword("")); // empty string
        assertFalse(Password.isValidPassword(" ")); // spaces only
        assertFalse(Password.isValidPassword("^")); // only non-alphanumeric characters
        assertFalse(Password.isValidPassword("peter*")); // contains non-alphanumeric characters
        assertFalse(Password.isValidPassword("pete")); // less than 6 characters
        assertFalse(Password.isValidPassword("Capital Tan")); // with spaces
        assertFalse(Password.isValidPassword("David Roger Jackson Ray Jr 2nd")); // more than 12 characters

        // valid name
        assertTrue(Password.isValidPassword("joseph")); // 6 alphabets only
        assertTrue(Password.isValidPassword("peterjack")); // lower-case alphabets only
        assertTrue(Password.isValidPassword("81920543")); // numbers only
        assertTrue(Password.isValidPassword("123456789012")); // 12 numbers only
        assertTrue(Password.isValidPassword("CapitalTan")); // mix of upper and lower case alphabets
        assertTrue(Password.isValidPassword("Capital123")); // alphanuemic characters only
        
    }
}

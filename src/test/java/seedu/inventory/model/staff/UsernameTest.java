package seedu.inventory.model.staff;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.inventory.testutil.Assert;

public class UsernameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Username(null));
    }

    @Test
    public void constructor_invalidUsername_throwIllegalArgumentException() {
        String invalidUsername = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Username(invalidUsername));
    }

    @Test
    public void isValidUsername() {
        // null
        Assert.assertThrows(NullPointerException.class, () -> Username.isValidUsername(null));

        // invalid username
        assertFalse(Username.isValidUsername(""));
        assertFalse(Username.isValidUsername(" "));
        assertFalse(Username.isValidUsername("*"));
        assertFalse(Username.isValidUsername("john doe"));
        assertFalse(Username.isValidUsername("....,,,"));

        // valid username
        assertTrue(Username.isValidUsername("johnd"));
        assertTrue(Username.isValidUsername("john111"));
        assertTrue(Username.isValidUsername("1231231"));
        assertTrue(Username.isValidUsername("DaMyth"));
        assertTrue(Username.isValidUsername("johndoeisdamyth"));
    }
}

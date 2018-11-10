package seedu.restaurant.model.account;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.restaurant.logic.commands.CommandTestUtil.INVALID_USERNAME;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_USERNAME_DEMO_ONE;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_USERNAME_DEMO_TWO;

import org.junit.Test;

import seedu.restaurant.testutil.Assert;

//@@author AZhiKai
public class UsernameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Username(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () -> new Username(INVALID_USERNAME));
    }

    @Test
    public void isValidUsername() {
        // null username
        Assert.assertThrows(NullPointerException.class, () -> Username.isValidUsername(null));

        // invalid username
        assertFalse(Username.isValidUsername("")); // empty string
        assertFalse(Username.isValidUsername(" ")); // spaces only
        assertFalse(Username.isValidUsername("^")); // only non-alphanumeric characters
        assertFalse(Username.isValidUsername("peter*")); // contains non-alphanumeric characters
        assertFalse(Username.isValidUsername("azhi kai")); // contains space

        // valid username
        assertTrue(Username.isValidUsername("azhikai")); // alphabets only
        assertTrue(Username.isValidUsername("12345")); // numbers only
        assertTrue(Username.isValidUsername("peter2")); // alphanumeric characters
        assertTrue(Username.isValidUsername("CapitalAng")); // with capital letters
        assertTrue(Username.isValidUsername("ThisIsALongUsernameAlthoughWhoWouldDoThisRight")); // long names
    }

    @Test
    public void hash_code() {
        Username usernameOne = new Username(VALID_USERNAME_DEMO_ONE);
        assertEquals(usernameOne.hashCode(), usernameOne.hashCode());

        Username usernameTwo = new Username(VALID_USERNAME_DEMO_TWO);
        assertEquals(usernameTwo.hashCode(), usernameTwo.hashCode());

        assertNotEquals(usernameOne.hashCode(), usernameTwo.hashCode());
    }
}

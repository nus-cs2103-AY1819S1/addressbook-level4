package seedu.address.model.record;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class HourTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Hour(null));
    }

    @Test
    public void constructor_invalidHour_throwsIllegalArgumentException() {
        String invalidHour = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Hour(invalidHour));
    }

    @Test
    public void isValidHour() {
        // null hour
        Assert.assertThrows(NullPointerException.class, () -> Hour.isValidHour(null));

        // invalid hour
        assertFalse(Hour.isValidHour("")); // empty string
        assertFalse(Hour.isValidHour(" ")); // spaces only
        assertFalse(Hour.isValidHour("^")); // only non-alphanumeric characters
        assertFalse(Hour.isValidHour("0*")); // contains non-alphanumeric characters

        // valid hour
        assertTrue(Hour.isValidHour("1")); // numbers only
    }
}

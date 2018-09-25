package seedu.address.model.record;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

/**
 * Note that VolunteerId will be from Volunteers. All tests should be done there.
 */
public class VolunteerIdTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new VolunteerId(null));
    }

    @Test
    public void constructor_invalidEventId_throwsIllegalArgumentException() {
        String invalidVolunterId = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new VolunteerId(invalidVolunterId));
    }

    @Test
    public void isValidVolunteerId() {
        // null eventId
        Assert.assertThrows(NullPointerException.class, () -> VolunteerId.isValidVolunteerId(null));

        // invalid eventId
        assertFalse(VolunteerId.isValidVolunteerId("")); // empty string
        assertFalse(VolunteerId.isValidVolunteerId(" ")); // spaces only
        assertFalse(VolunteerId.isValidVolunteerId("^")); // only non-alphanumeric characters
        assertFalse(VolunteerId.isValidVolunteerId("0*")); // contains non-alphanumeric characters

        // valid eventId
        assertTrue(VolunteerId.isValidVolunteerId("1")); // numbers only
    }
}

package seedu.address.model.volunteer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class VolunteerIdTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new VolunteerId(null));
    }

    @Test
    public void constructor_invalidVolunteerId_throwsIllegalArgumentException() {
        String invalidVolunteerId = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new VolunteerId(invalidVolunteerId));
    }

    @Test
    public void isValidVolunteerId() {
        // null volunteerId
        Assert.assertThrows(NullPointerException.class, () -> VolunteerId.isValidId(null));

        // invalid volunteerId
        assertFalse(VolunteerId.isValidId("")); // empty string
        assertFalse(VolunteerId.isValidId(" ")); // spaces only
        assertFalse(VolunteerId.isValidId("S1234567")); // contains insufficient characters
        assertFalse(VolunteerId.isValidId("A1234567A")); // contains incorrect start character
        assertFalse(VolunteerId.isValidId("S123B567A")); // contains incorrect character in the middle
        assertFalse(VolunteerId.isValidId("S1234567*")); // contains incorrect end character
        assertFalse(VolunteerId.isValidId("S123456788888A")); // contains too many characters

        // valid volunteerId
        assertTrue(VolunteerId.isValidId("S1234567A")); // correct start character S
        assertTrue(VolunteerId.isValidId("F9876543A")); // correct start character F
        assertTrue(VolunteerId.isValidId("T0123456A")); // correct start character T
        assertTrue(VolunteerId.isValidId("G6543210A")); // correct start character G
        assertTrue(VolunteerId.isValidId("t9999999z")); // lowercase characters
    }
}

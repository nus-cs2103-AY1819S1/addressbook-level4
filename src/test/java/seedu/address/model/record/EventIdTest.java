package seedu.address.model.record;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

/**
 * Note that EventId will be from Event. All tests should be done there.
 */
public class EventIdTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new EventId(null));
    }

    @Test
    public void constructor_invalidEventId_throwsIllegalArgumentException() {
        String invalidEventId = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new EventId(invalidEventId));
    }

    @Test
    public void isValidEventId() {
        // null eventId
        Assert.assertThrows(NullPointerException.class, () -> EventId.isValidEventId(null));

        // invalid eventId
        assertFalse(EventId.isValidEventId("")); // empty string
        assertFalse(EventId.isValidEventId(" ")); // spaces only
        assertFalse(EventId.isValidEventId("^")); // only non-alphanumeric characters
        assertFalse(EventId.isValidEventId("0*")); // contains non-alphanumeric characters

        // valid eventId
        assertTrue(EventId.isValidEventId("1")); // numbers only
    }
}

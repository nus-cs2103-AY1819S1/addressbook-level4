package seedu.scheduler.model.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.scheduler.testutil.Assert;

public class EventNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new EventName(null));
    }

    @Test
    public void constructor_invalidEventName_throwsIllegalArgumentException() {
        String invalidEventName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new EventName(invalidEventName));
    }

    @Test
    public void isValidEventName() {
        // null scheduler
        Assert.assertThrows(NullPointerException.class, () -> EventName.isValidEventName(null));

        // invalid events
        assertFalse(EventName.isValidEventName("")); // empty string
        assertFalse(EventName.isValidEventName(" ")); // spaces only

        // valid events
        assertTrue(EventName.isValidEventName("CS2103 Lecture"));
        assertTrue(EventName.isValidEventName("-")); // one character
        assertTrue(EventName.isValidEventName("223")); // numbers only
    }
}

package seedu.address.model.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class TimeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Time(null));
    }

    @Test
    public void constructor_invalidTime_throwsIllegalArgumentException() {
        String invalidTime = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Time(invalidTime));
    }

    @Test
    public void isValidTime() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> Time.isValidTime(null));

        // invalid name
        assertFalse(Time.isValidTime("")); // empty string
        assertFalse(Time.isValidTime(" ")); // spaces only
        assertFalse(Time.isValidTime("12:30")); // time with colon
        assertFalse(Time.isValidTime("233012")); // hhmmss
        assertFalse(Time.isValidTime("2400")); // invalid hour
        assertFalse(Time.isValidTime("1061")); // invalid minute

        // valid name
        assertTrue(Time.isValidTime("1230")); // normal time
        assertTrue(Time.isValidTime("0000")); // start point
        assertTrue(Time.isValidTime("2359")); // end point
    }
}

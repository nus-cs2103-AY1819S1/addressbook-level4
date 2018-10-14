package seedu.address.model.timetable;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

/**
 * Unit test for the Time model class.
 */
public class TimeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Time(null));
    }

    @Test
    public void constructor_invalidTime_throwsIllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () -> new Time(""));
        Assert.assertThrows(IllegalArgumentException.class, () -> new Time("1445"));
        Assert.assertThrows(IllegalArgumentException.class, () -> new Time("2400"));
    }

    @Test
    public void isValidTime() {
        // null time
        Assert.assertThrows(NullPointerException.class, () -> Time.isValidTime(null));

        // invalid name
        assertFalse(Time.isValidTime("")); // empty-string
        assertFalse(Time.isValidTime(" ")); // spaces only
        assertFalse(Time.isValidTime("^")); // only numbers are allowed.
        assertFalse(Time.isValidTime("hello")); // cannot have non-numerical characters

        // valid time
        assertTrue(Time.isValidTime("0000"));
        assertTrue(Time.isValidTime("1000"));
        assertTrue(Time.isValidTime("1800"));
        assertTrue(Time.isValidTime("2000"));
        assertTrue(Time.isValidTime("2330"));

    }

}

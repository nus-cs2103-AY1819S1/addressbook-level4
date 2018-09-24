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
        // null Time
        Assert.assertThrows(NullPointerException.class, () -> Time.isValidTime(null));

        // blank Time
        assertFalse(Time.isValidTime("")); // empty string
        assertFalse(Time.isValidTime(" ")); // spaces only

        // missing parts
        assertFalse(Time.isValidTime(":59")); // missing hour
        assertFalse(Time.isValidTime("1:")); // missing minute
        assertFalse(Time.isValidTime("12:")); // missing minute

        // invalid parts
        assertFalse(Time.isValidTime("25:00")); // invalid hour over 24
        assertFalse(Time.isValidTime("13:60")); // invalid minute over 59
        assertFalse(Time.isValidTime("1122")); // no delimiter
        assertFalse(Time.isValidTime("11-22")); // wrong delimiter
        assertFalse(Time.isValidTime("13:6")); // invalid one character for minute


        // valid Time
        assertTrue(Time.isValidTime("15:15")); // 3:15 PM
        assertTrue(Time.isValidTime("9:00")); // valid one character for hour
        assertTrue(Time.isValidTime("0:00")); // 12:00 AM
        assertTrue(Time.isValidTime("23:59")); // 11:59 PM
    }
}

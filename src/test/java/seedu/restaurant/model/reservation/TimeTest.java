package seedu.restaurant.model.reservation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.restaurant.testutil.Assert;

//@@author m4dkip
public class TimeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Time(null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidTime = "hi im a time"; //don't believe him, he's not a time
        Assert.assertThrows(IllegalArgumentException.class, () -> new Time(invalidTime));
    }

    @Test
    public void isValidTime() {
        // null date
        Assert.assertThrows(NullPointerException.class, () -> Time.isValidTime(null));

        // invalid times
        assertFalse(Time.isValidTime("")); // empty string
        assertFalse(Time.isValidTime(" ")); // spaces only
        assertFalse(Time.isValidTime("my-time")); // non-numeric
        assertFalse(Time.isValidTime("99")); // just 1 number

        // valid times
        assertTrue(Time.isValidTime("12:00")); // follows strict format
        assertTrue(Time.isValidTime("23:30"));

        // natty can parse these times
        assertTrue(Time.isValidTime("2:30")); // H:mm instead of HH:mm
        assertTrue(Time.isValidTime("2pm")); // with meridian timing
        assertTrue(Time.isValidTime("7 a.m.")); // with meridian timing
        assertTrue(Time.isValidTime("noon")); // parses as 12:00
        assertTrue(Time.isValidTime("midnight")); // parses as 00:00
    }
}

package seedu.address.model.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class EventTimeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new EventTime(null));
    }

    @Test
    public void constructor_invalidTime_throwsIllegalArgumentException() {
        String invalidTime = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new EventTime(invalidTime));
    }

    @Test
    public void isValidTime() {
        // null event time
        Assert.assertThrows(NullPointerException.class, () -> EventTime.isValidTime(null));

        // invalid name
        assertFalse(EventTime.isValidTime("")); // empty string
        assertFalse(EventTime.isValidTime(" ")); // spaces only
        assertFalse(EventTime.isValidTime("12:30")); // time with colon
        assertFalse(EventTime.isValidTime("233012")); // hhmmss
        assertFalse(EventTime.isValidTime("2400")); // invalid hour
        assertFalse(EventTime.isValidTime("1061")); // invalid minute

        // valid name
        assertTrue(EventTime.isValidTime("1230")); // normal time
        assertTrue(EventTime.isValidTime("0000")); // start point
        assertTrue(EventTime.isValidTime("2359")); // end point
    }

    @Test
    public void compareTo() {
        EventTime eventTime = new EventTime("1200");

        // null EventTime
        Assert.assertThrows(NullPointerException.class, () -> eventTime.compareTo(null));

        // same object
        assertTrue(eventTime.compareTo(eventTime) == 0);

        // same time
        EventTime sameTime = new EventTime("1200");
        assertTrue(eventTime.compareTo(sameTime) == 0);

        // eventTime is earlier time
        EventTime laterTime = new EventTime("1230");
        assertTrue(eventTime.compareTo(laterTime) < 0);

        // eventTime is later time
        EventTime earlierTime = new EventTime("1130");
        assertTrue(eventTime.compareTo(earlierTime) > 0);
    }
}

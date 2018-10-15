package seedu.address.model.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class EventDateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new EventDate(null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidDate = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new EventDate(invalidDate));
    }

    @Test
    public void isValidDate() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> EventDate.isValidDate(null));

        // invalid name
        assertFalse(EventDate.isValidDate("")); // empty string
        assertFalse(EventDate.isValidDate(" ")); // spaces only
        assertFalse(EventDate.isValidDate("20180901")); // date not conforming to yyyy-mm-dd
        assertFalse(EventDate.isValidDate("2018-09-013")); // date not conforming to yyyy-mm-dd
        assertFalse(EventDate.isValidDate("201809013")); // date not conforming to yyyy-mm-dd
        assertFalse(EventDate.isValidDate("2018-0901")); // only one dash
        assertFalse(EventDate.isValidDate("20181301")); // invalid month
        assertFalse(EventDate.isValidDate("20181032")); // invalid day
        assertFalse(EventDate.isValidDate("2018-02-29")); // leap year

        // valid name
        assertTrue(EventDate.isValidDate("2018-08-01")); // normal date with separator -
        assertTrue(EventDate.isValidDate("2016-02-29")); // leap year
    }
}

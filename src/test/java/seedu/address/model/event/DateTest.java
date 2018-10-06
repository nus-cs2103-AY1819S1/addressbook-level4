package seedu.address.model.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class DateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Date(null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidDate = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Date(invalidDate));
    }

    @Test
    public void isValidDate() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> Date.isValidDate(null));

        // invalid name
        assertFalse(Date.isValidDate("")); // empty string
        assertFalse(Date.isValidDate(" ")); // spaces only
        assertFalse(Date.isValidDate("20180901")); // date not conforming to yyyy-mm-dd
        assertFalse(Date.isValidDate("2018-09-013")); // date not conforming to yyyy-mm-dd
        assertFalse(Date.isValidDate("201809013")); // date not conforming to yyyy-mm-dd
        assertFalse(Date.isValidDate("2018-0901")); // only one dash
        assertFalse(Date.isValidDate("20181301")); // invalid month
        assertFalse(Date.isValidDate("20181032")); // invalid day
        assertFalse(Date.isValidDate("2018-02-29")); // leap year

        // valid name
        assertTrue(Date.isValidDate("2018-08-01")); // normal date with separator -
        assertTrue(Date.isValidDate("2016-02-29")); // leap year
    }
}

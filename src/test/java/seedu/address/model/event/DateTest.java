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
        // null Date
        Assert.assertThrows(NullPointerException.class, () -> Date.isValidDate(null));

        // blank Date
        assertFalse(Date.isValidDate("")); // empty string
        assertFalse(Date.isValidDate(" ")); // spaces only

        // missing parts
        assertFalse(Date.isValidDate("-12-2018")); // missing day
        assertFalse(Date.isValidDate("31--2018")); // missing month
        assertFalse(Date.isValidDate("02-05-")); // missing year

        // invalid parts
        assertFalse(Date.isValidDate("123-08-2008")); // invalid day with 3 characters
        assertFalse(Date.isValidDate("02-123-2008")); // invalid month with 3 characters
        assertFalse(Date.isValidDate("02-12-20081")); // invalid year with 5 characters
        assertFalse(Date.isValidDate("42-08-2008")); // invalid day with first character 4
        assertFalse(Date.isValidDate("39-08-2020")); // invalid day with first character 3
        assertFalse(Date.isValidDate("05-13-2020")); // invalid month more than 12
        assertFalse(Date.isValidDate("02--05-2018")); // double dash between day and month
        assertFalse(Date.isValidDate("02-05--2018")); // double dash between month and year
        assertFalse(Date.isValidDate("02/05/2018")); // invalid delimiter
        assertFalse(Date.isValidDate("29-02-2018")); // non-leap year 2018
        assertFalse(Date.isValidDate("31-04-2018")); // invalid day for April
        assertFalse(Date.isValidDate("31-06-2018")); // invalid day for June
        assertFalse(Date.isValidDate("31-09-2018")); // invalid day for September
        assertFalse(Date.isValidDate("31-11-2018")); // invalid day for September

        // valid Date
        assertTrue(Date.isValidDate("05-08-2018"));
        assertTrue(Date.isValidDate("01-01-2018")); // first day of January
        assertTrue(Date.isValidDate("15-06-2018")); // middle of June
        assertTrue(Date.isValidDate("31-12-2018")); // last day of December
        assertTrue(Date.isValidDate("29-02-2020")); // leap year 2020
        assertTrue(Date.isValidDate("29-02-2024")); // leap year 2024
    }
}

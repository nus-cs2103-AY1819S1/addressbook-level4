package seedu.address.model.volunteer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class BirthdayTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Birthday(null));
    }

    @Test
    public void constructor_invalidBirthday_throwsIllegalArgumentException() {
        String invalidBirthday = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Birthday(invalidBirthday));
    }

    @Test
    public void isValidBirthday() {
        // null Birthday
        Assert.assertThrows(NullPointerException.class, () -> Birthday.isValidBirthday(null));

        // blank Date
        assertFalse(Birthday.isValidBirthday("")); // empty string
        assertFalse(Birthday.isValidBirthday(" ")); // spaces only

        // missing parts
        assertFalse(Birthday.isValidBirthday("-12-1995")); // missing day
        assertFalse(Birthday.isValidBirthday("31--1995")); // missing month
        assertFalse(Birthday.isValidBirthday("02-05-")); // missing year

        // invalid parts
        assertFalse(Birthday.isValidBirthday("123-08-2008")); // invalid day with 3 characters
        assertFalse(Birthday.isValidBirthday("02-123-2008")); // invalid month with 3 characters
        assertFalse(Birthday.isValidBirthday("02-12-20081")); // invalid year with 5 characters
        assertFalse(Birthday.isValidBirthday("42-08-2008")); // invalid day with first character 4
        assertFalse(Birthday.isValidBirthday("39-08-2008")); // invalid day with first character 3
        assertFalse(Birthday.isValidBirthday("05-13-2008")); // invalid month more than 12
        assertFalse(Birthday.isValidBirthday("02--05-2008")); // double dash between day and month
        assertFalse(Birthday.isValidBirthday("02-05--2008")); // double dash between month and year
        assertFalse(Birthday.isValidBirthday("02/05/2008")); // invalid delimiter
        assertFalse(Birthday.isValidBirthday("29-02-2002")); // non-leap year 2018
        assertFalse(Birthday.isValidBirthday("31-04-2002")); // invalid day for April
        assertFalse(Birthday.isValidBirthday("31-06-2002")); // invalid day for June
        assertFalse(Birthday.isValidBirthday("31-09-2002")); // invalid day for September
        assertFalse(Birthday.isValidBirthday("31-11-2002")); // invalid day for September
        assertFalse(Birthday.isValidBirthday("31-11-2040")); //birthday in future

        // valid parts
        assertTrue(Birthday.isValidBirthday("05-08-1995"));
        assertTrue(Birthday.isValidBirthday("01-01-2001")); // first day of January
        assertTrue(Birthday.isValidBirthday("15-06-2001")); // middle of June
        assertTrue(Birthday.isValidBirthday("31-12-2001")); // last day of December
        assertTrue(Birthday.isValidBirthday("29-02-2004")); // leap year 2020
        assertTrue(Birthday.isValidBirthday("29-02-2008")); // leap year 2024
    }
}

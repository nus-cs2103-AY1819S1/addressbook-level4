package seedu.restaurant.model.reservation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.restaurant.testutil.Assert;

//@@author m4dkip
public class DateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Date(null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidDate = "hi im a date"; //don't believe him, he's not a date
        Assert.assertThrows(IllegalArgumentException.class, () -> new Date(invalidDate));
    }

    @Test
    public void isValidDate() {
        // null date
        Assert.assertThrows(NullPointerException.class, () -> Date.isValidDate(null));

        // invalid dates
        assertFalse(Date.isValidDate("")); // empty string
        assertFalse(Date.isValidDate(" ")); // spaces only
        assertFalse(Date.isValidDate("my-bd-date")); // non-numeric

        // valid dates
        assertTrue(Date.isValidDate("12-12-2019")); // follows strict format
        assertTrue(Date.isValidDate("31-01-2020"));
        assertTrue(Date.isValidDate("11-11-2019"));

        // natty can parse these dates
        assertTrue(Date.isValidDate("19032019")); // No "-" delimiter
        assertTrue(Date.isValidDate("2nd February 2019")); // non-numeric
        assertTrue(Date.isValidDate("2 may 2019")); // non-numeric
        assertTrue(Date.isValidDate("1-12-2020")); // day does not have 2 digits
        assertTrue(Date.isValidDate("next thursday")); // wow! english!
        assertTrue(Date.isValidDate("in 2 weeks")); // it can parse this too??? wow!!!
    }
}

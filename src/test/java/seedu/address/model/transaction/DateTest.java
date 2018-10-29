package seedu.address.model.transaction;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.cca.CcaName;
import seedu.address.testutil.Assert;

//@@author ericyjw

/**
 * To test for valid {@code Date}.
 * Checks for null, empty date and other combinations of dates.
 */
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
        // null date
        Assert.assertThrows(NullPointerException.class, () -> Date.isValidDate(null));

        // invalid date
        assertFalse(Date.isValidDate("")); // empty string
        assertFalse(Date.isValidDate(" ")); // spaces only
        assertFalse(Date.isValidDate("1")); // numbers only
        assertFalse(Date.isValidDate("-")); // symbols only
        assertFalse(Date.isValidDate("F")); // characters only
        assertFalse(Date.isValidDate("12-12-2018")); // contains wrong symbol between digits
        assertFalse(Date.isValidDate("12 12 2018")); // contains spaces between digits
        assertFalse(Date.isValidDate("12DEC2018")); // contains letters between digits
        assertFalse(Date.isValidDate("12.12.18")); // shorten year

        // valid date
        assertTrue(Date.isValidDate("12.12.2018")); // valid date
        assertTrue(Date.isValidDate("1.12.2018")); // shorten date
        assertTrue(Date.isValidDate("01.9.2018")); // shorten month
    }
}

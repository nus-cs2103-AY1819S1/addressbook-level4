package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class DueDateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new DueDate(null));
    }

    @Test
    public void constructor_invalidDueDate_throwsIllegalArgumentException() {
        String invalidDueDate = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new DueDate(invalidDueDate));
    }

    @Test
    public void isValidDueDate() {
        // null due date
        Assert.assertThrows(NullPointerException.class, () -> DueDate.isValidDueDate(null));

        // invalid due date
        assertFalse(DueDate.isValidDueDate("")); // empty string
        assertFalse(DueDate.isValidDueDate(" ")); // spaces only
        assertFalse(DueDate.isValidDueDate("date")); // non-numeric
        assertFalse(DueDate.isValidDueDate("11 11 12")); // spaces within digits
        assertFalse(DueDate.isValidDueDate("00-01-18")); //Invalid date
        assertFalse(DueDate.isValidDueDate("32-02-18")); //Invalid date
        assertFalse(DueDate.isValidDueDate("01-02-18 12pm")); //Invalid time
        assertFalse(DueDate.isValidDueDate("01-02-18 1200pm")); //Invalid time

        // valid due date (minimal format)
        assertTrue(DueDate.isValidDueDate("11-12-18"));
        assertTrue(DueDate.isValidDueDate("01-11-17"));
        assertTrue(DueDate.isValidDueDate("10-11-2018"));

        // valid due date (standard format)
        assertTrue(DueDate.isValidDueDate("11-12-18 1200"));
        assertTrue(DueDate.isValidDueDate("11-12-18 2000"));

    }
}

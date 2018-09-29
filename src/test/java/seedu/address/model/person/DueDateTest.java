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
        assertFalse(DueDate.isValidDueDate("91")); // less than 3 numbers
        assertFalse(DueDate.isValidDueDate("phone")); // non-numeric
        assertFalse(DueDate.isValidDueDate("9011p041")); // alphabets within digits
        assertFalse(DueDate.isValidDueDate("9312 1534")); // spaces within digits

        // valid due date
        assertTrue(DueDate.isValidDueDate("11-12-18"));
        assertTrue(DueDate.isValidDueDate("01-11-17"));
    }
}

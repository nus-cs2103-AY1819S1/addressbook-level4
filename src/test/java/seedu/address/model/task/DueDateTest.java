package seedu.address.model.task;

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
    public void isValidDueDateFormat() {
        // null due date
        Assert.assertThrows(NullPointerException.class, () -> DueDate.isValidDueDateFormat(null));

        // invalid due date
        assertFalse(DueDate.isValidDueDateFormat("")); // empty string
        assertFalse(DueDate.isValidDueDateFormat(" ")); // spaces only
        assertFalse(DueDate.isValidDueDateFormat("date")); // non-numeric
        assertFalse(DueDate.isValidDueDateFormat("11 11 12")); // spaces within digits
        assertFalse(DueDate.isValidDueDateFormat("00-01-18")); //Invalid date
        assertFalse(DueDate.isValidDueDateFormat("32-02-18")); //Invalid date
        assertFalse(DueDate.isValidDueDateFormat("01-02-18 12pm")); //Invalid time
        assertFalse(DueDate.isValidDueDateFormat("01-02-18 1200pm")); //Invalid time

        // valid due date (minimal format)
        assertTrue(DueDate.isValidDueDateFormat("11-12-18"));
        assertTrue(DueDate.isValidDueDateFormat("01-11-17"));
        assertTrue(DueDate.isValidDueDateFormat("10-11-2018"));

        // valid due date (standard format)
        assertTrue(DueDate.isValidDueDateFormat("11-12-18 1200"));
        assertTrue(DueDate.isValidDueDateFormat("11-12-18 2000"));

    }

    @Test
    public void isOverdue() {
        DueDate oldDate = new DueDate("1-1-18");
        assertTrue(oldDate.isOverdue());


        DueDate newDate = new DueDate("1-1-30");
        assertFalse(newDate.isOverdue());
    }

    @Test
    public void equals() {
        DueDate date1 = new DueDate("1-1-18");
        DueDate date2 = new DueDate("1-1-18");
        assertTrue(date1.equals(date2));
    }

    @Test
    public void compareTo() {
        DueDate date1 = new DueDate("1-1-18");
        DueDate date2 = new DueDate("2-1-18");
        DueDate date3 = new DueDate("3-1-18");
        assertTrue(date1.compareTo(date2) < 0);
        assertTrue(date3.compareTo(date2) > 0);

    }
}

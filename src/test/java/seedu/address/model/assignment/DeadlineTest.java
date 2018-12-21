package seedu.address.model.assignment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class DeadlineTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Deadline(null));
    }

    @Test
    public void constructor_invalidDeadline_throwsIllegalArgumentException() {
        String invalidDeadline = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Deadline(invalidDeadline));
    }

    @Test
    public void isValidDeadline() {
        // null deadline
        Assert.assertThrows(NullPointerException.class, () -> Deadline.isValid(null));

        // invalid deadline
        assertFalse(Deadline.isValid("")); // empty string
        assertFalse(Deadline.isValid(" ")); // spaces only
        assertFalse(Deadline.isValid("01/01/0000")); // year 0 invalid
        assertFalse(Deadline.isValid("a12/12/2012")); // non number at the front
        assertFalse(Deadline.isValid("29/02/2013")); // 29 february on non-leap year
        assertFalse(Deadline.isValid("29/02/2100")); // 29 february on non-leap year
        assertFalse(Deadline.isValid("31/04/2100")); // 31 April


        // valid deadline
        assertTrue(Deadline.isValid("01/01/1970"));
        assertTrue(Deadline.isValid("29/02/2000")); // 29 february leap year
        assertTrue(Deadline.isValid("31/01/2000")); // date 31 on certain months.
    }

    @Test
    public void deadlineComparison() {
        Deadline deadline1 = new Deadline("01/01/1970");
        Deadline deadline2 = new Deadline("31/01/2000");
        Deadline deadline3 = new Deadline("29/02/2000");

        assertEquals(deadline1.compareTo(deadline2), -1);
        assertEquals(deadline1.compareTo(deadline3), -1);
        assertEquals(deadline2.compareTo(deadline3), -1);
        assertEquals(deadline1.compareTo(deadline1), 0);
        assertEquals(deadline2.compareTo(deadline2), 0);
        assertEquals(deadline3.compareTo(deadline3), 0);
    }
}

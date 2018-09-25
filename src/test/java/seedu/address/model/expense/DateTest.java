package seedu.address.model.expense;

import org.junit.Test;
import seedu.address.testutil.Assert;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

//@@author jonathantjm
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

        // blank date
        assertFalse(Date.isValidDate("")); // empty string
        assertFalse(Date.isValidDate(" ")); // spaces only

        // missing parts
        assertFalse(Date.isValidDate("09-2018")); // missing day
        assertFalse(Date.isValidDate("31-2018")); // missing month
        assertFalse(Date.isValidDate("15-09")); // missing year

        // invalid parts
        assertFalse(Date.isValidDate("49-02-2018")); // invalid day
        assertFalse(Date.isValidDate("15-13-2018")); // invalid month
        assertFalse(Date.isValidDate("15-09-20192")); // invalid year
        assertFalse(Date.isValidDate("31-02-2018")); // invalid date
        assertFalse(Date.isValidDate("31-04-2018")); // invalid date
        assertFalse(Date.isValidDate("13/02/2018")); // invalid separator

        // valid Date
        assertTrue(Date.isValidDate("15-02-2018"));
        assertTrue(Date.isValidDate("15-02-2017"));
        assertTrue(Date.isValidDate("31-12-2017"));
    }
}

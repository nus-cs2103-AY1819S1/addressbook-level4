package seedu.restaurant.model.sales;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.restaurant.testutil.Assert;

class DateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Date(null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidDate = "31-02-2018";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Date(invalidDate));
    }

    @Test
    public void getDayOfWeek() {
        assertEquals(new Date("31-10-2018").getDayOfWeek(), "Wednesday");
    }

    @Test
    public void compareTo_dateBefore_returnNegative() {
        Date date = new Date("31-12-2018");
        assertTrue((date.compareTo(new Date("31-12-2019"))) < 0);
    }

    @Test
    public void compareTo_dateAfter_returnPositive() {
        Date date = new Date("31-12-2018");
        assertTrue((date.compareTo(new Date("31-12-2017"))) > 0);
    }

    @Test
    public void compareTo_dateEqual_returnZero() {
        Date date = new Date("31-12-2018");
        assertTrue((date.compareTo(new Date("31-12-2018"))) == 0);
    }

    @Test
    public void isValidDate() {
        // null date
        Assert.assertThrows(NullPointerException.class, () -> Date.isValidDate(null));

        // invalid dates
        assertFalse(Date.isValidDate("")); // empty string
        assertFalse(Date.isValidDate(" ")); // spaces only
        assertFalse(Date.isValidDate("19022018")); // Does not have "-" delimiter
        assertFalse(Date.isValidDate("my-bd-date")); // non-numeric
        assertFalse(Date.isValidDate("2nd February 2018")); // non-numeric
        assertFalse(Date.isValidDate("2nd May 18")); // non-numeric
        assertFalse(Date.isValidDate("122-02-2018")); // day does not have 2 digits
        assertFalse(Date.isValidDate("1-12-2018")); // day does not have 2 digits
        assertFalse(Date.isValidDate("12-13-2018")); // no such month
        assertFalse(Date.isValidDate("31-02-2018")); // no such day

        // valid dates
        assertTrue(Date.isValidDate("12-12-2012")); // follows format
        assertTrue(Date.isValidDate("31-01-2018"));
        assertTrue(Date.isValidDate("11-11-2017"));
    }
}

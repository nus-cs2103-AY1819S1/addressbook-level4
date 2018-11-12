package seedu.address.model.order;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class OrderDateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new OrderDate(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidDate = "12/10/18";
        Assert.assertThrows(IllegalArgumentException.class, () -> new OrderDate(invalidDate));
    }

    @Test
    public void isValidDate() {
        // null date
        Assert.assertThrows(NullPointerException.class, () -> OrderDate.isValidDate(null));

        // invalid date
        assertFalse(OrderDate.isValidDate("")); // empty string
        assertFalse(OrderDate.isValidDate(" ")); // spaces only
        assertFalse(OrderDate.isValidDate("12-13-2018")); // date without time
        assertFalse(OrderDate.isValidDate("12/13/2018")); // wrong date format
        assertFalse(OrderDate.isValidDate("12-13 10:00:00")); // wrong date format
        assertFalse(OrderDate.isValidDate("2018-12-10 10:00")); // wrong date format
        assertFalse(OrderDate.isValidDate("31-04-2018 01:00:00")); // invalid date value
        assertFalse(OrderDate.isValidDate("29-02-2018 00:00:00")); // invalid date value

        // valid date
        assertTrue(OrderDate.isValidDate("10-10-18 10:00:00"));
        assertTrue(OrderDate.isValidDate("09-04-2017 12:00:00"));
        assertTrue(OrderDate.isValidDate("06-11-2018 06:00:00"));
        assertTrue(OrderDate.isValidDate("29-02-2016 00:00:00"));
    }
}

package seedu.address.model.wish;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class DateTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Date(null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Date(invalidName));
    }

    @Test
    public void isValidDate() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // invalid date
        assertFalse(Date.isValidDate("32/11/2018")); // invalid day
        assertFalse(Date.isValidDate("01/13/2018")); // invalid month
        assertFalse(Date.isValidDate("")); // empty date string
        assertFalse(Date.isValidDate("email@email.com")); // non date

        // valid
        assertTrue(Date.isValidDate("28/10/2019"));
        assertTrue(Date.isValidDate("2/2/2018"));
        assertTrue(Date.isValidDate("03/2/2018"));
        assertTrue(Date.isValidDate("2/09/2018"));
    }

}

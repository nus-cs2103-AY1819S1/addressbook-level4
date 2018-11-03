package seedu.address.model.calendar;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author GilgameshTC
/**
 * To test for valid {@code Month}.
 */
public class YearTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Year(null));
    }

    @Test
    public void constructor_invalidYear_throwsIllegalArgumentException() {
        String invalidYear = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Month(invalidYear));
    }

    @Test
    public void isValidYear() {
        // null year
        Assert.assertThrows(NullPointerException.class, () -> Year.isValidYear(null));

        // invalid year
        assertFalse(Year.isValidYear("")); // empty string
        assertFalse(Year.isValidYear(" ")); // spaces only
        assertFalse(Year.isValidYear("2018AD")); // contains alphabets
        assertFalse(Year.isValidYear("12345")); // contains more than 4 digit
        assertFalse(Year.isValidYear("12")); // less than 4 digit
        assertFalse(Year.isValidYear("-1234")); // negative number
        assertFalse(Year.isValidYear("-123")); // negative number

        // valid year
        assertTrue(Year.isValidYear("0000")); // all zeroes
        assertTrue(Year.isValidYear("2018"));
        assertTrue(Year.isValidYear("0125"));
    }
}

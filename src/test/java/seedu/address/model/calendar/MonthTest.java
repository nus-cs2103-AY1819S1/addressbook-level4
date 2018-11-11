package seedu.address.model.calendar;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author GilgameshTC
/**
 * To test for valid {@code Month}.
 */
public class MonthTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Month(null));
    }

    @Test
    public void constructor_invalidMonth_throwsIllegalArgumentException() {
        String invalidMonth = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Month(invalidMonth));
    }

    @Test
    public void isValidMonth() {
        // null month
        Assert.assertThrows(NullPointerException.class, () -> Month.isValidMonth(null));

        // invalid month
        assertFalse(Month.isValidMonth("")); // empty string
        assertFalse(Month.isValidMonth(" ")); // spaces only
        assertFalse(Month.isValidMonth("^")); // only non-alphanumeric characters
        assertFalse(Month.isValidMonth("Ja*")); // contains non-alphanumeric characters
        assertFalse(Month.isValidMonth("January")); // contains more than 3 characters
        assertFalse(Month.isValidMonth("Mai")); // not a valid month name

        // valid month
        // Test that month isn't case-sensitive : test using Jan as valid month
        assertTrue(Month.isValidMonth("jan")); // all lower-case
        assertTrue(Month.isValidMonth("JAN")); // all upper-case
        assertTrue(Month.isValidMonth("Jan")); // mixture of upper and lower case

        // Test all other 11 valid month
        assertTrue(Month.isValidMonth("feb")); // February
        assertTrue(Month.isValidMonth("mar")); // March
        assertTrue(Month.isValidMonth("apr")); // April
        assertTrue(Month.isValidMonth("may")); // May
        assertTrue(Month.isValidMonth("jun")); // June
        assertTrue(Month.isValidMonth("jul")); // July
        assertTrue(Month.isValidMonth("aug")); // August
        assertTrue(Month.isValidMonth("sep")); // September
        assertTrue(Month.isValidMonth("oct")); // October
        assertTrue(Month.isValidMonth("nov")); // November
        assertTrue(Month.isValidMonth("dec")); // December

    }
}

package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class OtHourTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new OtHour(null));
    }

    @Test
    public void constructor_invalidOtHour_throwsIllegalArgumentException() {
        String invalidHours = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new OtHour(invalidHours));
    }

    @Test
    public void isValidOtHours() {
        // null OT Hour
        Assert.assertThrows(NullPointerException.class, () -> OtHour.isValidTwoDecimalNumber(null));

        // invalid OT hour
        assertFalse(OtHour.isValidTwoDecimalNumber("")); // empty string
        assertFalse(OtHour.isValidTwoDecimalNumber(" ")); // spaces only
        assertFalse(OtHour.isValidTwoDecimalNumber("hours")); // non-numeric
        assertFalse(OtHour.isValidTwoDecimalNumber("2o")); // alphabets within digits
        assertFalse(OtHour.isValidTwoDecimalNumber("9312 1534")); // spaces within digits

        // valid hours
        assertTrue(OtHour.isValidTwoDecimalNumber("100")); // exactly 3 numbers
        assertTrue(OtHour.isValidTwoDecimalNumber("100.00"));
        assertTrue(OtHour.isValidTwoDecimalNumber("33123")); // long hours
    }
}

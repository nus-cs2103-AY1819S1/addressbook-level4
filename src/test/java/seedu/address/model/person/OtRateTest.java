package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class OtRateTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new OtRate(null));
    }

    @Test
    public void constructor_invalidOtRate_throwsIllegalArgumentException() {
        String invalidRate = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new OtRate(invalidRate));
    }

    @Test
    public void isValidOtRate() {
        // null OT rate
        Assert.assertThrows(NullPointerException.class, () -> OtRate.isValidTwoDecimalNumber(null));

        // invalid OT rate
        assertFalse(OtRate.isValidTwoDecimalNumber("")); // empty string
        assertFalse(OtRate.isValidTwoDecimalNumber(" ")); // spaces only
        assertFalse(OtRate.isValidTwoDecimalNumber("hours")); // non-numeric
        assertFalse(OtRate.isValidTwoDecimalNumber("2o")); // alphabets within digits
        assertFalse(OtRate.isValidTwoDecimalNumber("9312 1534")); // spaces within digits

        // valid rate
        assertTrue(OtRate.isValidTwoDecimalNumber("100")); // exactly 3 numbers
        assertTrue(OtRate.isValidTwoDecimalNumber("100.00")); // decimal places
        assertTrue(OtRate.isValidTwoDecimalNumber("33123")); // long rate
    }
}

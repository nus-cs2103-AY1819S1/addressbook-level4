package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class PayDeductiblesTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new PayDeductibles(null));
    }

    @Test
    public void constructor_invalidPayDeductibles_throwsIllegalArgumentException() {
        String invalidHours = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new OtHour(invalidHours));
    }

    @Test
    public void isValidPayDeductibles() {
        // null pay deductibles
        Assert.assertThrows(NullPointerException.class, () -> PayDeductibles.isValidTwoDecimalNumber(null));

        // invalid pay deductibles
        assertFalse(PayDeductibles.isValidTwoDecimalNumber("")); // empty string
        assertFalse(PayDeductibles.isValidTwoDecimalNumber(" ")); // spaces only
        assertFalse(PayDeductibles.isValidTwoDecimalNumber("hours")); // non-numeric
        assertFalse(PayDeductibles.isValidTwoDecimalNumber("2o")); // alphabets within digits
        assertFalse(PayDeductibles.isValidTwoDecimalNumber("9312 1534")); // spaces within digits

        // valid pay deductibles
        assertTrue(OtHour.isValidTwoDecimalNumber("100")); // exactly 3 numbers
        assertTrue(OtHour.isValidTwoDecimalNumber("100.00")); // decimal places
        assertTrue(OtHour.isValidTwoDecimalNumber("102948200098")); // long hours
    }
}

package seedu.address.model.medicine;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class QuantityToDispenseTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new QuantityToDispense(null));
    }

    @Test
    public void isValidQuantityToDispense() {
        // invalid quantities to dispense
        assertFalse(QuantityToDispense.isValidQuantityToDispense(0)); // cannot be 0
        assertFalse(QuantityToDispense.isValidQuantityToDispense(-2)); // cannot be negative integer

        // valid quantities to dispense
        assertTrue(QuantityToDispense.isValidQuantityToDispense(1));
        assertTrue(QuantityToDispense.isValidQuantityToDispense(2147483647));
        assertTrue(QuantityToDispense.isValidQuantityToDispense(11));
    }
}

package seedu.address.model.expense;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class AddressTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Cost(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidCost = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Cost(invalidCost));
    }

    @Test
    public void isValidCost() {
        // null address
        Assert.assertThrows(NullPointerException.class, () -> Cost.isValidCost(null));

        // invalid costs
        assertFalse(Cost.isValidCost("")); // empty string
        assertFalse(Cost.isValidCost(" ")); // spaces only
        assertFalse(Cost.isValidCost("200")); // number only

        // valid costs
        assertTrue(Cost.isValidCost("255.00"));
        assertTrue(Cost.isValidCost("1.00")); // one dollar
        assertTrue(Cost.isValidCost("231231232131231.00")); // long cost
    }
}

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

        // invalid addresses
        assertFalse(Cost.isValidCost("")); // empty string
        assertFalse(Cost.isValidCost(" ")); // spaces only

        // valid addresses
        assertTrue(Cost.isValidCost("Blk 456, Den Road, #01-355"));
        assertTrue(Cost.isValidCost("-")); // one character
        assertTrue(Cost.isValidCost("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
    }
}

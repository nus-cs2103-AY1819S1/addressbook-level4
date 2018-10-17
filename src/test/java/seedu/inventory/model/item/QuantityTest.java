package seedu.inventory.model.item;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.inventory.testutil.Assert;

public class QuantityTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Quantity(null));
    }

    @Test
    public void constructor_invalidQuantity_throwsIllegalArgumentException() {
        String invalidQuantity = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Quantity(invalidQuantity));
    }

    @Test
    public void isValidQuantity() {
        // null quantity
        Assert.assertThrows(NullPointerException.class, () -> Quantity.isValidQuantity(null));

        // invalid quantities
        assertFalse(Quantity.isValidQuantity("")); // empty string
        assertFalse(Quantity.isValidQuantity(" ")); // spaces only
        assertFalse(Quantity.isValidQuantity("quantity")); // non-numeric
        assertFalse(Quantity.isValidQuantity("9011p041")); // alphabets within digits
        assertFalse(Quantity.isValidQuantity("9312 1534")); // spaces within digits
        assertFalse(Quantity.isValidQuantity("-")); // invalid symbols
        assertFalse(Quantity.isValidQuantity("-132!")); // invalid symbols with digits

        // valid quantities
        assertTrue(Quantity.isValidQuantity("9")); // less than 3 numbers
        assertTrue(Quantity.isValidQuantity("91")); // less than 3 numbers
        assertTrue(Quantity.isValidQuantity("911")); // exactly 3 numbers
        assertTrue(Quantity.isValidQuantity("9114")); // exactly 4 numbers
        assertTrue(Quantity.isValidQuantity("91141")); // exactly 5 numbers
        assertTrue(Quantity.isValidQuantity("623123")); // exactly 5 numbers
        assertTrue(Quantity.isValidQuantity("93121534"));
        assertTrue(Quantity.isValidQuantity("124293842033123")); // long quantities
    }
}

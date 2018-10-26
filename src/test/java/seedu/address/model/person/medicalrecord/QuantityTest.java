package seedu.address.model.person.medicalrecord;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class QuantityTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Quantity(null));
    }

    @Test
    public void constructor_invalidQuantity_throwsIllegalArgumentException() {
        String invalidQuantity = " ";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Quantity(invalidQuantity));
    }

    @Test
    public void isValidQuantity() {
        // null Quantity
        Assert.assertThrows(NullPointerException.class, () -> Quantity.isValidQuantity(null));

        // invalid Quantity
        assertFalse(Quantity.isValidQuantity(" "));
        assertFalse(Quantity.isValidQuantity("**"));
        assertFalse(Quantity.isValidQuantity("()"));
        assertFalse(Quantity.isValidQuantity("dsfvsdf#$%"));


        // valid Quantity
        assertTrue(Quantity.isValidQuantity("1"));
        assertTrue(Quantity.isValidQuantity("2"));
        assertTrue(Quantity.isValidQuantity("3"));
        assertTrue(Quantity.isValidQuantity("4"));
        assertTrue(Quantity.isValidQuantity("5"));
        assertTrue(Quantity.isValidQuantity("6"));
    }
}

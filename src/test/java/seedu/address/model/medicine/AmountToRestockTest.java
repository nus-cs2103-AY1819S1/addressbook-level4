package seedu.address.model.medicine;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class AmountToRestockTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new AmountToRestock(null));
    }

    @Test
    public void isValidAmountToRestock() {
        // invalid amount to restock
        assertFalse(AmountToRestock.isValidAmountToRestock(0)); // cannot be 0
        assertFalse(AmountToRestock.isValidAmountToRestock(-2)); // cannot be negative integer

        // valid amount to restock
        assertTrue(AmountToRestock.isValidAmountToRestock(1));
        assertTrue(AmountToRestock.isValidAmountToRestock(2147483647));
        assertTrue(AmountToRestock.isValidAmountToRestock(11));
    }
}

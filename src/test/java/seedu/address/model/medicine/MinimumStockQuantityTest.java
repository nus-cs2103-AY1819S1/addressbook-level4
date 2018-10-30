package seedu.address.model.medicine;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class MinimumStockQuantityTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new MinimumStockQuantity(null));
    }

    @Test
    public void isValidMinimumStockQuantity() {
        // invalid minimum stock quantities
        assertFalse(MinimumStockQuantity.isValidMinimumStockQuantity(0)); // cannot be 0
        assertFalse(MinimumStockQuantity.isValidMinimumStockQuantity(-2)); // cannot be negative integer

        // valid minimum stock quantities
        assertTrue(MinimumStockQuantity.isValidMinimumStockQuantity(1));
        assertTrue(MinimumStockQuantity.isValidMinimumStockQuantity(2147483647));
        assertTrue(MinimumStockQuantity.isValidMinimumStockQuantity(11));
    }
}

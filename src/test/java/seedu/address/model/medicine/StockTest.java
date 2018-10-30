package seedu.address.model.medicine;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class StockTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Stock(null));
    }

    @Test
    public void isValidStock() {
        // null stock number
        Assert.assertThrows(NullPointerException.class, () -> Stock.isValidStock(null));

        // invalid stock numbers
        assertFalse(Stock.isValidStock(-1)); // cannot be negative

        // valid stock numbers
        assertTrue(Stock.isValidStock(1)); // first case
        assertTrue(Stock.isValidStock(145)); // normal level
        assertTrue(Stock.isValidStock(124033123)); // long stock number
    }
}

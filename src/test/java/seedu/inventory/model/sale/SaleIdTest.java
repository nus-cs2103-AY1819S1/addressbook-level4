package seedu.inventory.model.sale;

import org.junit.Test;

import seedu.inventory.testutil.Assert;

public class SaleIdTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new SaleId(null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        // Test empty string
        Assert.assertThrows(IllegalArgumentException.class, () -> new SaleId(""));

        // Test letters
        Assert.assertThrows(IllegalArgumentException.class, () -> new SaleId("ABCDE"));

        // Test alphanumeric
        Assert.assertThrows(IllegalArgumentException.class, () -> new SaleId("123ABC"));
    }

    @Test
    public void isValidSaleId() {
        // null sale ID
        Assert.assertThrows(NullPointerException.class, () -> SaleId.isValidSaleId(null));
    }
}

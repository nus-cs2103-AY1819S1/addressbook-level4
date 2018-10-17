package seedu.inventory.model.sale;

import org.junit.Test;

import seedu.inventory.testutil.Assert;

public class SaleDateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new SaleDate(null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        // Test empty string
        Assert.assertThrows(IllegalArgumentException.class, () -> new SaleDate(""));

        // Test wrong format dd-mm-yyyy
        Assert.assertThrows(IllegalArgumentException.class, () -> new SaleDate("09-12-2018"));

        // Test wrong format mm-dd-yyyy
        Assert.assertThrows(IllegalArgumentException.class, () -> new SaleDate("12-01-2018"));

        // Test non existent date
        Assert.assertThrows(IllegalArgumentException.class, () -> new SaleDate("2018-12-50"));

        // Test non existent date
        Assert.assertThrows(IllegalArgumentException.class, () -> new SaleDate("2018-02-31"));
    }

    @Test
    public void isValidSaleDate() {
        // null tag name
        Assert.assertThrows(NullPointerException.class, () -> SaleDate.isValidSaleDate(null));
    }
}

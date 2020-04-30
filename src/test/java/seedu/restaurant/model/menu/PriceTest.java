package seedu.restaurant.model.menu;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.restaurant.testutil.Assert;

//@@author yican95
public class PriceTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Price(null));
    }

    @Test
    public void constructor_invalidPrice_throwsIllegalArgumentException() {
        String invalidPrice = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Price(invalidPrice));
    }

    @Test
    public void isValidPrice() {
        // null price
        Assert.assertThrows(NullPointerException.class, () -> Price.isValidPrice(null));

        // invalid price numbers
        assertFalse(Price.isValidPrice("")); // empty string
        assertFalse(Price.isValidPrice(" ")); // spaces only
        assertFalse(Price.isValidPrice("phone")); // non-numeric
        assertFalse(Price.isValidPrice("9011p041")); // alphabets within digits
        assertFalse(Price.isValidPrice("9312 1534")); // spaces within digits
        assertFalse(Price.isValidPrice("2.55555")); // more than 2 decimal places
        assertFalse(Price.isValidPrice("2.55.55.5")); // multiple dots
        assertFalse(Price.isValidPrice("-888")); // negative price
        assertFalse(Price.isValidPrice("2147483647.01")); // Greater than max price

        // valid price numbers
        assertTrue(Price.isValidPrice("911")); // no decimal places
        assertTrue(Price.isValidPrice("2.50")); // at most 2 decimal places
        assertTrue(Price.isValidPrice("2147483647")); // max price
    }
}

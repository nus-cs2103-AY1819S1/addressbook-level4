package seedu.inventory.model.item;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.inventory.testutil.Assert;

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

        // invalid prices
        assertFalse(Price.isValidPrice("")); // empty string
        assertFalse(Price.isValidPrice(" ")); // spaces only
        assertFalse(Price.isValidPrice("price")); // non-numeric
        assertFalse(Price.isValidPrice("9011p041")); // alphabets within digits
        assertFalse(Price.isValidPrice("9312 1534")); // spaces within digits
        assertFalse(Price.isValidPrice("-")); // invalid symbols
        assertFalse(Price.isValidPrice("-132!")); // invalid symbols with digits
        assertFalse(Price.isValidPrice("123456789")); // more than eight digits before decimal point
        assertFalse(Price.isValidPrice("123.")); // invalid decimal
        assertFalse(Price.isValidPrice("123.123")); // more than two digits after decimal point
        assertFalse(Price.isValidPrice("123456789.123")); // too many digits before/after decimal point

        // valid prices
        assertTrue(Price.isValidPrice("9")); // less than 3 numbers
        assertTrue(Price.isValidPrice("91")); // less than 3 numbers
        assertTrue(Price.isValidPrice("911")); // exactly 3 numbers
        assertTrue(Price.isValidPrice("9114")); // exactly 4 numbers
        assertTrue(Price.isValidPrice("91141")); // exactly 5 numbers
        assertTrue(Price.isValidPrice("623123")); // exactly 5 numbers
        assertTrue(Price.isValidPrice("93121534")); // 8 digits (the maximum)
        assertTrue(Price.isValidPrice("1242938.50")); // long price
        assertTrue(Price.isValidPrice("1242938")); // long price without decimal
        assertTrue(Price.isValidPrice("123.5")); // one decimal points
        assertTrue(Price.isValidPrice("123.56")); // two decimal points
        assertTrue(Price.isValidPrice("0.50")); // 0 before decimal point
    }
}

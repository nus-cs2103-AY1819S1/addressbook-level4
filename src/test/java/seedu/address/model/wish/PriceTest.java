package seedu.address.model.wish;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

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
        // null phone number
        Assert.assertThrows(NullPointerException.class, () -> Price.isValidPrice(null));

        // invalid phone numbers
        assertFalse(Price.isValidPrice("")); // empty string
        assertFalse(Price.isValidPrice(" ")); // spaces only
        assertFalse(Price.isValidPrice("91")); // less than 3 numbers
        assertFalse(Price.isValidPrice("phone")); // non-numeric
        assertFalse(Price.isValidPrice("9011p041")); // alphabets within digits
        assertFalse(Price.isValidPrice("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(Price.isValidPrice("911")); // exactly 3 numbers
        assertTrue(Price.isValidPrice("93121534"));
        assertTrue(Price.isValidPrice("124293842033123")); // long phone numbers
    }
}

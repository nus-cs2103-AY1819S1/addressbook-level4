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
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidPhone = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Price(invalidPhone));
    }

    @Test
    public void isValidPhone() {
        // null phone number
        Assert.assertThrows(NullPointerException.class, () -> Price.isValidPhone(null));

        // invalid phone numbers
        assertFalse(Price.isValidPhone("")); // empty string
        assertFalse(Price.isValidPhone(" ")); // spaces only
        assertFalse(Price.isValidPhone("91")); // less than 3 numbers
        assertFalse(Price.isValidPhone("phone")); // non-numeric
        assertFalse(Price.isValidPhone("9011p041")); // alphabets within digits
        assertFalse(Price.isValidPhone("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(Price.isValidPhone("911")); // exactly 3 numbers
        assertTrue(Price.isValidPhone("93121534"));
        assertTrue(Price.isValidPhone("124293842033123")); // long phone numbers
    }
}

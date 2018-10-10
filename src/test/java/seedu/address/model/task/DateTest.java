package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class DateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Date(null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidPhone = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Date(invalidPhone));
    }

    @Test
    public void isValidPhone() {
        // null phone number
        Assert.assertThrows(NullPointerException.class, () -> Date.isValidPhone(null));

        // invalid phone numbers
        assertFalse(Date.isValidPhone("")); // empty string
        assertFalse(Date.isValidPhone(" ")); // spaces only
        assertFalse(Date.isValidPhone("91")); // less than 3 numbers
        assertFalse(Date.isValidPhone("phone")); // non-numeric
        assertFalse(Date.isValidPhone("9011p041")); // alphabets within digits
        assertFalse(Date.isValidPhone("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(Date.isValidPhone("911")); // exactly 3 numbers
        assertTrue(Date.isValidPhone("93121534"));
        assertTrue(Date.isValidPhone("124293842033123")); // long phone numbers
    }
}

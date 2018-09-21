package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class PhoneTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new DueDate(null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidPhone = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new DueDate(invalidPhone));
    }

    @Test
    public void isValidPhone() {
        // null phone number
        Assert.assertThrows(NullPointerException.class, () -> DueDate.isValidDueDate(null));

        // invalid phone numbers
        assertFalse(DueDate.isValidDueDate("")); // empty string
        assertFalse(DueDate.isValidDueDate(" ")); // spaces only
        assertFalse(DueDate.isValidDueDate("91")); // less than 3 numbers
        assertFalse(DueDate.isValidDueDate("phone")); // non-numeric
        assertFalse(DueDate.isValidDueDate("9011p041")); // alphabets within digits
        assertFalse(DueDate.isValidDueDate("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(DueDate.isValidDueDate("911")); // exactly 3 numbers
        assertTrue(DueDate.isValidDueDate("93121534"));
        assertTrue(DueDate.isValidDueDate("124293842033123")); // long phone numbers
    }
}

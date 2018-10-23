package seedu.address.model.volunteer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class AddressTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new VolunteerAddress(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidAddress = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new VolunteerAddress(invalidAddress));
    }

    @Test
    public void isValidAddress() {
        // null address
        Assert.assertThrows(NullPointerException.class, () -> VolunteerAddress.isValidAddress(null));

        // invalid addresses
        assertFalse(VolunteerAddress.isValidAddress("")); // empty string
        assertFalse(VolunteerAddress.isValidAddress(" ")); // spaces only

        // valid addresses
        assertTrue(VolunteerAddress.isValidAddress("Blk 456, Den Road, #01-355"));
        assertTrue(VolunteerAddress.isValidAddress("-")); // one character
        assertTrue(VolunteerAddress.isValidAddress("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA"));
        // long address
    }
}

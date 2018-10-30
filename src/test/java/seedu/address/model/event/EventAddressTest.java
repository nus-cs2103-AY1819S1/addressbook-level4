package seedu.address.model.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class EventAddressTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new EventAddress(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidAddress = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new EventAddress(invalidAddress));
    }

    @Test
    public void isValidAddress() {
        // null address
        Assert.assertThrows(NullPointerException.class, () -> EventAddress.isValidAddress(null));

        // invalid addresses
        assertFalse(EventAddress.isValidAddress("")); // empty string
        assertFalse(EventAddress.isValidAddress(" ")); // spaces only

        // valid addresses
        assertTrue(EventAddress.isValidAddress("Blk 456, Den Road, #01-355"));
        assertTrue(EventAddress.isValidAddress("-")); // one character
        assertTrue(EventAddress.isValidAddress("NUS")); // one word
        assertTrue(EventAddress.isValidAddress("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA"));
        // long address
    }
}

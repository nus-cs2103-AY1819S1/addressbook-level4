package seedu.address.model.wish;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class UrlTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Url(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidAddress = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Url(invalidAddress));
    }

    @Test
    public void isValidAddress() {
        // null address
        Assert.assertThrows(NullPointerException.class, () -> Url.isValidAddress(null));

        // invalid addresses
        assertFalse(Url.isValidAddress("")); // empty string
        assertFalse(Url.isValidAddress(" ")); // spaces only

        // valid addresses
        assertTrue(Url.isValidAddress("Blk 456, Den Road, #01-355"));
        assertTrue(Url.isValidAddress("-")); // one character
        assertTrue(Url.isValidAddress("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
    }
}

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
        Assert.assertThrows(NullPointerException.class, () -> Url.isValidUrl(null));

        // invalid addresses
        assertFalse(Url.isValidUrl("")); // empty string
        assertFalse(Url.isValidUrl(" ")); // spaces only

        // valid addresses
        assertTrue(Url.isValidUrl("Blk 456, Den Road, #01-355"));
        assertTrue(Url.isValidUrl("-")); // one character
        assertTrue(Url.isValidUrl("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
    }
}

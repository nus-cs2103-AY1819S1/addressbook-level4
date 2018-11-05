package seedu.thanepark.model.ride;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.thanepark.testutil.Assert;

public class ZoneTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Zone(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidAddress = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Zone(invalidAddress));
    }

    @Test
    public void isValidZone() {
        // null thanepark
        Assert.assertThrows(NullPointerException.class, () -> Zone.isValidZone(null));

        // invalid addresses
        assertFalse(Zone.isValidZone("")); // empty string
        assertFalse(Zone.isValidZone(" ")); // spaces only

        // valid addresses
        assertTrue(Zone.isValidZone("Blk 456, Den Road, #01-355"));
        assertTrue(Zone.isValidZone("-")); // one character
        assertTrue(Zone.isValidZone("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long thanepark
    }
}

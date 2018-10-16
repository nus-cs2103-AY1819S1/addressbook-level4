package seedu.address.model.group;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

/**
 * {@author Derek-Hardy}
 */
public class PlaceTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Place(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidPlace = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Place(invalidPlace));
    }

    @Test
    public void isValidPlace() {
        // null address
        Assert.assertThrows(NullPointerException.class, () -> Place.isValidPlace(null));

        // invalid addresses
        assertFalse(Place.isValidPlace("")); // empty string
        assertFalse(Place.isValidPlace(" ")); // spaces only

        // valid addresses
        assertTrue(Place.isValidPlace("COM1, School of Computing"));
        assertTrue(Place.isValidPlace("-")); // one character
        assertTrue(Place.isValidPlace("Basement 1, Block 9, Prince George's Park Residence, NUS")); // long address
    }
}

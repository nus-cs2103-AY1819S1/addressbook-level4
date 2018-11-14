package seedu.parking.model.carpark;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.parking.testutil.Assert;

public class LotsAvailableTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new LotsAvailable(null));
    }

    @Test
    public void constructor_invalidLotsAvailable_throwsIllegalArgumentException() {
        String invalidLotsAvailable = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new LotsAvailable(invalidLotsAvailable));
    }

    @Test
    public void isValidLotsAvailable() {
        // null lots available
        Assert.assertThrows(NullPointerException.class, () -> LotsAvailable.isValidLotsAvailable(null));

        // invalid lots available
        assertFalse(LotsAvailable.isValidLotsAvailable("")); // empty string
        assertFalse(LotsAvailable.isValidLotsAvailable(" ")); // spaces only
        assertFalse(LotsAvailable.isValidLotsAvailable("phone")); // non-numeric
        assertFalse(LotsAvailable.isValidLotsAvailable("9011p041")); // alphabets within digits
        assertFalse(LotsAvailable.isValidLotsAvailable("9312 1534")); // spaces within digits
        assertFalse(LotsAvailable.isValidLotsAvailable("!#$%&'*+/=?`{|}~^.-,")); // special characters

        // valid lots available
        assertTrue(LotsAvailable.isValidLotsAvailable("911"));
        assertTrue(LotsAvailable.isValidLotsAvailable("93121534"));
        assertTrue(LotsAvailable.isValidLotsAvailable("124293842033123")); // long numbers
    }
}

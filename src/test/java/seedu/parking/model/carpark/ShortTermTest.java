package seedu.parking.model.carpark;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.parking.testutil.Assert;

public class ShortTermTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new ShortTerm(null));
    }

    @Test
    public void constructor_invalidShortTerm_throwsIllegalArgumentException() {
        String invalidShortTerm = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new ShortTerm(invalidShortTerm));
    }

    @Test
    public void isValidShortTerm() {
        // null short term parking
        Assert.assertThrows(NullPointerException.class, () -> ShortTerm.isValidShortTerm(null));

        // invalid short term parking
        assertFalse(ShortTerm.isValidShortTerm("")); // empty string
        assertFalse(ShortTerm.isValidShortTerm(" ")); // spaces only

        // valid short term parking
        assertTrue(ShortTerm.isValidShortTerm("WHOLE DAY"));
        assertTrue(ShortTerm.isValidShortTerm("-")); // one character
        assertTrue(ShortTerm.isValidShortTerm("7AM-10.30PM")); // long
    }
}

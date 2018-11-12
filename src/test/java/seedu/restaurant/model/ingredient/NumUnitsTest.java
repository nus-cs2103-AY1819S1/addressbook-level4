package seedu.restaurant.model.ingredient;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import seedu.restaurant.testutil.Assert;

//@@author rebstan97
public class NumUnitsTest {

    @Test
    public void constructor_invalidNumUnits_throwsIllegalArgumentException() {
        int invalidNumUnits = -1;
        Assert.assertThrows(IllegalArgumentException.class, () -> new NumUnits(invalidNumUnits));
    }

    @Test
    public void increase_validNumUnits_assertEquals() {
        NumUnits currentNumUnits = new NumUnits(10);
        NumUnits expectedNumUnits = new NumUnits(22);
        assertEquals(expectedNumUnits, currentNumUnits.increase(12));
    }

    @Test
    public void decrease_validNumUnits_assertEquals() {
        NumUnits currentNumUnits = new NumUnits(22);
        NumUnits expectedNumUnits = new NumUnits(10);
        assertEquals(expectedNumUnits, currentNumUnits.decrease(12));
    }

    @Test
    public void isValidNumUnits() {
        // null number of units
        Assert.assertThrows(NullPointerException.class, () -> NumUnits.isValidNumUnits(null));

        // invalid number of units
        assertFalse(NumUnits.isValidNumUnits("")); // empty string
        assertFalse(NumUnits.isValidNumUnits(" ")); // spaces only
        assertFalse(NumUnits.isValidNumUnits("abcde^")); // only non-numeric characters
        assertFalse(NumUnits.isValidNumUnits("12ab*")); // contains non-numeric characters
        assertFalse(NumUnits.isValidNumUnits("200.2")); // numeric value with decimal separator
        assertFalse(NumUnits.isValidNumUnits(-1)); // negative integer

        // valid number of units
        assertTrue(NumUnits.isValidNumUnits("800")); // numeric value
        assertTrue(NumUnits.isValidNumUnits("28902375639")); // numeric value with many digits
        assertTrue(NumUnits.isValidNumUnits(1)); // small positive integer
        assertTrue(NumUnits.isValidNumUnits(10000)); // large positive integer
        assertTrue(NumUnits.isValidNumUnits(0)); // zero
    }
}

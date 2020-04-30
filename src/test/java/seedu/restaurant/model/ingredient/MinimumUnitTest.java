package seedu.restaurant.model.ingredient;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.restaurant.testutil.Assert;

//@@author rebstan97
public class MinimumUnitTest {

    @Test
    public void constructor_invalidMinimum_throwsIllegalArgumentException() {
        int invalidMinimum = -1;
        Assert.assertThrows(IllegalArgumentException.class, () -> new MinimumUnit(invalidMinimum));
    }

    @Test
    public void isValidMinimum_string() {
        // null minimum
        Assert.assertThrows(NullPointerException.class, () -> MinimumUnit.isValidMinimum(null));

        // invalid minimum
        assertFalse(MinimumUnit.isValidMinimum("")); // empty string
        assertFalse(MinimumUnit.isValidMinimum(" ")); // spaces only
        assertFalse(MinimumUnit.isValidMinimum("abcde^")); // only non-numeric characters
        assertFalse(MinimumUnit.isValidMinimum("12ab*")); // contains non-numeric characters
        assertFalse(MinimumUnit.isValidMinimum("200.2")); // numeric value with decimal separator

        // valid minimum
        assertTrue(MinimumUnit.isValidMinimum("800")); // numeric value
        assertTrue(MinimumUnit.isValidMinimum("28902375639")); // numeric value with many digits
    }

    @Test
    public void isValidMinimum_int() {
        // invalid minimum
        assertFalse(MinimumUnit.isValidMinimum(-1)); // small negative integer
        assertFalse(MinimumUnit.isValidMinimum(-1000)); // large negative integer

        // valid minimum
        assertTrue(MinimumUnit.isValidMinimum(1)); // small positive integer
        assertTrue(MinimumUnit.isValidMinimum(289023)); // large positive integer
        assertTrue(MinimumUnit.isValidMinimum(0)); // zero
    }
}

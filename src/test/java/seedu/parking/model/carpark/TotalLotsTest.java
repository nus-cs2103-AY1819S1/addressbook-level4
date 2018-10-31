package seedu.parking.model.carpark;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.parking.testutil.Assert;

public class TotalLotsTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new TotalLots(null));
    }

    @Test
    public void constructor_invalidTotalLots_throwsIllegalArgumentException() {
        String invalidTotalLots = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new TotalLots(invalidTotalLots));
    }

    @Test
    public void isValidTotalLots() {
        // null total lots
        Assert.assertThrows(NullPointerException.class, () -> TotalLots.isValidTotalLots(null));

        // invalid total lots
        assertFalse(TotalLots.isValidTotalLots("")); // empty string
        assertFalse(TotalLots.isValidTotalLots(" ")); // spaces only
        assertFalse(TotalLots.isValidTotalLots("phone")); // non-numeric
        assertFalse(TotalLots.isValidTotalLots("9011p041")); // alphabets within digits
        assertFalse(TotalLots.isValidTotalLots("9312 1534")); // spaces within digits
        assertFalse(TotalLots.isValidTotalLots("!#$%&'*+/=?`{|}~^.-,")); // special characters

        // valid total lots
        assertTrue(TotalLots.isValidTotalLots("911"));
        assertTrue(TotalLots.isValidTotalLots("93121534"));
        assertTrue(TotalLots.isValidTotalLots("124293842033123")); // long numbers
    }
}

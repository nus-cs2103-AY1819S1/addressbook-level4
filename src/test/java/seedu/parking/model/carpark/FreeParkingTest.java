package seedu.parking.model.carpark;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.parking.testutil.Assert;

public class FreeParkingTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new FreeParking(null));
    }

    @Test
    public void constructor_invalidFreeParking_throwsIllegalArgumentException() {
        String invalidFreeParking = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new FreeParking(invalidFreeParking));
    }

    @Test
    public void isValidFreePark() {
        // null free parking
        Assert.assertThrows(NullPointerException.class, () -> FreeParking.isValidFreePark(null));

        // invalid free parking
        assertFalse(FreeParking.isValidFreePark("")); // empty string
        assertFalse(FreeParking.isValidFreePark(" ")); // spaces only

        // valid free parking
        assertTrue(FreeParking.isValidFreePark("SUN & PH FR 7AM-10.30PM"));
        assertTrue(FreeParking.isValidFreePark("-")); // one character
        assertTrue(FreeParking.isValidFreePark("NO")); // long
    }
}

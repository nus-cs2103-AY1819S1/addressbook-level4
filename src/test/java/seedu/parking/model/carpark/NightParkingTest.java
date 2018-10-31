package seedu.parking.model.carpark;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.parking.testutil.Assert;

public class NightParkingTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new NightParking(null));
    }

    @Test
    public void constructor_invalidNightParking_throwsIllegalArgumentException() {
        String invalidNightParking = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new NightParking(invalidNightParking));
    }

    @Test
    public void isValidNightPark() {
        // null night parking
        Assert.assertThrows(NullPointerException.class, () -> NightParking.isValidNightPark(null));

        // invalid night parking
        assertFalse(NightParking.isValidNightPark("")); // empty string
        assertFalse(NightParking.isValidNightPark(" ")); // spaces only

        // valid night parking
        assertTrue(NightParking.isValidNightPark("NO"));
        assertTrue(NightParking.isValidNightPark("-")); // one character
        assertTrue(NightParking.isValidNightPark("YES")); // long
    }
}

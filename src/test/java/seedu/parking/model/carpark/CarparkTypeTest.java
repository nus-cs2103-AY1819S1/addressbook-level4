package seedu.parking.model.carpark;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.parking.testutil.Assert;

public class CarparkTypeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new CarparkType(null));
    }

    @Test
    public void constructor_invalidCarparkType_throwsIllegalArgumentException() {
        String invalidCarparkType = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new CarparkType(invalidCarparkType));
    }

    @Test
    public void isValidCarType() {
        // null car park type
        Assert.assertThrows(NullPointerException.class, () -> CarparkType.isValidCarType(null));

        // invalid car park type
        assertFalse(CarparkType.isValidCarType("")); // empty string
        assertFalse(CarparkType.isValidCarType(" ")); // spaces only

        // valid car park type
        assertTrue(CarparkType.isValidCarType("COVERED CAR PARK"));
        assertTrue(CarparkType.isValidCarType("-")); // one character
        assertTrue(CarparkType.isValidCarType("SURFACE/MULTI-STOREY CAR PARK")); // long
    }
}

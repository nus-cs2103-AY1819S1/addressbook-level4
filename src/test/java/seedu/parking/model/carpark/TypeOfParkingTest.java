package seedu.parking.model.carpark;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.parking.testutil.Assert;

public class TypeOfParkingTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new TypeOfParking(null));
    }

    @Test
    public void constructor_invalidTypeOfParking_throwsIllegalArgumentException() {
        String invalidTypeOfParking = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new TypeOfParking(invalidTypeOfParking));
    }

    @Test
    public void isValidTypeOfParking() {
        // null type of parking
        Assert.assertThrows(NullPointerException.class, () -> TypeOfParking.isValidTypePark(null));

        // invalid type of parking
        assertFalse(TypeOfParking.isValidTypePark("")); // empty string
        assertFalse(TypeOfParking.isValidTypePark(" ")); // spaces only

        // valid type of parking
        assertTrue(TypeOfParking.isValidTypePark("COUPON PARKING"));
        assertTrue(TypeOfParking.isValidTypePark("-")); // one character
        assertTrue(TypeOfParking.isValidTypePark("ELECTRONIC PARKING")); // long TypeOfParking
    }
}

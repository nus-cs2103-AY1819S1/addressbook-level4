package seedu.parking.model.carpark;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.parking.testutil.Assert;

public class CarparkNumberTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new CarparkNumber(null));
    }

    @Test
    public void constructor_invalidCarparkNumber_throwsIllegalArgumentException() {
        String invalidCarparkName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new CarparkNumber(invalidCarparkName));
    }

    @Test
    public void isValidCarparkNumber() {
        // null car park number
        Assert.assertThrows(NullPointerException.class, () -> CarparkNumber.isValidCarparkNumber(null));

        // invalid car park number
        assertFalse(CarparkNumber.isValidCarparkNumber("")); // empty string
        assertFalse(CarparkNumber.isValidCarparkNumber(" ")); // spaces only
        assertFalse(CarparkNumber.isValidCarparkNumber("^")); // only non-alphanumeric characters
        assertFalse(CarparkNumber.isValidCarparkNumber("a123*")); // contains non-alphanumeric characters
        assertFalse(Coordinate.isValidCoordinate("!#$%&'*+/=?`{|}~^.-,")); // special characters
        assertFalse(CarparkNumber.isValidCarparkNumber("Z12 Z12 Z12")); // alphanumeric characters

        // valid car park number
        assertTrue(CarparkNumber.isValidCarparkNumber("Z12")); // alphanumeric only
        assertTrue(CarparkNumber.isValidCarparkNumber("12345")); // numbers only
        assertTrue(CarparkNumber.isValidCarparkNumber("z12")); // with small letters
        assertTrue(CarparkNumber.isValidCarparkNumber("Z12123124123")); // long number
        assertTrue(CarparkNumber.isValidCarparkNumber("ZAWEAWDAWT16")); // long alphabet
    }
}

package seedu.address.model.occasion;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class OccasionLocationTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new OccasionLocation(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidLocation = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new OccasionLocation(invalidLocation));
    }

    @Test
    public void isValidOccasionLocation() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> OccasionLocation.isValidLocation(null));

        // invalid name
        assertFalse(OccasionLocation.isValidLocation("")); // empty string
        assertFalse(OccasionLocation.isValidLocation(" ")); // spaces only
        assertFalse(OccasionLocation.isValidLocation("^")); // only non-alphanumeric characters
        assertFalse(OccasionLocation.isValidLocation("somewhere*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(OccasionLocation.isValidLocation("somewhere")); // alphabets only
        assertTrue(OccasionLocation.isValidLocation("12345")); // numbers only
        assertTrue(OccasionLocation.isValidLocation("soc 2nd floor")); // alphanumeric characters
        assertTrue(OccasionLocation.isValidLocation("University Town")); // with capital letters
        assertTrue(OccasionLocation.isValidLocation("Super Cold Lecture Theater")); // long names
    }
}

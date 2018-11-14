package seedu.address.model.occasion;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class OccasionNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new OccasionName(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new OccasionName(invalidName));
    }

    @Test
    public void isValidOccasionName() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> OccasionName.isValidName(null));

        // invalid name
        assertFalse(OccasionName.isValidName("")); // empty string
        assertFalse(OccasionName.isValidName(" ")); // spaces only
        assertFalse(OccasionName.isValidName("^")); // only non-alphanumeric characters
        assertFalse(OccasionName.isValidName("meeting*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(OccasionName.isValidName("project meeting")); // alphabets only
        assertTrue(OccasionName.isValidName("12345")); // numbers only
        assertTrue(OccasionName.isValidName("2nd meeting")); // alphanumeric characters
        assertTrue(OccasionName.isValidName("Project Meeting")); // with capital letters
        assertTrue(OccasionName.isValidName("Project which drives me crazy")); // long names
    }
}

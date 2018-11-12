package seedu.address.model.project;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class AssignmentNameTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new AssignmentName(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new AssignmentName(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> AssignmentName.isValidName(null));

        // invalid name
        assertFalse(AssignmentName.isValidName("")); // empty string
        assertFalse(AssignmentName.isValidName(" ")); // spaces only
        assertFalse(AssignmentName.isValidName("^")); // only non-alphanumeric characters
        assertFalse(AssignmentName.isValidName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(AssignmentName.isValidName("OASIS")); // alphabets only
        assertTrue(AssignmentName.isValidName("12345")); // numbers only
        assertTrue(AssignmentName.isValidName("FALCON V3")); // alphanumeric characters
        assertTrue(AssignmentName.isValidName("Capital Lan")); // with capital letters
        assertTrue(AssignmentName.isValidName("Quadratini Management System")); // long names
    }
}

package seedu.address.model.assignment;

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
    public void constructor_invalidAssignmentName_throwsIllegalArgumentException() {
        String invalidAssignmentName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new AssignmentName(invalidAssignmentName));
    }

    @Test
    public void isValidAssignmentName() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> AssignmentName.isValid(null));

        // invalid assignment name
        assertFalse(AssignmentName.isValid("")); // empty string
        assertFalse(AssignmentName.isValid(" ")); // spaces only

        // valid assignment name
        assertTrue(AssignmentName.isValid("^")); // only non-alphanumeric characters
        assertTrue(AssignmentName.isValid("a\t,.$#%^&1*")); // contains non-alphanumeric characters
        assertTrue(AssignmentName.isValid("aa")); // alphabets only
        assertTrue(AssignmentName.isValid("12345")); // numbers only
        assertTrue(AssignmentName.isValid("a1")); // alphanumeric characters
        assertTrue(AssignmentName.isValid("AbCD")); // with capital letters
        assertTrue(AssignmentName.isValid("Task from David Roger Jackson Ray Jr 2nd")); // long names
    }
}

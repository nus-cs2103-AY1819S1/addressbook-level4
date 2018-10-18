package seedu.address.model.project;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class ProjectNameTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new ProjectName(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new ProjectName(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> ProjectName.isValidName(null));

        // invalid name
        assertFalse(ProjectName.isValidName("")); // empty string
        assertFalse(ProjectName.isValidName(" ")); // spaces only
        assertFalse(ProjectName.isValidName("^")); // only non-alphanumeric characters
        assertFalse(ProjectName.isValidName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(ProjectName.isValidName("OASIS")); // alphabets only
        assertTrue(ProjectName.isValidName("12345")); // numbers only
        assertTrue(ProjectName.isValidName("FALCON V3")); // alphanumeric characters
        assertTrue(ProjectName.isValidName("Capital Lan")); // with capital letters
        assertTrue(ProjectName.isValidName("Quadratini Management System")); // long names
    }
}

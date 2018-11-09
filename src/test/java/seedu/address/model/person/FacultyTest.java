package seedu.address.model.person;

import seedu.address.testutil.Assert;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

class FacultyTest {

    @Test
    public void constructor_requireNonNullError_ExceptionThrownTest() {
        Assert.assertThrows(NullPointerException.class, () -> new Faculty(null));
    }

    @Test
    public void constructor_emptySpaceInput_ExceptionThrownTest() {
        String invalidFaculty = " ";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Faculty(invalidFaculty));
    }

    @Test
    void isValidFaculty() {
        // invalid name
        assertFalse(Faculty.isValidFaculty("")); // empty string
        assertFalse(Faculty.isValidFaculty(" ")); // spaces only
        assertFalse(Faculty.isValidFaculty("@")); // only non-alphanumeric characters
        assertFalse(Faculty.isValidFaculty("SOC*")); // contains non-alphanumeric characters
        assertFalse(Faculty.isValidFaculty("COM")); // unconventional faculty naming

        // valid name
        assertTrue(Faculty.isValidFaculty("SDE")); // in enum
        assertTrue(Faculty.isValidFaculty("FASS")); // in enum
        assertTrue(Faculty.isValidFaculty("ystcom")); // caps do not matter
        assertTrue(Faculty.isValidFaculty("-")); // dash input for those who have no faculty
    }

    @Test
    void equals() {
    }

}
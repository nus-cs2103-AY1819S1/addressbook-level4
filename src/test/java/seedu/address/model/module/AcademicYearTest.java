package seedu.address.model.module;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class AcademicYearTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new AcademicYear(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new AcademicYear(invalidName));
    }

    @Test
    public void isValidAcademicYear() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> AcademicYear.isValidYear(null));

        // invalid name
        assertFalse(AcademicYear.isValidYear("")); // empty string
        assertFalse(AcademicYear.isValidYear(" ")); // spaces only
        assertFalse(AcademicYear.isValidYear("^")); // only non-alphanumeric characters
        assertFalse(AcademicYear.isValidYear("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(AcademicYear.isValidYear("1718")); // alphabets only
        assertTrue(AcademicYear.isValidYear("1213")); // numbers only
    }

    @Test
    public void isEqualsAcademicYear() {
        AcademicYear year = new AcademicYear("1819");
        AcademicYear secondYear = new AcademicYear("1819");
        assertTrue(year.equals(secondYear));
    }
}

package seedu.address.model.module;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class SemesterTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Semester(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Semester(invalidName));
    }

    @Test
    public void isValidModuleTitle() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> Semester.isValidSemester(null));

        // invalid name
        assertFalse(Semester.isValidSemester("")); // empty string
        assertFalse(Semester.isValidSemester(" ")); // spaces only
        assertFalse(Semester.isValidSemester("^")); // only non-alphanumeric characters
        assertFalse(Semester.isValidSemester("peter*")); // contains non-alphanumeric characters
        assertFalse(Semester.isValidSemester("5"));
        assertFalse(Semester.isValidSemester("22"));
        assertFalse(Semester.isValidSemester("daddy"));

        // valid name
        assertTrue(Semester.isValidSemester("1")); // alphabets only
        assertTrue(Semester.isValidSemester("2")); // numbers only
    }

    @Test
    public void isEquals() {
        AcademicYear year = new AcademicYear("1819");
        AcademicYear secondYear = new AcademicYear("1819");
        assertTrue(year.equals(secondYear));
    }
}

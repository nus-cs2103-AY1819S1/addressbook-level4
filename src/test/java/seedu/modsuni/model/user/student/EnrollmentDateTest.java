package seedu.modsuni.model.user.student;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.modsuni.logic.commands.CommandTestUtil.INVALID_ENROLLMENT_DESC;
import static seedu.modsuni.logic.commands.CommandTestUtil.VALID_ENROLLMENT;
import static seedu.modsuni.testutil.TypicalUsers.STUDENT_MAX;
import static seedu.modsuni.testutil.TypicalUsers.STUDENT_SEB;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.modsuni.testutil.Assert;

public class EnrollmentDateTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void is_validEnrollmentDate() {
        // null enrollment date
        Assert.assertThrows(NullPointerException.class, () -> EnrollmentDate.isValidEnrollmentDate(null));

        // valid enrollment date -> returns true
        assertTrue(EnrollmentDate.isValidEnrollmentDate(VALID_ENROLLMENT));

        // invalid enrollment date -> returns false
        assertFalse(EnrollmentDate.isValidEnrollmentDate(INVALID_ENROLLMENT_DESC));
        assertFalse(EnrollmentDate.isValidEnrollmentDate("")); // empty string
        assertFalse(EnrollmentDate.isValidEnrollmentDate(" ")); // spaces only
        assertFalse(EnrollmentDate.isValidEnrollmentDate("^")); // only
        // non-alphanumeric
        // characters
        assertFalse(EnrollmentDate.isValidEnrollmentDate("peter*")); //
        // contains
        // non-alphanumeric characters
        assertFalse(EnrollmentDate.isValidEnrollmentDate("12082014")); //no
        // slashes
    }

    @Test
    public void test_hashCode () {
        EnrollmentDate maxEnrollment = STUDENT_MAX.getEnrollmentDate();

        // same credential
        EnrollmentDate maxEnrollmentCopy = new EnrollmentDate(maxEnrollment.enrollmentDate);
        assertTrue(maxEnrollment.hashCode() == (maxEnrollmentCopy.hashCode()));

        // diff credential
        assertFalse(maxEnrollment.hashCode() == (STUDENT_SEB.getEnrollmentDate().hashCode()));
    }
}

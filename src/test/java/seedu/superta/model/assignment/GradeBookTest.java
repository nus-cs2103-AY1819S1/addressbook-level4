package seedu.superta.model.assignment;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static seedu.superta.testutil.TypicalSuperTaClient.ALICE;
import static seedu.superta.testutil.TypicalSuperTaClient.BENSON;
import static seedu.superta.testutil.TypicalSuperTaClient.CARL;

import java.util.List;

import org.junit.Test;

public class GradeBookTest {
    private GradeBook gradeBook = new GradeBook();

    @Test
    public void isStudentIn_success() {
        gradeBook.addGrade(BENSON.getStudentId(), 10.0);
        assertTrue(gradeBook.isStudentIn(BENSON));
    }

    @Test
    public void isStudentIn_failure() {
        // empty grade book should return false
        assertFalse(gradeBook.isStudentIn(BENSON));
    }

    @Test
    public void removeStudentReference_success() {
        gradeBook.addGrade(BENSON.getStudentId(), 10.0);
        gradeBook.removeStudentReference(BENSON);
        assertFalse(gradeBook.isStudentIn(BENSON));
    }

    @Test
    public void getAverage_success() {
        gradeBook.addGrade(BENSON.getStudentId(), 10.0);
        gradeBook.addGrade(ALICE.getStudentId(), 20.0);
        assertTrue(gradeBook.getAverage() == 15.0);
    }

    @Test
    public void getMedian_success() {
        gradeBook.addGrade(BENSON.getStudentId(), 10.0);
        gradeBook.addGrade(ALICE.getStudentId(), 11.0);
        gradeBook.addGrade(CARL.getStudentId(), 1000.0);
        assertTrue(gradeBook.getMedian() == 11.0);
    }

    @Test
    public void asUnmodifiableList_success() {
        List<GradeEntry> entries = gradeBook.asUnmodifiableObservableList();
        assertTrue(entries.size() == 0);
        gradeBook.addGrade(BENSON.getStudentId(), 10.0);
        assertTrue(entries.size() == 1);
        gradeBook.addGrade(BENSON.getStudentId(), 20.0);
        assertTrue(entries.size() == 1);
        gradeBook.removeStudentReference(BENSON);
        assertTrue(entries.size() == 0);
    }

}

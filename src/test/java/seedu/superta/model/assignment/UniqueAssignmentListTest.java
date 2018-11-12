package seedu.superta.model.assignment;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static seedu.superta.testutil.TypicalAssignments.LAB;
import static seedu.superta.testutil.TypicalAssignments.TUT;

import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.superta.model.assignment.exceptions.AssignmentNotFoundException;
import seedu.superta.model.assignment.exceptions.DuplicateAssignmentException;
import seedu.superta.model.assignment.exceptions.DuplicateAssignmentNameException;


public class UniqueAssignmentListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueAssignmentList uniqueAssignmentList = new UniqueAssignmentList();

    @Test
    public void contains_nullAssignment_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueAssignmentList.contains(null);
    }

    @Test
    public void contains_assignmentNotInList_returnsFalse() {
        assertFalse(uniqueAssignmentList.contains(TUT));
    }

    @Test
    public void contains_assignmentInList_returnsTrue() {
        uniqueAssignmentList.add(TUT);
        assertTrue(uniqueAssignmentList.contains(TUT));
    }

    @Test
    public void add_nullAssignment_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueAssignmentList.add(null);
    }

    @Test
    public void add_duplicateAssignment_throwsDuplicatePersonException() {
        uniqueAssignmentList.add(TUT);
        thrown.expect(DuplicateAssignmentException.class);
        uniqueAssignmentList.add(TUT);
    }

    @Test
    public void setAssignment_nullTargetAssignment_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueAssignmentList.setAssignment(null, TUT);
    }

    @Test
    public void setAssignment_nullEditedAssignment_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueAssignmentList.setAssignment(TUT, null);
    }

    @Test
    public void setAssignment_targetAssignmentNotInList_throwsAssignmentNotFoundException() {
        thrown.expect(AssignmentNotFoundException.class);
        uniqueAssignmentList.setAssignment(TUT, TUT);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueAssignmentList.add(TUT);
        uniqueAssignmentList.add(LAB);
        thrown.expect(DuplicateAssignmentNameException.class);
        uniqueAssignmentList.setAssignment(TUT, LAB);
    }

    @Test
    public void remove_nullAssignment_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueAssignmentList.remove(null);
    }

    @Test
    public void remove_assignmentDoesNotExist_throwsAssignmentNotFoundException() {
        thrown.expect(AssignmentNotFoundException.class);
        uniqueAssignmentList.remove(TUT);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueAssignmentList.setAssignments((UniqueAssignmentList) null);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueAssignmentList.setAssignments((List<Assignment>) null);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Assignment> listWithDuplicateStudents = Arrays.asList(TUT, TUT);
        thrown.expect(DuplicateAssignmentException.class);
        uniqueAssignmentList.setAssignments(listWithDuplicateStudents);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueAssignmentList.asUnmodifiableObservableList().remove(0);
    }
}

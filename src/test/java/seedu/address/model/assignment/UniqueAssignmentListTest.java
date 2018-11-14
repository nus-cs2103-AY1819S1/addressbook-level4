package seedu.address.model.assignment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_B;
import static seedu.address.testutil.TypicalAssignments.ASSIGNMENT_A;
import static seedu.address.testutil.TypicalAssignments.ASSIGNMENT_B;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.assignment.exceptions.AssignmentNotFoundException;
import seedu.address.model.assignment.exceptions.DuplicateAssignmentException;
import seedu.address.testutil.AssignmentBuilder;

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
        assertFalse(uniqueAssignmentList.contains(ASSIGNMENT_A));
    }

    @Test
    public void contains_assignmentInList_returnsTrue() {
        uniqueAssignmentList.add(ASSIGNMENT_A);
        assertTrue(uniqueAssignmentList.contains(ASSIGNMENT_A));
    }

    @Test
    public void contains_assignmentWithSameIdentityFieldsInList_returnsTrue() {
        uniqueAssignmentList.add(ASSIGNMENT_A);
        Assignment editedA = new AssignmentBuilder(ASSIGNMENT_A).withDeadline(VALID_DEADLINE_B)
                .build();
        assertTrue(uniqueAssignmentList.contains(editedA));
    }

    @Test
    public void add_nullAssignment_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueAssignmentList.add(null);
    }

    @Test
    public void add_duplicateAssignment_throwsDuplicateAssignmentException() {
        uniqueAssignmentList.add(ASSIGNMENT_A);
        thrown.expect(DuplicateAssignmentException.class);
        uniqueAssignmentList.add(ASSIGNMENT_A);
    }

    @Test
    public void setAssignment_nullTargetAssignment_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueAssignmentList.setAssignment(null, ASSIGNMENT_A);
    }

    @Test
    public void setAssignment_nullEditedAssignment_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueAssignmentList.setAssignment(ASSIGNMENT_A, null);
    }

    @Test
    public void setAssignment_targetAssignmentNotInList_throwsAssignmentNotFoundException() {
        thrown.expect(AssignmentNotFoundException.class);
        uniqueAssignmentList.setAssignment(ASSIGNMENT_A, ASSIGNMENT_A);
    }

    @Test
    public void setAssignment_editedAssignmentIsSameAssignment_success() {
        uniqueAssignmentList.add(ASSIGNMENT_A);
        uniqueAssignmentList.setAssignment(ASSIGNMENT_A, ASSIGNMENT_A);
        UniqueAssignmentList expectedUniqueAssignmentList = new UniqueAssignmentList();
        expectedUniqueAssignmentList.add(ASSIGNMENT_A);
        assertEquals(expectedUniqueAssignmentList, uniqueAssignmentList);
    }

    @Test
    public void setAssignment_editedAssignmentHasSameIdentity_success() {
        uniqueAssignmentList.add(ASSIGNMENT_A);
        Assignment editedA = new AssignmentBuilder(ASSIGNMENT_A).withDeadline(VALID_DEADLINE_B)
                .build();
        uniqueAssignmentList.setAssignment(ASSIGNMENT_A, editedA);
        UniqueAssignmentList expectedUniqueAssignmentList = new UniqueAssignmentList();
        expectedUniqueAssignmentList.add(editedA);
        assertEquals(expectedUniqueAssignmentList, uniqueAssignmentList);
    }

    @Test
    public void setAssignment_editedAssignmentHasDifferentIdentity_success() {
        uniqueAssignmentList.add(ASSIGNMENT_A);
        uniqueAssignmentList.setAssignment(ASSIGNMENT_A, ASSIGNMENT_B);
        UniqueAssignmentList expectedUniqueAssignmentList = new UniqueAssignmentList();
        expectedUniqueAssignmentList.add(ASSIGNMENT_B);
        assertEquals(expectedUniqueAssignmentList, uniqueAssignmentList);
    }

    @Test
    public void setAssignment_editedAssignmentHasNonUniqueIdentity_throwsDuplicateAssignmentException() {
        uniqueAssignmentList.add(ASSIGNMENT_A);
        uniqueAssignmentList.add(ASSIGNMENT_B);
        thrown.expect(DuplicateAssignmentException.class);
        uniqueAssignmentList.setAssignment(ASSIGNMENT_A, ASSIGNMENT_B);
    }

    @Test
    public void remove_nullAssignment_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueAssignmentList.remove(null);
    }

    @Test
    public void remove_personDoesNotExist_throwsAssignmentNotFoundException() {
        thrown.expect(AssignmentNotFoundException.class);
        uniqueAssignmentList.remove(ASSIGNMENT_A);
    }

    @Test
    public void remove_existingAssignment_removesAssignment() {
        uniqueAssignmentList.add(ASSIGNMENT_A);
        uniqueAssignmentList.remove(ASSIGNMENT_A);
        UniqueAssignmentList expectedUniqueAssignmentList = new UniqueAssignmentList();
        assertEquals(expectedUniqueAssignmentList, uniqueAssignmentList);
    }

    @Test
    public void setAssignments_nullUniqueAssignmentList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueAssignmentList.setAssignments((UniqueAssignmentList) null);
    }

    @Test
    public void setAssignments_uniqueAssignmentList_replacesOwnListWithProvidedUniqueAssignmentList() {
        uniqueAssignmentList.add(ASSIGNMENT_A);
        UniqueAssignmentList expectedUniqueAssignmentList = new UniqueAssignmentList();
        expectedUniqueAssignmentList.add(ASSIGNMENT_B);
        uniqueAssignmentList.setAssignments(expectedUniqueAssignmentList);
        assertEquals(expectedUniqueAssignmentList, uniqueAssignmentList);
    }

    @Test
    public void setAssignments_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueAssignmentList.setAssignments((List<Assignment>) null);
    }

    @Test
    public void setAssignments_list_replacesOwnListWithProvidedList() {
        uniqueAssignmentList.add(ASSIGNMENT_A);
        List<Assignment> personList = Collections.singletonList(ASSIGNMENT_B);
        uniqueAssignmentList.setAssignments(personList);
        UniqueAssignmentList expectedUniqueAssignmentList = new UniqueAssignmentList();
        expectedUniqueAssignmentList.add(ASSIGNMENT_B);
        assertEquals(expectedUniqueAssignmentList, uniqueAssignmentList);
    }

    @Test
    public void setAssignments_listWithDuplicateAssignments_throwsDuplicateAssignmentException() {
        List<Assignment> listWithDuplicateAssignments = Arrays.asList(ASSIGNMENT_A, ASSIGNMENT_A);
        thrown.expect(DuplicateAssignmentException.class);
        uniqueAssignmentList.setAssignments(listWithDuplicateAssignments);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueAssignmentList.asUnmodifiableObservableList().remove(0);
    }
}

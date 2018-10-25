package seedu.address.model.project;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.testutil.TypicalAssignment.FALCON;
import static seedu.address.testutil.TypicalAssignment.OASIS;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.project.exceptions.AssignmentNotFoundException;
import seedu.address.model.project.exceptions.DuplicateAssignmentException;
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
        assertFalse(uniqueAssignmentList.contains(OASIS));
    }

    @Test
    public void contains_assignmentInList_returnsTrue() {
        uniqueAssignmentList.add(OASIS);
        assertTrue(uniqueAssignmentList.contains(OASIS));
    }

    @Test
    public void contains_assignmentWithSameIdentityFieldsInList_returnsTrue() {
        uniqueAssignmentList.add(OASIS);
        Assignment editedOasis = new AssignmentBuilder(OASIS).withAuthor(VALID_NAME_AMY)
                .build();
        assertTrue(uniqueAssignmentList.contains(editedOasis));
    }

    @Test
    public void add_nullAssignment_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueAssignmentList.add(null);
    }


    @Test
    public void setAssignment_nullTargetAssignment_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueAssignmentList.setAssignment(null, OASIS);
    }

    @Test
    public void setAssignment_nullEditedAssignment_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueAssignmentList.setAssignment(OASIS, null);
    }

    @Test
    public void setAssignment_targetAssignmentNotInList_throwsAssignmentNotFoundException() {
        thrown.expect(AssignmentNotFoundException.class);
        uniqueAssignmentList.setAssignment(OASIS, OASIS);
    }

    @Test
    public void setAssignment_editedAssignmentIsSameAssignment_success() {
        uniqueAssignmentList.add(OASIS);
        uniqueAssignmentList.setAssignment(OASIS, OASIS);
        UniqueAssignmentList expectedUniqueAssignmentList = new UniqueAssignmentList();
        expectedUniqueAssignmentList.add(OASIS);
        assertEquals(expectedUniqueAssignmentList, uniqueAssignmentList);
    }

    @Test
    public void setAssignment_editedAssignmentHasSameIdentity_success() {
        uniqueAssignmentList.add(OASIS);
        Assignment editedOasis = new AssignmentBuilder(OASIS).withAuthor(VALID_NAME_BOB)
                .build();
        uniqueAssignmentList.setAssignment(OASIS, editedOasis);
        UniqueAssignmentList expectedUniqueAssignmentList = new UniqueAssignmentList();
        expectedUniqueAssignmentList.add(editedOasis);
        assertEquals(expectedUniqueAssignmentList, uniqueAssignmentList);
    }

    @Test
    public void setAssignment_editedAssignmentHasDifferentIdentity_success() {
        uniqueAssignmentList.add(OASIS);
        uniqueAssignmentList.setAssignment(OASIS, FALCON);
        UniqueAssignmentList expectedUniqueAssignmentList = new UniqueAssignmentList();
        expectedUniqueAssignmentList.add(FALCON);
        assertEquals(expectedUniqueAssignmentList, uniqueAssignmentList);
    }

    @Test
    public void remove_nullAssignment_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueAssignmentList.remove(null);
    }

    @Test
    public void remove_assignmentDoesNotExist_throwsAssignmentNotFoundException() {
        thrown.expect(AssignmentNotFoundException.class);
        uniqueAssignmentList.remove(OASIS);
    }

    @Test
    public void remove_existingAssignment_removesAssignment() {
        uniqueAssignmentList.add(OASIS);
        uniqueAssignmentList.remove(OASIS);
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
        uniqueAssignmentList.add(OASIS);
        UniqueAssignmentList expectedUniqueAssignmentList = new UniqueAssignmentList();
        expectedUniqueAssignmentList.add(FALCON);
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
        uniqueAssignmentList.add(OASIS);
        List<Assignment> assignmentList = Collections.singletonList(FALCON);
        uniqueAssignmentList.setAssignments(assignmentList);
        UniqueAssignmentList expectedUniqueAssignmentList = new UniqueAssignmentList();
        expectedUniqueAssignmentList.add(FALCON);
        assertEquals(expectedUniqueAssignmentList, uniqueAssignmentList);
    }

    @Test
    public void setAssignments_listWithDuplicateAssignments_throwsDuplicateAssignmentException() {
        List<Assignment> listWithDuplicateAssignments = Arrays.asList(OASIS, OASIS);
        thrown.expect(DuplicateAssignmentException.class);
        uniqueAssignmentList.setAssignments(listWithDuplicateAssignments);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueAssignmentList.asUnmodifiableObservableList().remove(0);
    }
}

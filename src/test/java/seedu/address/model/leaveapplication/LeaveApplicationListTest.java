package seedu.address.model.leaveapplication;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalLeaveApplicationWithEmployees.ALICE_LEAVE_WITH_EMP;
import static seedu.address.testutil.TypicalLeaveApplicationWithEmployees.BENSON_LEAVE_WITH_EMP;

import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.leaveapplication.exceptions.LeaveNotFoundException;

public class LeaveApplicationListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final LeaveApplicationList leaveApplicationList = new LeaveApplicationList();

    @Test
    public void contains_nullLeaveApplication_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        leaveApplicationList.contains(null);
    }

    @Test
    public void contains_leaveApplicationNotInList_returnsFalse() {
        assertFalse(leaveApplicationList.contains(ALICE_LEAVE_WITH_EMP));
    }

    @Test
    public void contains_leaveApplicationInList_returnsTrue() {
        leaveApplicationList.add(ALICE_LEAVE_WITH_EMP);
        assertTrue(leaveApplicationList.contains(ALICE_LEAVE_WITH_EMP));
    }

    @Test
    public void add_nullLeaveApplication_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        leaveApplicationList.add(null);
    }

    @Test
    public void setLeaveApplication_nullTargetLeaveApplication_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        leaveApplicationList.setLeaveApplication(null, ALICE_LEAVE_WITH_EMP);
    }

    @Test
    public void setLeaveApplication_nullEditedLeaveApplication_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        leaveApplicationList.setLeaveApplication(ALICE_LEAVE_WITH_EMP, null);
    }

    @Test
    public void setLeaveApplication_targetLeaveApplicationNotInList_throwsLeaveNotFoundException() {
        thrown.expect(LeaveNotFoundException.class);
        leaveApplicationList.setLeaveApplication(ALICE_LEAVE_WITH_EMP, ALICE_LEAVE_WITH_EMP);
    }

    @Test
    public void setLeaveApplication_editedLeaveApplicationIsSameLeaveApplication_success() {
        leaveApplicationList.add(ALICE_LEAVE_WITH_EMP);
        leaveApplicationList.setLeaveApplication(ALICE_LEAVE_WITH_EMP, ALICE_LEAVE_WITH_EMP);
        LeaveApplicationList expectedUniqueLeaveApplicationList = new LeaveApplicationList();
        expectedUniqueLeaveApplicationList.add(ALICE_LEAVE_WITH_EMP);
        assertEquals(expectedUniqueLeaveApplicationList, leaveApplicationList);
    }

    @Test
    public void setLeaveApplication_editedLeaveApplicationHasDifferentIdentity_success() {
        leaveApplicationList.add(ALICE_LEAVE_WITH_EMP);
        leaveApplicationList.setLeaveApplication(ALICE_LEAVE_WITH_EMP, BENSON_LEAVE_WITH_EMP);
        LeaveApplicationList expectedUniqueLeaveApplicationList = new LeaveApplicationList();
        expectedUniqueLeaveApplicationList.add(BENSON_LEAVE_WITH_EMP);
        assertEquals(expectedUniqueLeaveApplicationList, leaveApplicationList);
    }

    @Test
    public void remove_nullLeaveApplication_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        leaveApplicationList.remove(null);
    }

    @Test
    public void remove_leaveApplicationDoesNotExist_throwsLeaveApplicationNotFoundException() {
        thrown.expect(LeaveNotFoundException.class);
        leaveApplicationList.remove(ALICE_LEAVE_WITH_EMP);
    }

    @Test
    public void remove_existingLeaveApplication_removesLeaveApplication() {
        leaveApplicationList.add(ALICE_LEAVE_WITH_EMP);
        leaveApplicationList.remove(ALICE_LEAVE_WITH_EMP);
        LeaveApplicationList expectedUniqueLeaveApplicationList = new LeaveApplicationList();
        assertEquals(expectedUniqueLeaveApplicationList, leaveApplicationList);
    }

    @Test
    public void setLeaveApplications_nullUniqueLeaveApplicationList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        leaveApplicationList.setLeaveApplications((LeaveApplicationList) null);
    }

    @Test
    public void setLeaveApplications_leaveApplicationList_replacesOwnListWithProvidedUniqueLeaveApplicationList() {
        leaveApplicationList.add(ALICE_LEAVE_WITH_EMP);
        LeaveApplicationList expectedUniqueLeaveApplicationList = new LeaveApplicationList();
        expectedUniqueLeaveApplicationList.add(BENSON_LEAVE_WITH_EMP);
        leaveApplicationList.setLeaveApplications(expectedUniqueLeaveApplicationList);
        assertEquals(expectedUniqueLeaveApplicationList, leaveApplicationList);
    }

    @Test
    public void setLeaveApplications_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        leaveApplicationList.setLeaveApplications((List<LeaveApplicationWithEmployee>) null);
    }

    @Test
    public void setLeaveApplications_list_replacesOwnListWithProvidedList() {
        leaveApplicationList.add(ALICE_LEAVE_WITH_EMP);
        List<LeaveApplicationWithEmployee> otherLeaveApplicationList = Collections.singletonList(BENSON_LEAVE_WITH_EMP);
        leaveApplicationList.setLeaveApplications(otherLeaveApplicationList);
        LeaveApplicationList expectedUniqueLeaveApplicationList = new LeaveApplicationList();
        expectedUniqueLeaveApplicationList.add(BENSON_LEAVE_WITH_EMP);
        assertEquals(expectedUniqueLeaveApplicationList, leaveApplicationList);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        leaveApplicationList.asUnmodifiableObservableList().remove(0);
    }
}

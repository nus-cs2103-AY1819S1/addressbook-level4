package seedu.address.model.leaveapplication;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BOB_LEAVE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEAVEDATE_BOB_LEAVE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEAVEID_BOB_LEAVE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEAVESTATUS_BOB_LEAVE;
import static seedu.address.testutil.TypicalLeaveApplications.ALICE_LEAVE;
import static seedu.address.testutil.TypicalLeaveApplications.BENSON_LEAVE;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.LeaveApplicationBuilder;

public class LeaveApplicationTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        LeaveApplication leaveApplication = new LeaveApplicationBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        leaveApplication.getDates().remove(0);
    }

    @Test
    public void equals() {
        // same values -> returns true
        LeaveApplication aliceCopy = new LeaveApplicationBuilder(ALICE_LEAVE).build();
        assertTrue(ALICE_LEAVE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE_LEAVE.equals(ALICE_LEAVE));

        // null -> returns false
        assertFalse(ALICE_LEAVE.equals(null));

        // different type -> returns false
        assertFalse(ALICE_LEAVE.equals(5));

        // different leave application -> returns false
        assertFalse(ALICE_LEAVE.equals(BENSON_LEAVE));

        // different id -> returns false
        LeaveApplication editedAlice = new LeaveApplicationBuilder(ALICE_LEAVE)
                .withId(VALID_LEAVEID_BOB_LEAVE).build();
        assertFalse(ALICE_LEAVE.equals(editedAlice));

        // different description -> returns false
        editedAlice = new LeaveApplicationBuilder(ALICE_LEAVE).withDescription(VALID_DESCRIPTION_BOB_LEAVE).build();
        assertFalse(ALICE_LEAVE.equals(editedAlice));

        // different status -> returns false
        editedAlice = new LeaveApplicationBuilder(ALICE_LEAVE).withStatus(VALID_LEAVESTATUS_BOB_LEAVE).build();
        assertFalse(ALICE_LEAVE.equals(editedAlice));

        // different dates -> returns false
        editedAlice = new LeaveApplicationBuilder(ALICE_LEAVE).withDates(VALID_LEAVEDATE_BOB_LEAVE).build();
        assertFalse(ALICE_LEAVE.equals(editedAlice));
    }
}

package seedu.address.model.leaveapplication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BOB_LEAVE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEAVEDATE_BOB_LEAVE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEAVESTATUS_BOB_LEAVE;
import static seedu.address.testutil.TypicalLeaveApplicationWithEmployees.ALICE_LEAVE_WITH_EMP;
import static seedu.address.testutil.TypicalLeaveApplicationWithEmployees.BENSON_LEAVE_WITH_EMP;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.time.LocalDate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.LeaveApplicationWithEmployeeBuilder;

public class LeaveApplicationWithEmployeeTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        LeaveApplicationWithEmployee leaveApplication = new LeaveApplicationWithEmployeeBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        leaveApplication.getDates().remove(0);
    }

    @Test
    public void equals() {
        // same values -> returns true
        LeaveApplicationWithEmployee aliceCopy = new LeaveApplicationWithEmployeeBuilder(ALICE_LEAVE_WITH_EMP).build();
        assertTrue(ALICE_LEAVE_WITH_EMP.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE_LEAVE_WITH_EMP.equals(ALICE_LEAVE_WITH_EMP));

        // null -> returns false
        assertFalse(ALICE_LEAVE_WITH_EMP.equals(null));

        // different type -> returns false
        assertFalse(ALICE_LEAVE_WITH_EMP.equals(5));

        // different leave application -> returns false
        assertFalse(ALICE_LEAVE_WITH_EMP.equals(BENSON_LEAVE_WITH_EMP));

        // different description -> returns false
        LeaveApplicationWithEmployee editedAlice = new LeaveApplicationWithEmployeeBuilder(ALICE_LEAVE_WITH_EMP)
                .withDescription(VALID_DESCRIPTION_BOB_LEAVE).build();
        assertFalse(ALICE_LEAVE_WITH_EMP.equals(editedAlice));

        // different status -> returns false
        editedAlice = new LeaveApplicationWithEmployeeBuilder(ALICE_LEAVE_WITH_EMP)
                .withStatus(VALID_LEAVESTATUS_BOB_LEAVE).build();
        assertFalse(ALICE_LEAVE_WITH_EMP.equals(editedAlice));

        // different dates -> returns false
        editedAlice = new LeaveApplicationWithEmployeeBuilder(ALICE_LEAVE_WITH_EMP)
                .withDates(VALID_LEAVEDATE_BOB_LEAVE).build();
        assertFalse(ALICE_LEAVE_WITH_EMP.equals(editedAlice));

        // different employee -> returns false
        editedAlice = new LeaveApplicationWithEmployeeBuilder(ALICE_LEAVE_WITH_EMP)
                .withEmployee(BENSON).build();
        assertFalse(ALICE_LEAVE_WITH_EMP.equals(editedAlice));
    }

    @Test
    public void toStringTest() {
        // same expected and actual toString() output for TypicalLeaveApplicationWithEmployee's ALICE_LEAVE_WITH_EMP
        StringBuilder builder = new StringBuilder();
        builder.append(" Description: ")
                .append("Alice family holiday")
                .append(" Status: ")
                .append(StatusEnum.Status.PENDING.toString())
                .append(" Dates: ")
                .append(LocalDate.of(2018, 10, 23).toString())
                .append(LocalDate.of(2018, 10, 24).toString())
                .append(" Employee: ")
                .append(ALICE.toString());
        assertEquals(ALICE_LEAVE_WITH_EMP.toString(), builder.toString());

        // same expected and actual toString() output for TypicalLeaveApplicationWithEmployee's BENSON_LEAVE_WITH_EMP
        builder = new StringBuilder();
        builder.append(" Description: ")
                .append("Benson's brother's wedding")
                .append(" Status: ")
                .append(StatusEnum.Status.PENDING.toString())
                .append(" Dates: ")
                .append(LocalDate.of(2018, 10, 25).toString())
                .append(LocalDate.of(2018, 10, 26).toString())
                .append(" Employee: ")
                .append(BENSON.toString());
        assertEquals(BENSON_LEAVE_WITH_EMP.toString(), builder.toString());
    }
}

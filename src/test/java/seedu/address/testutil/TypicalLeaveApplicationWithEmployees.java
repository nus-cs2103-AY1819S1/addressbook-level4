package seedu.address.testutil;

import static seedu.address.testutil.TypicalLeaveApplications.ALICE_LEAVE;
import static seedu.address.testutil.TypicalLeaveApplications.BENSON_LEAVE;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.leaveapplication.LeaveApplicationWithEmployee;

/**
 * A utility class containing a list of {@code LeaveApplicationWithEmployee} objects to be used in tests.
 */
public class TypicalLeaveApplicationWithEmployees {
    public static final LeaveApplicationWithEmployee ALICE_LEAVE_WITH_EMP =
            new LeaveApplicationWithEmployeeBuilder(ALICE_LEAVE, ALICE).build();
    public static final LeaveApplicationWithEmployee BENSON_LEAVE_WITH_EMP =
            new LeaveApplicationWithEmployeeBuilder(BENSON_LEAVE, BENSON).build();

    private TypicalLeaveApplicationWithEmployees() {} // prevents instantiation


    public static List<LeaveApplicationWithEmployee> getTypicalLeaveApplications() {
        return new ArrayList<>(Arrays.asList(ALICE_LEAVE_WITH_EMP, BENSON_LEAVE_WITH_EMP));
    }
}

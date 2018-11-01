package seedu.address.testutil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.leaveapplication.LeaveApplication;
import seedu.address.model.leaveapplication.StatusEnum;

/**
 * A utility class containing a list of {@code LeaveApplication} objects to be used in tests.
 */
public class TypicalLeaveApplications {
    public static final LeaveApplication ALICE_LEAVE = new LeaveApplicationBuilder()
            .withDates(LocalDate.of(2018, 10, 23), LocalDate.of(2018, 10, 24))
            .withStatus(StatusEnum.Status.PENDING).withDescription("Alice family holiday").build();
    public static final LeaveApplication BENSON_LEAVE = new LeaveApplicationBuilder()
            .withDates(LocalDate.of(2018, 10, 25), LocalDate.of(2018, 10, 26))
            .withStatus(StatusEnum.Status.PENDING).withDescription("Benson's brother's wedding").build();

    private TypicalLeaveApplications() {} // prevents instantiation


    public static List<LeaveApplication> getTypicalLeaveApplications() {
        return new ArrayList<>(Arrays.asList(ALICE_LEAVE, BENSON_LEAVE));
    }
}

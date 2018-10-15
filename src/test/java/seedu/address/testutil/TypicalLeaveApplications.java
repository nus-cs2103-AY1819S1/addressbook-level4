package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import seedu.address.model.leaveapplication.LeaveApplication;
import seedu.address.model.leaveapplication.StatusEnum;

/**
 * A utility class containing a list of {@code LeaveApplication} objects to be used in tests.
 */
public class TypicalLeaveApplications {

    public static final LeaveApplication ALICE_LEAVE = new LeaveApplicationBuilder().withId(0)
            .withDates(new Date(1539561600), new Date(1539648000)).withEmployee(TypicalPersons.ALICE)
            .withStatus(StatusEnum.Status.PENDING).withDescription("Alice family holiday").build();
    public static final LeaveApplication BENSON_LEAVE = new LeaveApplicationBuilder().withId(0)
            .withDates(new Date(1539734400), new Date(1539820800)).withEmployee(TypicalPersons.BENSON)
            .withStatus(StatusEnum.Status.APPROVED).withDescription("Benson's brother's wedding").build();

    private TypicalLeaveApplications() {} // prevents instantiation


    public static List<LeaveApplication> getTypicalLeaveApplications() {
        return new ArrayList<>(Arrays.asList(ALICE_LEAVE, BENSON_LEAVE));
    }
}

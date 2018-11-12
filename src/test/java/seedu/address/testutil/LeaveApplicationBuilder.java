package seedu.address.testutil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import seedu.address.model.leaveapplication.Description;
import seedu.address.model.leaveapplication.LeaveApplication;
import seedu.address.model.leaveapplication.LeaveStatus;
import seedu.address.model.leaveapplication.StatusEnum;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building LeaveApplication objects.
 */
public class LeaveApplicationBuilder {

    public static final String DEFAULT_DESCRIPTION = "Benson's brother's wedding";
    public static final StatusEnum.Status DEFAULT_STATUS = StatusEnum.Status.PENDING;
    public static final List<LocalDate> DEFAULT_DATES = List.of(
            LocalDate.of(2018, 10, 25),
            LocalDate.of(2018, 10, 26)
    );

    protected Description description;
    protected LeaveStatus leaveStatus;
    protected List<LocalDate> dates;

    public LeaveApplicationBuilder() {
        description = new Description(DEFAULT_DESCRIPTION);
        leaveStatus = new LeaveStatus(DEFAULT_STATUS.toString());
        dates = new ArrayList<>(DEFAULT_DATES);
    }

    /**
     * Initializes the LeaveApplicationBuilder with the data of {@code leaveApplicationToCopy}.
     */
    public LeaveApplicationBuilder(LeaveApplication leaveApplicationToCopy) {
        description = leaveApplicationToCopy.getDescription();
        leaveStatus = leaveApplicationToCopy.getLeaveStatus();
        dates = new ArrayList<>(leaveApplicationToCopy.getDates());
    }

    /**
     * Sets the {@code Description} of the {@code LeaveApplication} that we are building.
     */
    public LeaveApplicationBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Parses the {@code dates} into a {@code List<Date>} and set it to the {@code LeaveApplication}
     * that we are building.
     */
    public LeaveApplicationBuilder withDates(LocalDate ... dates) {
        this.dates = SampleDataUtil.getDateList(dates);
        return this;
    }

    /**
     * Sets the {@code LeaveStatus} of the {@code LeaveApplication} that we are building.
     */
    public LeaveApplicationBuilder withStatus(StatusEnum.Status status) {
        this.leaveStatus = new LeaveStatus(status.toString());
        return this;
    }

    public LeaveApplication build() {
        return new LeaveApplication(description, leaveStatus, dates);
    }

}

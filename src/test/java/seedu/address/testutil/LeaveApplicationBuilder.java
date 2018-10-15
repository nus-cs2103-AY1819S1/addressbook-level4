package seedu.address.testutil;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.leaveapplication.Description;
import seedu.address.model.leaveapplication.LeaveApplication;
import seedu.address.model.leaveapplication.LeaveId;
import seedu.address.model.leaveapplication.LeaveStatus;
import seedu.address.model.leaveapplication.StatusEnum;
import seedu.address.model.person.Person;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class LeaveApplicationBuilder {

    public static final Integer DEFAULT_ID = 0;
    public static final String DEFAULT_DESCRIPTION = "Family holiday to Thailand";
    public static final StatusEnum.Status DEFAULT_STATUS = StatusEnum.Status.PENDING;

    private LeaveId leaveId;
    private Description description;
    private LeaveStatus leaveStatus;
    private Person employee;
    private Set<Date> dates;

    public LeaveApplicationBuilder() {
        leaveId = new LeaveId(DEFAULT_ID);
        description = new Description(DEFAULT_DESCRIPTION);
        employee = new PersonBuilder().build();
        leaveStatus = new LeaveStatus(DEFAULT_STATUS);
        dates = new HashSet<>();
    }

    /**
     * Initializes the LeaveApplicationBuilder with the data of {@code leaveApplicationToCopy}.
     */
    public LeaveApplicationBuilder(LeaveApplication leaveApplicationToCopy) {
        leaveId = leaveApplicationToCopy.getId();
        description = leaveApplicationToCopy.getDescription();
        employee = leaveApplicationToCopy.getEmployee();
        leaveStatus = leaveApplicationToCopy.getLeaveStatus();
        dates = new HashSet<>(leaveApplicationToCopy.getDates());
    }

    /**
     * Sets the {@code Description} of the {@code LeaveApplication} that we are building.
     */
    public LeaveApplicationBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Parses the {@code dates} into a {@code Set<Date>} and set it to the {@code LeaveApplication}
     * that we are building.
     */
    public LeaveApplicationBuilder withDates(Date ... dates) {
        this.dates = SampleDataUtil.getDateSet(dates);
        return this;
    }

    /**
     * Sets the {@code Person} of the {@code LeaveApplication} that we are building.
     */
    public LeaveApplicationBuilder withEmployee(Person employee) {
        this.employee = employee;
        return this;
    }

    /**
     * Sets the {@code LeaveId} of the {@code LeaveApplication} that we are building.
     */
    public LeaveApplicationBuilder withId(Integer leaveId) {
        this.leaveId = new LeaveId(leaveId);
        return this;
    }

    /**
     * Sets the {@code LeaveStatus} of the {@code LeaveApplication} that we are building.
     */
    public LeaveApplicationBuilder withStatus(StatusEnum.Status status) {
        this.leaveStatus = new LeaveStatus(status);
        return this;
    }

    public LeaveApplication build() {
        return new LeaveApplication(leaveId, description, leaveStatus, employee, dates);
    }

}

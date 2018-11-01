package seedu.address.testutil;

import static seedu.address.testutil.TypicalPersons.ALICE;

import java.time.LocalDate;

import seedu.address.model.leaveapplication.LeaveApplication;
import seedu.address.model.leaveapplication.LeaveApplicationWithEmployee;
import seedu.address.model.leaveapplication.StatusEnum;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building LeaveApplicationWithEmployee objects.
 */
public class LeaveApplicationWithEmployeeBuilder extends LeaveApplicationBuilder {

    public static final Person DEFAULT_EMPLOYEE = ALICE;

    private Person employee;

    /**
     * Initializes the LeaveApplicationWithEmployeeBuilder with default data
     */
    public LeaveApplicationWithEmployeeBuilder() {
        super();
        this.employee = new PersonBuilder(DEFAULT_EMPLOYEE).build();
    }

    /**
     * Initializes the LeaveApplicationWithEmployeeBuilder with the data of {@code leaveApplicationToCopy}
     * and the default employee
     */
    public LeaveApplicationWithEmployeeBuilder(LeaveApplication leaveApplicationToCopy) {
        this(leaveApplicationToCopy, new PersonBuilder(DEFAULT_EMPLOYEE).build());
    }

    /**
     * Initializes the LeaveApplicationWithEmployeeBuilder with the data of {@code leaveApplicationToCopy}
     */
    public LeaveApplicationWithEmployeeBuilder(LeaveApplicationWithEmployee leaveApplicationToCopy) {
        this(leaveApplicationToCopy, leaveApplicationToCopy.getEmployee());
    }

    /**
     * Initializes the LeaveApplicationWithEmployeeBuilder with the data of {@code leaveApplicationToCopy}
     * and {@code employee}.
     */
    public LeaveApplicationWithEmployeeBuilder(LeaveApplication leaveApplicationToCopy, Person employee) {
        super(leaveApplicationToCopy);
        this.employee = new PersonBuilder(employee).build();
    }


    /**
     * Sets the {@code Description} of the {@code LeaveApplication} that we are building.
     */
    public LeaveApplicationWithEmployeeBuilder withDescription(String description) {
        super.withDescription(description);
        return this;
    }

    /**
     * Parses the {@code dates} into a {@code List<Date>} and set it to the {@code LeaveApplication}
     * that we are building.
     */
    public LeaveApplicationWithEmployeeBuilder withDates(LocalDate... dates) {
        super.withDates(dates);
        return this;
    }

    /**
     * Sets the {@code LeaveStatus} of the {@code LeaveApplication} that we are building.
     */
    public LeaveApplicationWithEmployeeBuilder withStatus(StatusEnum.Status status) {
        super.withStatus(status);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code LeaveApplication} that we are building.
     */
    public LeaveApplicationWithEmployeeBuilder withEmployee(Person employee) {
        this.employee = new PersonBuilder(employee).build();
        return this;
    }

    public LeaveApplicationWithEmployee build() {
        return new LeaveApplicationWithEmployee(description, leaveStatus, dates, employee);
    }

}

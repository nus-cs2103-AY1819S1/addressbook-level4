package seedu.address.model.leaveapplication;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.List;

import seedu.address.model.person.Person;

/**
 * Represents a LeaveApplication in the address book, tagged with the Person that made the application.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class LeaveApplicationWithEmployee extends LeaveApplication {
    private final Person employee;

    /**
     * Every field must be present and not null.
     */
    public LeaveApplicationWithEmployee(LeaveApplication leaveApplication, Person employee) {
        this(leaveApplication.getDescription(), leaveApplication.getLeaveStatus(), leaveApplication.getDates(),
                employee);
    }

    public LeaveApplicationWithEmployee(Description description, LeaveStatus leaveStatus, List<LocalDate> dates,
                                        Person employee) {
        super(description, leaveStatus, dates);
        requireNonNull(employee);
        this.employee = employee;
    }

    public Person getEmployee() {
        return employee;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof LeaveApplicationWithEmployee)) {
            return false;
        }

        LeaveApplicationWithEmployee otherLeaveApplication = (LeaveApplicationWithEmployee) other;
        return otherLeaveApplication.getEmployee().equals(getEmployee())
                && super.equals(other);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return super.hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(super.toString())
                .append(" Employee: ")
                .append(getEmployee());
        return builder.toString();
    }
}

package seedu.address.model.leaveapplication;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.person.Person;

/**
 * Represents a LeaveApplication in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class LeaveApplication {
    // Identity fields
    private final LeaveId id;

    // Data fields
    private final Description description;
    private final LeaveStatus leaveStatus;
    private final Person employee;
    private final Set<Date> dates = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public LeaveApplication(LeaveId id, Description description, LeaveStatus leaveStatus,
                            Person employee, Set<Date> dates) {
        requireAllNonNull(id, description, leaveStatus, employee, dates);
        this.id = id;
        this.description = description;
        this.leaveStatus = leaveStatus;
        this.employee = employee;
        this.dates.addAll(dates);
    }

    public LeaveId getId() {
        return id;
    }

    public Description getDescription() {
        return description;
    }

    public LeaveStatus getLeaveStatus() {
        return leaveStatus;
    }

    public Person getEmployee() {
        return employee;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Date> getDates() {
        return Collections.unmodifiableSet(dates);
    }

    /**
     * Returns true if both leave applications have the same identity and data fields.
     * This defines a stronger notion of equality between two leave applications.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof LeaveApplication)) {
            return false;
        }

        LeaveApplication otherLeaveApplication = (LeaveApplication) other;
        return otherLeaveApplication.getId().equals(getId())
                && otherLeaveApplication.getDescription().equals(getDescription())
                && otherLeaveApplication.getEmployee().equals(getEmployee())
                && otherLeaveApplication.getLeaveStatus().equals(getLeaveStatus())
                && otherLeaveApplication.getDates().equals(getDates());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(id, description, leaveStatus, employee, dates);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getId())
                .append(" Description: ")
                .append(getDescription())
                .append(" Employee: ")
                .append(getEmployee())
                .append(" Status: ")
                .append(getLeaveStatus())
                .append(" Dates: ");
        getDates().forEach(builder::append);
        return builder.toString();
    }
}

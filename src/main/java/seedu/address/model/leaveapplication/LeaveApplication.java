package seedu.address.model.leaveapplication;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Represents a LeaveApplication in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class LeaveApplication {
    private final Description description;
    private final LeaveStatus leaveStatus;
    private final List<LocalDate> dates = new ArrayList<>();

    /**
     * Every field must be present and not null.
     */
    public LeaveApplication(Description description, LeaveStatus leaveStatus, List<LocalDate> dates) {
        requireAllNonNull(description, leaveStatus, dates);
        this.description = description;
        this.leaveStatus = leaveStatus;

        // Remove duplicate dates
        this.dates.addAll(dates
                .stream().collect(Collectors.toSet())
                .stream().collect(Collectors.toList()));
    }

    public LeaveApplication(LeaveApplicationWithEmployee leaveApplication) {
        this(leaveApplication.getDescription(), leaveApplication.getLeaveStatus(), leaveApplication.getDates());
    }

    public Description getDescription() {
        return description;
    }

    public LeaveStatus getLeaveStatus() {
        return leaveStatus;
    }

    /**
     * Returns an immutable date list, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public List<LocalDate> getDates() {
        return Collections.unmodifiableList(dates);
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
        return otherLeaveApplication.getDescription().equals(getDescription())
                && otherLeaveApplication.getLeaveStatus().equals(getLeaveStatus())
                && otherLeaveApplication.getDates().equals(getDates());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(description, leaveStatus, dates);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Description: ")
                .append(getDescription())
                .append(" Status: ")
                .append(getLeaveStatus())
                .append(" Dates: ");
        getDates().forEach(builder::append);
        return builder.toString();
    }
}

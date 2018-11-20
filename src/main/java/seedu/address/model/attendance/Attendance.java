package seedu.address.model.attendance;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.commons.util.UniqueIdUtil.createUniqueId;

import java.util.Objects;

/**
 * Represents an Attendance in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Attendance implements Comparable<Attendance> {

    // Identity fields
    private final Session name;
    private final SessionDate date;
    private final String uniqueId;

    /**
     * Every field must be present and not null.
     */
    public Attendance(Session name, SessionDate date, String uniqueId) {
        requireAllNonNull(name, uniqueId);
        this.name = name;
        this.date = date;
        this.uniqueId = uniqueId;

    }

    public Attendance(Session name, SessionDate date) {
        this(name, date,
                createUniqueId(name.hashCode() + date.hashCode())
        );
    }

    public Session getSession() {
        return name; }

    public SessionDate getDate() {
        return date; }

    public String getUniqueId() {
        return uniqueId;
    }

    /**
     * Returns true if both attendance have the same name.
     * This defines a weaker notion of equality between two attendance.
     */
    public boolean isSameAttendance(Attendance otherAttendance) {
        if (otherAttendance == this) {
            return true;
        }

        return otherAttendance != null
                && otherAttendance.getSession().equals(getSession());
    }

    @Override
    public int compareTo(Attendance other) {
        int sessionComparison = this.getSession().compareTo(other.getSession());
        if (sessionComparison != 0) {
            return sessionComparison;
        }
        return 0;
    }

    /**
     * Returns true if both attendance have the same name and data fields.
     * This defines a stronger notion of equality between two attendance.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Attendance)) {
            return false;
        }

        Attendance otherAttendance = (Attendance) other;
        return otherAttendance.getSession().equals(getSession());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(uniqueId);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Attendance Session: ")
                .append(getSession() + " ")
                .append(getDate());
        return builder.toString();
    }

}

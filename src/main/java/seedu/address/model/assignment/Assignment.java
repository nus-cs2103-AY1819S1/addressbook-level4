package seedu.address.model.assignment;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.commons.util.UniqueIdUtil.createUniqueId;

import java.util.Objects;

import seedu.address.model.common.Mark;

/**
 * Represents an Assignment in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Assignment implements Comparable<Assignment> {

    // Identity fields
    private final AssignmentName name;
    private final Deadline deadline;
    private final Weight weight;
    private final Mark maxMark;
    private final String uniqueId;

    /**
     * Every field must be present and not null.
     */
    public Assignment(AssignmentName name, Weight weight, Deadline deadline, Mark maxMark, String uniqueId) {
        requireAllNonNull(name, weight, deadline, maxMark, uniqueId);
        this.name = name;
        this.weight = weight;
        this.deadline = deadline;
        this.maxMark = maxMark;
        this.uniqueId = uniqueId;
    }

    public Assignment(AssignmentName name, Weight weight, Deadline deadline, Mark maxMark) {
        this(name, weight, deadline, maxMark,
                createUniqueId(name.hashCode() + weight.hashCode() + deadline.hashCode() + maxMark.hashCode())
        );
    }

    public AssignmentName getName() {
        return name;
    }

    public Weight getWeight() {
        return weight;
    }

    public Deadline getDeadline() {
        return deadline;
    }

    public Mark getMaxMark() {
        return maxMark;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    /**
     * Returns true if both assignment have the same name.
     * This defines a weaker notion of equality between two assignment.
     */
    public boolean isSameAssignment(Assignment otherAssignment) {
        if (otherAssignment == this) {
            return true;
        }

        return otherAssignment != null
                && otherAssignment.getName().equals(getName());
    }

    @Override
    public int compareTo(Assignment other) {
        int deadlineComparison = this.getDeadline().compareTo(other.getDeadline());
        if (deadlineComparison != 0) {
            return deadlineComparison;
        }
        int nameComparison = this.getName().compareTo(other.getName());
        if (nameComparison != 0) {
            return nameComparison;
        }
        return 0;
    }

    /**
     * Returns true if both assignments have the same name and data fields.
     * This defines a stronger notion of equality between two assignments.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Assignment)) {
            return false;
        }

        Assignment otherAssignment = (Assignment) other;
        return otherAssignment.getName().equals(getName())
                && otherAssignment.getWeight().equals(getWeight())
                && otherAssignment.getDeadline().equals(getDeadline())
                && otherAssignment.getMaxMark().equals(getMaxMark());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Weight: ")
                .append(getWeight())
                .append(" Deadline: ")
                .append(getDeadline())
                .append(" Max Mark: ")
                .append(getMaxMark());
        return builder.toString();
    }

}

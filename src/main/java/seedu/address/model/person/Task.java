package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Label;

/**
 * Represents a Task in the description book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Task {

    // Identity fields
    private final Name name;
    private final DueDate dueDate;
    private final PriorityValue priorityValue;
    private final Status status;

    // Data fields
    private final Description description;
    private final Set<Label> labels = new HashSet<>();

    /**
     * Every field must be present and not null.
     * Status of a new task is initialized to in progress unless specified.
     */
    public Task(Name name, DueDate dueDate, PriorityValue priorityValue, Description description, Set<Label> labels) {
        requireAllNonNull(name, dueDate, priorityValue, description, labels);
        this.name = name;
        this.dueDate = dueDate;
        this.priorityValue = priorityValue;
        this.description = description;
        this.labels.addAll(labels);
        this.status = Status.IN_PROGRESS;
    }

    public Task(Name name, DueDate dueDate, PriorityValue priorityValue, Description description, Set<Label> labels,
                Status status) {
        requireAllNonNull(name, dueDate, priorityValue, description, labels);
        this.name = name;
        this.dueDate = dueDate;
        this.priorityValue = priorityValue;
        this.description = description;
        this.labels.addAll(labels);
        this.status = status;
    }

    public Name getName() {
        return name;
    }

    public DueDate getDueDate() {
        return dueDate;
    }

    public PriorityValue getPriorityValue() {
        return priorityValue;
    }

    public Description getDescription() {
        return description;
    }

    /**
     * Returns an immutable label set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Label> getLabels() {
        return Collections.unmodifiableSet(labels);
    }

    public Status getStatus() {
        return status;
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameTask(Task othertask) {
        if (othertask == this) {
            return true;
        }

        return othertask != null
                && othertask.getName().equals(getName())
                && (othertask.getDueDate().equals(getDueDate())
                || othertask.getPriorityValue().equals(getPriorityValue()));
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Task)) {
            return false;
        }

        Task othertask = (Task) other;
        return othertask.getName().equals(getName())
                && othertask.getDueDate().equals(getDueDate())
                && othertask.getPriorityValue().equals(getPriorityValue())
                && othertask.getDescription().equals(getDescription())
                && othertask.getLabels().equals(getLabels())
                && othertask.getStatus().equals(getStatus());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, dueDate, priorityValue, description, labels, status);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" DueDate: ")
                .append(getDueDate())
                .append(" PriorityValue: ")
                .append(getPriorityValue())
                .append(" Description: ")
                .append(getDescription())
                .append(" Labels: ");
        getLabels().forEach(builder::append);
        builder.append(" Status: ")
                .append(getStatus());
        return builder.toString();
    }

}

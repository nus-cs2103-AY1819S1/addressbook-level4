package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Label;

/**
 * Represents a Task in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Task {

    // Identity fields
    private final Name name;
    private final DueDate phone;
    private final PriorityValue email;

    // Data fields
    private final Description address;
    private final Set<Label> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, DueDate phone, PriorityValue email, Description address, Set<Label> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public DueDate getDueDate() {
        return phone;
    }

    public PriorityValue getPriorityValue() {
        return email;
    }

    public Description getDescription() {
        return address;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Label> getLabels() {
        return Collections.unmodifiableSet(tags);
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
                && (othertask.getDueDate().equals(getDueDate()) || othertask.getPriorityValue().equals(getPriorityValue()));
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
                && othertask.getLabels().equals(getLabels());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags);
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
                .append(" Tags: ");
        getLabels().forEach(builder::append);
        return builder.toString();
    }

}

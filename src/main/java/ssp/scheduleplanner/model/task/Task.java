package ssp.scheduleplanner.model.task;

import static ssp.scheduleplanner.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import ssp.scheduleplanner.model.tag.Tag;

/**
 * Represents a Task in the schedule planner.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Task {

    // Identity fields
    private final Name name;
    private final Date date;
    private final Priority priority;

    // Data fields
    private final Venue venue;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, Date date, Priority priority, Venue venue, Set<Tag> tags) {
        requireAllNonNull(name, date, priority, venue, tags);
        this.name = name;
        this.date = date;
        this.priority = priority;
        this.venue = venue;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public Priority getPriority() {
        return priority;
    }

    public Venue getVenue() {
        return venue;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both  of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two tasks.
     */
    public boolean isSameTask(Task otherTask) {
        if (otherTask == this) {
            return true;
        }

        return otherTask != null
                && otherTask.getName().equals(getName()) && otherTask.getDate().equals(getDate())
                && otherTask.getVenue().equals(getVenue())
                && otherTask.getTags().equals(getTags());
    }

    /**
     * Returns true if both tasks have the same identity and data fields.
     * This defines a stronger notion of equality between two tasks.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Task)) {
            return false;
        }

        Task otherTask = (Task) other;
        return otherTask.getName().equals(getName())
                && otherTask.getDate().equals(getDate())
                && otherTask.getVenue().equals(getVenue())
                && otherTask.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, date, priority, venue, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Date: ")
                .append(getDate())
                .append(" Priority: ")
                .append(getPriority())
                .append(" Venue: ")
                .append(getVenue())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Compare two tasks according to their priority or name.
     * @param a
     * @param b
     * @return -1 if Task a has lower priority than Task b,
     * 0 if same priority,
     * 1 if Task a has higher priority.
     */
    public static int compare(Task a, Task b) {
        int result = Date.compare(a.getDate(), b.getDate());
        if (result == 0) {
            result = Priority.compare(a.getPriority(), b.getPriority());
            if (result == 0) {
                result = Name.compare(a.getName(), b.getName());
            }
        }
        return result;
    }

}

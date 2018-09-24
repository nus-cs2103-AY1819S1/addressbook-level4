package seedu.address.model.event;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents an Event in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Event {

    public static final String MESSAGE_START_END_DATE_CONSTRAINTS =
            "Start date should only be less than or equal to end date";

    public static final String MESSAGE_START_END_TIME_CONSTRAINTS =
            "Start time should only be less than or equal to end time";

    // Identity fields
    //private final Id id;
    private final Name name;
    private final Location location;
    private final Date startDate;
    private final Date endDate;

    // Data fields
    private final Time startTime;
    private final Time endTime;
    private final Description description;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    //Id to be added to constructor
    public Event(Name name, Location location, Date startDate, Date endDate,
                 Time startTime, Time endTime, Description description, Set<Tag> tags) {
        //requireAllNonNull(id, name, location, startDate, endDate, description, tags);
        requireAllNonNull(name, location, startDate, endDate, description, tags);

        //this.id = id;
        this.name = name;
        this.location = location;

        this.startDate = startDate;
        this.endDate = endDate;
        checkArgument(isValidStartAndEndDate(startDate, endDate), MESSAGE_START_END_DATE_CONSTRAINTS);

        this.startTime = startTime;
        this.endTime = endTime;
        checkArgument(isValidStartAndEndTime(startTime, endTime), MESSAGE_START_END_TIME_CONSTRAINTS);

        this.description = description;
        this.tags.addAll(tags);
    }

    /**
     * Returns true if a given start date is less than or equal to end date.
     */
    public static boolean isValidStartAndEndDate(Date startDate, Date endDate) {
        return startDate.isLessThanOrEqualTo(endDate);
    }

    /**
     * Returns true if a given start time is less than or equal to end time.
     */
    public static boolean isValidStartAndEndTime(Time startTime, Time endTime) {
        return startTime.isLessThanOrEqualTo(endTime);
    }

    /*public Id getId() {
        return id;
    }*/

    public Name getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public Description getDescription() {
        return description;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }


    /**
     * Returns true if both Events of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two events.
     */
    public boolean isSameEvent(Event otherEvent) {
        if (otherEvent == this) {
            return true;
        }

        return otherEvent != null
                && otherEvent.getName().equals(getName())
                && (otherEvent.getLocation().equals(getLocation()) || otherEvent.getStartDate().equals(getStartDate())
                    || otherEvent.getEndDate().equals(getEndDate()));
    }

    /**
     * Returns true if both events have the same identity and data fields.
     * This defines a stronger notion of equality between two events.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Event)) {
            return false;
        }

        Event otherEvent = (Event) other;
        return otherEvent.getName().equals(getName())
                && otherEvent.getLocation().equals(getLocation())
                && otherEvent.getStartDate().equals(getStartDate())
                && otherEvent.getEndDate().equals(getEndDate())
                && otherEvent.getStartTime().equals(getEndTime())
                && otherEvent.getEndTime().equals(getEndTime())
                && otherEvent.getDescription().equals(getDescription())
                && otherEvent.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, location, startDate, endDate, startTime, endTime, description, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Location: ")
                .append(getLocation())
                .append(" Start Date: ")
                .append(getStartDate())
                .append(" End Date: ")
                .append(getEndDate())
                .append(" Start Time.java: ")
                .append(getStartTime())
                .append(" End Time.java: ")
                .append(getEndTime())
                .append(" Description: ")
                .append(getDescription())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}

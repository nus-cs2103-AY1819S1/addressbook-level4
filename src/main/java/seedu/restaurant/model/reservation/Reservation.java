package seedu.restaurant.model.reservation;

import static seedu.restaurant.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.restaurant.model.tag.Tag;

//@@author m4dkip
/**
 * Represents a Reservation in the restaurant book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Reservation {

    // Identity fields
    private final Name name;
    private final Pax pax;
    private final Date date;
    private final Time time;
    private final Remark remark;

    // Data fields
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Reservation(Name name, Pax pax, Date date, Time time, Remark remark, Set<Tag> tags) {
        requireAllNonNull(name, pax, date, time, tags);
        this.name = name;
        this.pax = pax;
        this.date = date;
        this.time = time;
        this.remark = remark;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Pax getPax() {
        return pax;
    }

    public Date getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    public Remark getRemark() {
        return remark;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both reservations of the same name have at least one other
     * identity field that is the same.
     * This defines a weaker notion of equality between two reservations.
     */
    public boolean isSameReservation(Reservation otherReservation) {
        if (otherReservation == this) {
            return true;
        }

        return otherReservation != null
                && otherReservation.getName().equals(getName())
                && (otherReservation.getPax().equals(getPax())
                    || otherReservation.getDate().equals(getDate())
                    || otherReservation.getTime().equals(getTime()));
    }

    /**
     * Returns true if both reservations have the same identity and data fields.
     * This defines a stronger notion of equality between two reservations.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Reservation)) {
            return false;
        }

        Reservation otherReservation = (Reservation) other;
        return otherReservation.getName().equals(getName())
                && otherReservation.getPax().equals(getPax())
                && otherReservation.getDate().equals(getDate())
                && otherReservation.getTime().equals(getTime())
                && otherReservation.getRemark().equals(getRemark())
                && otherReservation.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, pax, date, time, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("Name: ");
        builder.append(getName())
                .append(" Pax: ")
                .append(getPax())
                .append(" Date: ")
                .append(getDate())
                .append(" Time: ")
                .append(getTime())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}

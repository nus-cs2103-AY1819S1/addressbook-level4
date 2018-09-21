package seedu.address.model.event;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.tag.Tag;

/**
 * Represents a Event in the event organiser.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Event {

    // Identity fields
    private final Name name;
    private final int id;
    private static int currID = 0;

    // Data fields
    private final Address location;

    private Date date;

    private final Set<Tag> tags = new HashSet<>();

    private final HashSet<Poll> polls;

    /**
     * Every field must be present and not null.
     */
    public Event(Name name, Address address, Set<Tag> tags) {
        requireAllNonNull(name, address, tags);
        this.id = currID;
        currID++;
        this.name = name;
        this.location = address;
        this.tags.addAll(tags);
        polls = new HashSet<>();
    }

    public Name getName() {
        return name;
    }

    public Address getLocation() {
        return location;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void addPoll(String pollName) {
        Poll poll = new Poll(pollName);
        polls.add(poll);
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameEvent(Event otherEvent) {
        if (otherEvent == this) {
            return true;
        }

        return otherEvent != null
                && otherEvent.getName().equals(getName());
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

        if (!(other instanceof seedu.address.model.event.Event)) {
            return false;
        }

        Event otherPerson = (seedu.address.model.event.Event) other;
        return otherPerson.getName().equals(getName())
                && otherPerson.getLocation().equals(getLocation())
                && otherPerson.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, location, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Address: ")
                .append(getLocation())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}

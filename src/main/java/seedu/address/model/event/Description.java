package seedu.address.model.event;

import static java.util.Objects.requireNonNull;

/**
 * Represents an Event's description in the scheduler.
 * Guarantees: immutable.
 */
public class Description {

    public final String eventDescription;

    /**
     * Constructs a {@code Description}.
     *
     * @param description A valid event description.
     */
    public Description(String description) {
        requireNonNull(description);
        eventDescription = description;
    }

    @Override
    public String toString() {
        return eventDescription;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Description // instanceof handles nulls
                && eventDescription.equals(((Description) other).eventDescription)); // state check
    }

    @Override
    public int hashCode() {
        return eventDescription.hashCode();
    }

}

package seedu.address.model.event;

import static java.util.Objects.requireNonNull;

/**
 * Represents an Event's venue in the scheduler.
 * Guarantees: immutable.
 */
public class Venue {

    public final String value;

    /**
     * Constructs a {@code Venue}.
     *
     * @param venue A valid event venue.
     */
    public Venue(String venue) {
        requireNonNull(venue);
        value = venue;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Venue // instanceof handles nulls
                && value.equals(((Venue) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

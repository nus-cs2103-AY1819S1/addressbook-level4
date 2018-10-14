package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Event's description in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDescription(String)}
 */
public class EventDescription {

    public static final String MESSAGE_DESCRIPTION_CONSTRAINTS =
            "Event descriptions should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the event name must not be a whitespace,
     * so that " " (a blank string) is considered an invalid input.
     */
    public static final String DESCRIPTION_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum}\\s]*";

    public final String eventDescription;

    /**
     * Constructs a {@code EventDescription}.
     *
     * @param eventDescription A valid description.
     */
    public EventDescription(String eventDescription) {
        requireNonNull(eventDescription);
        checkArgument(isValidDescription(eventDescription), MESSAGE_DESCRIPTION_CONSTRAINTS);
        this.eventDescription = eventDescription;
    }

    /**
     * Returns true if a given string is a valid event name.
     */
    public static boolean isValidDescription(String test) {
        return test.matches(DESCRIPTION_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return eventDescription;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventDescription // instanceof handles nulls
                && eventDescription.equals(((EventDescription) other).eventDescription)); // state check
    }

    @Override
    public int hashCode() {
        return eventDescription.hashCode();
    }

}

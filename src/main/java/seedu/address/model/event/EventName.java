package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Event's name in the scheduler.
 * Guarantees: immutable; is valid as declared in {@link #isValidEventName(String)}
 */
public class EventName {

    public static final String MESSAGE_EVENT_NAME_CONSTRAINTS =
            "Event names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the event name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String EVENT_NAME_VALIDATION_REGEX = "[^\\s].*";

    public final String eventName;

    /**
     * Constructs a {@code EventName}.
     *
     * @param name A valid event name.
     */
    public EventName(String name) {
        requireNonNull(name);
        checkArgument(isValidEventName(name), MESSAGE_EVENT_NAME_CONSTRAINTS);
        eventName = name;
    }

    /**
     * Returns true if a given string is a valid event name.
     */
    public static boolean isValidEventName(String test) {
        return test.matches(EVENT_NAME_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return eventName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventName // instanceof handles nulls
                && eventName.equals(((EventName) other).eventName)); // state check
    }

    @Override
    public int hashCode() {
        return eventName.hashCode();
    }

}

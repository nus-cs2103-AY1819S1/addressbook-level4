package seedu.address.model.record;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * This is just a placeholder class for development purposes.
 * Actual record EventId should come from the event package
 */
public class EventId {
    public static final String MESSAGE_EVENTID_CONSTRAINTS = "EventId can take in numerals only.";
    public static final String EVENTID_VALIDATION_REGEX = "\\p{Digit}+";

    public final String value;

    /**
     * Constructs a {@code Remark}.
     *
     * @param id A valid eventId.
     */
    public EventId(String id) {
        requireNonNull(id);
        checkArgument(isValidEventId(id), MESSAGE_EVENTID_CONSTRAINTS);
        this.value = id;
    }

    /**
     * Returns true if a given string is a valid eventId.
     */
    public static boolean isValidEventId(String test) {
        return test.matches(EVENTID_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventId // instanceof handles nulls
                && value.equals(((EventId) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return value;
    }
}

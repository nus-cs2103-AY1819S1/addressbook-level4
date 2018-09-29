package seedu.address.model.meeting;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a value that the user has scheduled with the client.
 * Guarantees: immutable.
 */
public class Meeting {

    public static final String MESSAGE_MEETING_CONSTRAINTS =
            "Meeting should only contain a date and time in DDMMYYHHMM format";

    public static final String MEETING_VALIDATION_REGEX = "\\d{10}";
    public static final String NO_MEETING = "0000000000";

    public final String value;

    /**
     * Constructs a {@code Meeting}.
     *
     * @param meeting A valid value time.
     */
    public Meeting(String meeting) {
        requireNonNull(meeting);
        checkArgument(isValidMeeting(meeting), MESSAGE_MEETING_CONSTRAINTS);
        value = meeting;
    }

    /**
     * Returns true if a given string is a valid value.
     */
    public static boolean isValidMeeting(String test) {
        return test.matches(MEETING_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return value;
        // TBD return details of the entry. Use linear lookup.
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Meeting // instanceof handles nulls
                && value.equals(((Meeting) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

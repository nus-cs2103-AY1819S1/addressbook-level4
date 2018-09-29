package seedu.address.model.meeting;

import seedu.address.model.person.Person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a meeting that the user has scheduled with the client.
 * Guarantees: immutable.
 */
public class Meeting {

    public static final String MESSAGE_MEETING_CONSTRAINTS =
            "Meeting should only contain a date and time in DDMMYYHHMM format";

    public static final String MEETING_VALIDATION_REGEX = "\\d{10}";

    public final String meeting;
    public final Person person;
    /**
     * Constructs a {@code Meeting}.
     *
     * @param meeting A valid meeting time.
     */
    public Meeting(String meeting, Person person) {
        requireNonNull(meeting);
        checkArgument(isValidMeeting(meeting), MESSAGE_MEETING_CONSTRAINTS);
        this.meeting = meeting;
        this.person = person;
    }

    /**
     * Returns true if a given string is a valid meeting.
     */
    public static boolean isValidMeeting(String test) {
        return test.matches(MEETING_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return meeting + " " + person.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Meeting // instanceof handles nulls
                && meeting.equals(((Meeting) other).meeting)); // state check
    }

    @Override
    public int hashCode() {
        return meeting.hashCode();
    }
}

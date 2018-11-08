package seedu.meeting.model.meeting.exceptions;

// @@author jeffreyooi
/**
 * Signals that the operation is unable to find the specified meeting.
 */
public class MeetingNotFoundException extends RuntimeException {
    public MeetingNotFoundException() {
        super("Meeting does not exist");
    }
}

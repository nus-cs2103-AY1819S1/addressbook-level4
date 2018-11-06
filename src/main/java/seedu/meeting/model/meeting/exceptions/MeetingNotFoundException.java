package seedu.meeting.model.meeting.exceptions;

/**
 * Signals that the operation is unable to find the specified meeting.
 *
 * {@author jeffreyooi}
 */
public class MeetingNotFoundException extends RuntimeException {
    public MeetingNotFoundException() {
        super("Meeting does not exist");
    }
}

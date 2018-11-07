package seedu.meeting.model.meeting.exceptions;

/**
 * Signals that the operation will result in duplicate Meetings
 * (Meetings are considered duplicates if they belong to the same group and have the same identity).
 *
 * {@author jeffreyooi}
 */
public class DuplicateMeetingException extends RuntimeException {

    public DuplicateMeetingException() {
        super("Operation would result in duplicate meetings");
    }
}

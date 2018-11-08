package seedu.meeting.model.meeting.exceptions;

// @@author jeffreyooi
/**
 * Signals that the operation will result in duplicate Meetings
 * (Meetings are considered duplicates if they belong to the same group and have the same identity).
 */
public class DuplicateMeetingException extends RuntimeException {

    public DuplicateMeetingException() {
        super("Operation would result in duplicate meetings");
    }
}

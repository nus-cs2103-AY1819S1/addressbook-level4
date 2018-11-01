package seedu.meeting.model.group.exceptions;

/**
 * Signals that the operation will result in duplicate Groups (Groups are considered duplicate if they have the same
 * properties).
 */
public class DuplicateGroupTagException extends RuntimeException {
    public DuplicateGroupTagException() {
        super("Operation would result in duplicate group");
    }
}

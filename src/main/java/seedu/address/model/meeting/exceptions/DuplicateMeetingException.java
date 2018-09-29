package seedu.address.model.meeting.exceptions;


/**
 * Signals that the operation will result in duplicate Meetings (Meetings are considered duplicte if they are at the
 * same time).
 */
public class DuplicateMeetingException extends RuntimeException{
    public DuplicateMeetingException() {
        super("There is already a value at the given time");
    }
}

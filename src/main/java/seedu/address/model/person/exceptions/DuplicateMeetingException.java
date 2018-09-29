package seedu.address.model.person.exceptions;


/**
 * Signals that the operation will result in duplicate Meetings (Meetings are considered duplicte if they are at the
 * same time).
 */
public class DuplicateMeetingException extends RuntimeException{
    public DuplicateMeetingException() {
        super("There is already a meeting at the given time");
    }
}

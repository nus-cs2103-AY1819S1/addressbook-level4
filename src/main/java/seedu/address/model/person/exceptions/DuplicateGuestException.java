package seedu.address.model.person.exceptions;

/**
 * Signals that the operation will result in duplicate Persons (Persons are considered duplicates if they have the same
 * identity).
 */
public class DuplicateGuestException extends RuntimeException {
    public DuplicateGuestException() {
        super("Operation would result in duplicate persons");
    }
}

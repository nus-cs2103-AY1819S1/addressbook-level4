package seedu.address.model.group.exceptions;

/**
 * Signals that the operation will result in duplicate Groups (Groups are considered duplicate if they have the same
 * properties).
 */
public class DuplicateGroupException extends RuntimeException {
    public DuplicateGroupException() {
        super("Operation would result in duplicate group");
    }
}

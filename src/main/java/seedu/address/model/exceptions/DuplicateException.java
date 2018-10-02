package seedu.address.model.exceptions;

/**
 * Signals that the operation will result in duplicate uniqueType (UniqueTypes are considered duplicates if they
 * have the
 * same
 * identity).
 */
public class DuplicateException extends RuntimeException {
    public DuplicateException(String className) {
        super("Operation would result in duplicate " + className + "s");
    }
}

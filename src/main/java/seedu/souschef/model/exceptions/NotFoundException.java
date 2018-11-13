package seedu.souschef.model.exceptions;

/**
 * Signals that the operation is unable to find the specified uniqueType.
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String className) {
        super(className + "not found");
    }
}

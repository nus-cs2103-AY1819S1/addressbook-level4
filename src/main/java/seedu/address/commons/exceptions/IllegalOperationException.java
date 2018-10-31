//@@author j-lum
package seedu.address.commons.exceptions;

/**
 * Exception thrown when the user performs an invalid operation.
 */
public class IllegalOperationException extends Exception {
    /**
     * @param message should contain relevant information on the failed operation
     */
    public IllegalOperationException(String message) {
        super(message);
    }

    /**
     * @param message should contain relevant information on the failed operation
     * @param cause of the main exception
     */
    public IllegalOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}

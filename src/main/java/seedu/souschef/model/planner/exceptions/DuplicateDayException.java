package seedu.souschef.model.planner.exceptions;

/**
 * Signals that a duplicate day already exists.
 */
public class DuplicateDayException extends RuntimeException {
    /**
     * @param message should contain relevant information on the failed constraint(s)
     */
    public DuplicateDayException(String message) {
        super(message);
    }

    /**
     * @param message should contain relevant information on the failed constraint(s)
     * @param cause of the main exception
     */
    public DuplicateDayException(String message, Throwable cause) {
        super(message, cause);
    }
}

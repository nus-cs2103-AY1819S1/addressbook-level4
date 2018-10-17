package seedu.inventory.commons.exceptions;

/**
 * Signals that data in the given file does not fulfill some constraints.
 */
public class UnrecognizableDataException extends Exception {
    /**
     * @param message should contain relevant information on the failed constraint(s)
     */
    public UnrecognizableDataException(String message) {
        super(message);
    }
}

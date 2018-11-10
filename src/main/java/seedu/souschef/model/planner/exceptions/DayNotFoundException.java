package seedu.souschef.model.planner.exceptions;

/**
 * Signals that no recipe is found at the specified meal index of the day.
 */
public class DayNotFoundException extends RuntimeException {
    /**
     * @param message should contain relevant information on the failed constraint(s)
     */
    public DayNotFoundException(String message) {
        super(message);
    }

    /**
     * @param message should contain relevant information on the failed constraint(s)
     * @param cause of the main exception
     */
    public DayNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

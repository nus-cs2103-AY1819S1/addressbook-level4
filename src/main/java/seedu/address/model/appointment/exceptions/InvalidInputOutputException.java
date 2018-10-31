package seedu.address.model.appointment.exceptions;

/**
 * Signals that user that the input is incorrect
 */
public class InvalidInputOutputException extends RuntimeException {
    public InvalidInputOutputException() {
        super("The input you sent to google calendar is invalid");
    }
}

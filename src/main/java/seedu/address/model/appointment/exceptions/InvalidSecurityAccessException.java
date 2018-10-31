package seedu.address.model.appointment.exceptions;

/**
 * Signals that user do not have security access for the operation
 */
public class InvalidSecurityAccessException extends RuntimeException {
    public InvalidSecurityAccessException() {
        super("You do not have a valid access to the google calendar");
    }
}

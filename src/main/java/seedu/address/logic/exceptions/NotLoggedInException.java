package seedu.address.logic.exceptions;

public class NotLoggedInException extends Exception {
    public NotLoggedInException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code NotLoggedInException} with the specified detail {@code message} and {@code cause}.
     */
    public NotLoggedInException(String message, Throwable cause) {
        super(message, cause);
    }
}

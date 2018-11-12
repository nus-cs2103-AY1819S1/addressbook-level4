package seedu.superta.model.attendance.exceptions;

/**
 * Signals that the operation is unable to find the specified attendance session.
 */
public class SessionNotFoundException extends RuntimeException {
    public SessionNotFoundException() {
        super("Attendance session not found.");
    }
}

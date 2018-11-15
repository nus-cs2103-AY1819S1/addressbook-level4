package seedu.address.model.person.exceptions;

/**
 * Signals that the operation will result in a change in the Patient's blood type, which is not allowed.
 */
public class DifferentBloodTypeException extends RuntimeException {
    public DifferentBloodTypeException() {
        super("Prohibited operation: Attempting to change blood type of Patient.");
    }
}

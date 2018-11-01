package seedu.address.model.medicine.exceptions;

/**
 * Signals that the operation will result in more than one medicine with same serial number.
 */
public class SerialNumberInUseException extends RuntimeException {
    public SerialNumberInUseException() {
        super("Operation would result in different medicines with same serial number.");
    }
}

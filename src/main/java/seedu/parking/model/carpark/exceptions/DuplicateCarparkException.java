package seedu.address.model.carpark.exceptions;

/**
 * Signals that the operation will result in duplicate Carparks (Carparks are considered duplicates
 * if they have the same identity).
 */
public class DuplicateCarparkException extends RuntimeException {
    public DuplicateCarparkException() {
        super("Operation would result in duplicate carparks");
    }
}

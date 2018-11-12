package seedu.address.model.timetable.exceptions;

/**
 * Signals that the operation will result in duplicate Dates.
 * (Dates are considered duplicates if they have the same
 * week. day, and time values).
 */
public class DuplicateDateException extends RuntimeException {
    public DuplicateDateException() {
        super("Operation would result in duplicate dates.");
    }
}


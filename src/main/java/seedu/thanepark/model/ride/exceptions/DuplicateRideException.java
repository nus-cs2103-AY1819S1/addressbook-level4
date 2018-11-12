package seedu.thanepark.model.ride.exceptions;

/**
 * Signals that the operation will result in duplicate Rides (Rides are considered duplicates if they have the same
 * identity).
 */
public class DuplicateRideException extends RuntimeException {
    public DuplicateRideException() {
        super("Operation would result in duplicate rides");
    }
}

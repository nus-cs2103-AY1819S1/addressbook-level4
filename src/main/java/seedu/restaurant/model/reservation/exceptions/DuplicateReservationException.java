package seedu.restaurant.model.reservation.exceptions;

//@@author m4dkip
/**
 * Signals that the operation will result in duplicate Reservations
 * (Reservations are considered duplicates if they have the same identity).
 */
public class DuplicateReservationException extends RuntimeException {
    public DuplicateReservationException() {
        super("Operation would result in duplicate reservations");
    }
}

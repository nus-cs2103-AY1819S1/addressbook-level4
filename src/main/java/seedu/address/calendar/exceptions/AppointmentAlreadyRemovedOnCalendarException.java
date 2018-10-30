package seedu.address.calendar.exceptions;

/**
 * Signals that the operation will result in duplicate Persons (Persons are considered duplicates if they have the same
 * identity).
 */
public class AppointmentAlreadyRemovedOnCalendarException extends RuntimeException {
    public AppointmentAlreadyRemovedOnCalendarException() {
        super("Appointment already removed on the google calendar.");
    }
}

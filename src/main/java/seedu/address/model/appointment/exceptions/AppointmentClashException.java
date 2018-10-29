package seedu.address.model.appointment.exceptions;

/**
 * Signals that the operation will result in clashing Appointments.
 * Appointments clash if they have the same time, date and doctor.
 */
public class AppointmentClashException extends RuntimeException {
    public AppointmentClashException() {
        super("Operation would result in clashing Appointments");
    }
}

package seedu.address.model.appointment;

/**
 * Represents an status of an appointment in the health book.
 */
public class AppointmentId {
    private int appointmentId;

    public AppointmentId(int id) {
        appointmentId = id;
    }

    public int getAppointmentId() {
        return appointmentId;
    }
}

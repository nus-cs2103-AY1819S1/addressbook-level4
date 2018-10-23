package seedu.address.model.appointment;

/**
 * Represents an status of an appointment in the health book.
 */
public class AppointmentId {
    private static int appointmentCounter;

    private int appointmentId;

    public AppointmentId() {
        appointmentId = appointmentCounter++;
    }

    public AppointmentId(int id) {
        appointmentId = id;
    }

    public int getAppointmentId() {
        return appointmentId;
    }
}

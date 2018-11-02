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

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AppointmentId)) {
            return false;
        }
        return appointmentId == ((AppointmentId) other).appointmentId;
    }
}

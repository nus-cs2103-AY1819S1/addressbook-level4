package seedu.address.model.appointment;

import java.util.Date;
import java.util.List;

/**
 * Represents an Appointment in the health book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Appointment {
    private int appointmentId;
    private Date startDateTime;
    private Status status;
    private String comments;
    private List<Prescription> prescriptions;

    public Appointment(int appointmentId, Date startDateTime, Status status) {
        this.appointmentId = appointmentId;
        this.startDateTime = startDateTime;
        this.status = status;
    }

    public void addPrescription (Prescription prescription) {
        prescriptions.add(prescription);
    }

    public void completeAppointment() {
        status = Status.COMPLETED;
    }
}

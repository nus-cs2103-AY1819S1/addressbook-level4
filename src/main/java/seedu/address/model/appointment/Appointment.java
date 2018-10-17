package seedu.address.model.appointment;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents an Appointment in the health book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Appointment {
    private static int appointmentCounter;

    private int appointmentId;
    private LocalDateTime startDateTime;
    private Status status;
    private String comments;
    private List<Prescription> prescriptions;

    public Appointment(LocalDateTime startDateTime) {
        this.appointmentId = appointmentCounter;
        appointmentCounter++;
        this.startDateTime = startDateTime;
        this.status = Status.UPCOMING;
    }

    public void addPrescription(Prescription prescription) {
        prescriptions.add(prescription);
    }

    public void completeAppointment() {
        status = Status.COMPLETED;
    }

    /**
     * Checks if target appointment is later than appointment argument by LocalDateTime.
     * Return true if target appointment is later than other appointment. Else, return false.
     */
    public boolean isLaterThan(Appointment appointmentToCheck) {
        if (startDateTime.compareTo(appointmentToCheck.startDateTime) > 0) {
            return true;
        }
        return false;
    }
}

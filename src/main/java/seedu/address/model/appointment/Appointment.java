package seedu.address.model.appointment;

import java.time.LocalDateTime;
import java.util.List;

import seedu.address.model.doctor.Doctor;

/**
 * Represents an Appointment in the health book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Appointment {
    private static int appointmentCounter;

    private int appointmentId;
    private Doctor doctor;
    private LocalDateTime dateTime;
    private Status status;
    private String comments;
    private List<Prescription> prescriptions;

    public Appointment(Doctor doctor, LocalDateTime dateTime) {
        this.appointmentId = appointmentCounter;
        appointmentCounter++;
        this.doctor = doctor;
        this.dateTime = dateTime;
        this.status = Status.UPCOMING;
        doctor.addUpcomingAppointment(this);
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public Doctor getDoctor() {
        return doctor;
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
        return dateTime.isAfter(appointmentToCheck.dateTime);
    }
}

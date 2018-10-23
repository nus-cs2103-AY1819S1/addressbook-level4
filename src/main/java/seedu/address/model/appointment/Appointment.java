package seedu.address.model.appointment;

import java.time.LocalDateTime;
import java.util.List;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.patient.Patient;

/**
 * Represents an Appointment in the health book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Appointment {
    private static int appointmentCounter;

    private int appointmentId;
    private Patient patient;
    private Doctor doctor;
    private LocalDateTime dateTime;
    private Status status;
    private String comments;
    private List<Prescription> prescriptions;

    public Appointment(Patient patient, Doctor doctor, LocalDateTime dateTime) {
        this.appointmentId = appointmentCounter;
        appointmentCounter++;
        this.patient = patient;
        this.doctor = doctor;
        this.dateTime = dateTime;
        this.status = Status.UPCOMING;
    }

    public int getAppointmentId() {
        return appointmentId;
    }
    
    public Patient getPatient() {
        return patient;
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

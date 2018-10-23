package seedu.address.model.appointment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an Appointment in the health book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Appointment {

    private AppointmentId appointmentId;
    private String doctor;
    private LocalDateTime dateTime;
    private Status status;
    private String comments;
    private List<Prescription> prescriptions;

    public Appointment(String doctor, LocalDateTime dateTime) {
        appointmentId = new AppointmentId();
        this.doctor = doctor;
        this.dateTime = dateTime;
        this.status = Status.UPCOMING;
        prescriptions = new ArrayList<>();
    }

    public Appointment(AppointmentId appointmentId, String doctor, LocalDateTime dateTime, Status status,
                       String comments, List<Prescription> prescriptions) {
        this.appointmentId = appointmentId;
        this.doctor = doctor;
        this.dateTime = dateTime;
        this.status = status;
        this.comments = comments;
        this.prescriptions = prescriptions;
    }

    public int getAppointmentId() {
        return appointmentId.getAppointmentId();
    }

    public String getDoctor() {
        return doctor;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Status getStatus() {
        return status;
    }

    public String getComments() {
        return comments;
    }

    public List<Prescription> getPrescriptions() {
        return prescriptions;
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

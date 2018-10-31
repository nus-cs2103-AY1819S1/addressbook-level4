package seedu.address.model.appointment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an Appointment in the health book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Appointment implements Comparable<Appointment> {

    private AppointmentId appointmentId;
    private String doctor;
    private String patient;
    private LocalDateTime dateTime;
    private Status status;
    private String comments;
    private List<Prescription> prescriptions;

    public Appointment(int appointmentCounter, String doctor, String patient, LocalDateTime dateTime) {
        appointmentId = new AppointmentId(appointmentCounter);
        this.doctor = doctor;
        this.patient = patient;
        this.dateTime = dateTime;
        this.status = Status.UPCOMING;
        prescriptions = new ArrayList<>();
    }

    public Appointment(AppointmentId appointmentId, String doctor, String patient,
                       LocalDateTime dateTime, Status status,
                       String comments, List<Prescription> prescriptions) {
        this.appointmentId = appointmentId;
        this.doctor = doctor;
        this.patient = patient;
        this.dateTime = dateTime;
        this.status = status;
        if (comments != null) {
            this.comments = comments;
        }
        if (prescriptions != null) {
            this.prescriptions = prescriptions;
        } else {
            this.prescriptions = new ArrayList<>();
        }

    }

    public int getAppointmentId() {
        return appointmentId.getAppointmentId();
    }

    public String getDoctor() {
        return doctor;
    }

    public String getPatient() {
        return patient;
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

    /**
     * already verified that prescription exists
     */
    public void deletePrescription(String medicineName) {
        for (Prescription p : prescriptions) {
            if (p.getMedicineName().toString().equals(medicineName)) {
                prescriptions.remove(p);
                break;
            }
        }
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

    /**
     * Returns true if {@code appointments} has the same appointmentId.
     */
    public boolean isSameAppointment(Appointment toCheck) {
        return this.appointmentId.getAppointmentId() == toCheck.getAppointmentId();
    }

    /**
     *  Returns 1 if {@code appointment} is earlier than other appointment
     */
    @Override
    public int compareTo(Appointment o) {
        if (this.isLaterThan(o)) {
            return -1;
        } else if (!this.isLaterThan(o)) {
            return 1;
        } else {
            return 0;
        }
    }
}

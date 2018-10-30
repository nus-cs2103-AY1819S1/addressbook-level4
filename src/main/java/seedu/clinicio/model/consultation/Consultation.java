package seedu.clinicio.model.consultation;

import static seedu.clinicio.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Optional;

import seedu.clinicio.model.appointment.Appointment;
import seedu.clinicio.model.appointment.Date;
import seedu.clinicio.model.appointment.Time;
import seedu.clinicio.model.patient.Patient;
import seedu.clinicio.model.staff.Staff;

//@@author arsalanc-v2

/**
 * Represents a medical consultation involving a patient and staff.
 */
public class Consultation {

    // information fields
    private final Patient patient;
    private Staff staff;
    private Optional<Appointment> appointment;
    private String description;

    // datetime fields
    private final Date date;
    private final Time arrivalTime;
    private Time consultationTime;
    private Time endTime;

    // fields to be changed
    private String prescription;

    /**
     * Initializes a {@code Consultation} object with an {@code Appointment}.
     * This consultation is the result of a requisite appointment.
     * All parameters are required.
     * @param patient The patient to be examined.
     * @param date The date.
     * @param arrivalTime The arrival time of the patient at the clinic.
     * @param appointment The appointment tied to this {@code Consultation} .
     */
    public Consultation(Patient patient, Date date, Time arrivalTime, Appointment appointment) {
        requireAllNonNull(patient, date, arrivalTime, appointment);
        this.patient = patient;
        this.date = date;
        this.arrivalTime = arrivalTime;
        this.appointment = Optional.of(appointment);
    }

    /**
     * Initializes a {@code Consultation} object without an {@code Appointment}.
     * This consultation is a walk-in.
     * All parameters are required.
     * @param patient The patient to be examined.
     * @param date The date.
     * @param arrivalTime The arrival time of the patient at the clinic.
     */
    public Consultation(Patient patient, Date date, Time arrivalTime) {
        requireAllNonNull(patient, date, arrivalTime);
        this.patient = patient;
        this.date = date;
        this.arrivalTime = arrivalTime;
        this.appointment = Optional.empty();
    }

    /**
     * Initializes a {@code Consultation} object with all fields.
     * Used at post-consultation time.
     * This consultation may or may not be the result of a requisite appointment.
     * All parameters are required.
     * @param patient The patient to be examined.
     * @param staff The staff examining the patient.
     * @param appointment The appointment tied to this {@code Consultation} .
     * @param date The date.
     * @param arrivalTime The arrival time of the patient at the clinic.

     */
    public Consultation(Patient patient, Staff staff, Appointment appointment, String description, Date date, Time
        arrivalTime, Time consultationTime, Time endTime, String prescription) {
        requireAllNonNull(patient, staff, appointment, description, date, arrivalTime, consultationTime,
            endTime, prescription);
        this.patient = patient;
        this.staff = staff;
        this.appointment = Optional.of(appointment);
        this.description = description;
        this.date = date;
        this.arrivalTime = arrivalTime;
        this.consultationTime = consultationTime;
        this.endTime = endTime;
        this.prescription = prescription;
    }

    public void updateDoctor(Staff staff) {
        this.staff = staff;
    }

    public void updateConsultationTime(Time time) {
        consultationTime = time;
    }

    public void updateDescription(String description) {
        this.description = description;
    }

    public void updatePrescription(String prescription) {
        this.prescription = prescription;
    }

    public void updateEndTime(Time time) {
        endTime = time;
    }

    /**
     * Denotes a weaker notion of equality between two {@code Consultation} objects.
     * @return {@code true} if {@code patient}, {@code staff}, {@code consultationDate} and {@code consultationTime}
     * fields are
     * the same.
     * {@code false} otherwise
     */
    public boolean isSameConsultation(Consultation toCheck) {
        return toCheck.getPatient().equals(getPatient())
            && toCheck.getStaff().equals(getStaff())
            && toCheck.getConsultationDate().equals(getConsultationDate())
            && toCheck.getConsultationTime().equals(getConsultationTime());
    }

    /**
     * Denotes a stronger notion of equality between two {@code Consultation} objects.
     * @return {@code true} if all {@code Consultation} fields are the same. {@code false} otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Consultation)) {
            return false;
        }

        Consultation otherConsultation = (Consultation) other;
        return otherConsultation.getConsultationDate().equals(getConsultationDate())
            && otherConsultation.getConsultationTime().equals(getConsultationTime())
            && otherConsultation.getPatient().equals(getPatient())
            && otherConsultation.getAppointment().equals(getAppointment())
            && otherConsultation.getStaff().equals(getStaff())
            && otherConsultation.getArrivalTime().equals(getArrivalTime())
            && otherConsultation.getEndTime().equals(getEndTime())
            && otherConsultation.getDescription().equals(getDescription())
            && otherConsultation.getPrescription().equals(getPrescription());
    }

    public Staff getStaff() {
        return staff;
    }

    public Patient getPatient() {
        return patient;
    }

    public Date getConsultationDate() {
        return date;
    }

    public Time getConsultationTime() {
        return consultationTime;
    }

    public Time getArrivalTime() {
        return arrivalTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public String getDescription() {
        return description;
    }

    public String getPrescription() {
        return prescription;
    }

    public Optional<Appointment> getAppointment() {
        return appointment;
    }
}

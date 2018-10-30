package seedu.clinicio.model.consultation;

import static seedu.clinicio.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Optional;

import seedu.clinicio.model.appointment.Appointment;
import seedu.clinicio.model.appointment.Date;
import seedu.clinicio.model.appointment.Time;
import seedu.clinicio.model.consultation.exceptions.NonDoctorException;
import seedu.clinicio.model.patient.Patient;
import seedu.clinicio.model.staff.Role;
import seedu.clinicio.model.staff.Staff;

//@@author arsalanc-v2

/**
 * Represents a medical consultation involving a patient and staff.
 * Guarantees that the staff is a doctor, if present.
 * Consultations may not be completed.
 */
public class Consultation {

    // information fields
    private final Patient patient;
    private Optional<Staff> doctor;
    private Optional<Appointment> appointment;
    private Optional<String> description;

    // datetime fields
    private final Date date;
    private final Time arrivalTime;
    private Optional<Time> consultationTime;
    private Optional<Time> endTime;

    // fields to be changed
    private Optional<String> prescription;

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
        this.appointment = Optional.ofNullable(appointment);
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
     * @param doctor The doctor examining the patient.
     * @param appointment The appointment tied to this consultation.
     * @param description The doctor's notes for this consultation.
     * @param date The date.
     * @param arrivalTime The arrival time of the patient at the clinic.
     * @param consultationTime The start time of the consultation.
     * @param endTime The end time of the consultation.
     * @param prescription The prescription provided by the doctor.
     */
    public Consultation(Patient patient, Staff doctor, Appointment appointment, String description, Date date, Time
        arrivalTime, Time consultationTime, Time endTime, String prescription) {
        requireAllNonNull(patient, doctor, appointment, description, date, arrivalTime, consultationTime,
            endTime, prescription);
        this.patient = patient;
        updateDoctor(doctor);
        this.appointment = Optional.ofNullable(appointment);
        this.description = Optional.ofNullable(description);
        this.date = date;
        this.arrivalTime = arrivalTime;
        this.consultationTime = Optional.ofNullable(consultationTime);
        this.endTime = Optional.ofNullable(endTime);
        this.prescription = Optional.ofNullable(prescription);
    }

    /**
     * Assigns a doctor to this consultation.
     */
    public void updateDoctor(Staff staff) {
        if (!staff.getRole().equals(Role.DOCTOR)) {
            throw new NonDoctorException();
        }
        this.doctor = Optional.ofNullable(staff);
    }

    public void updateConsultationTime(Time time) {
        consultationTime = Optional.ofNullable(time);
    }

    public void updateDescription(String description) {
        this.description = Optional.ofNullable(description);
    }

    public void updatePrescription(String prescription) {
        this.prescription = Optional.ofNullable(prescription);
    }

    public void updateEndTime(Time time) {
        endTime = Optional.ofNullable(time);
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
            && toCheck.getDoctor().equals(getDoctor())
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
            && otherConsultation.getDoctor().equals(getDoctor())
            && otherConsultation.getArrivalTime().equals(getArrivalTime())
            && otherConsultation.getEndTime().equals(getEndTime())
            && otherConsultation.getDescription().equals(getDescription())
            && otherConsultation.getPrescription().equals(getPrescription());
    }

    public Optional<Staff> getDoctor() {
        return doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public Date getConsultationDate() {
        return date;
    }

    public Optional<Time> getConsultationTime() {
        return consultationTime;
    }

    public Time getArrivalTime() {
        return arrivalTime;
    }

    public Optional<Time> getEndTime() {
        return endTime;
    }

    public Optional<String> getDescription() {
        return description;
    }

    public Optional<String> getPrescription() {
        return prescription;
    }

    public Optional<Appointment> getAppointment() {
        return appointment;
    }
}

package seedu.address.model;

import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Date;
import seedu.address.model.appointment.Time;
import seedu.address.model.doctor.Doctor;

/**
 * Represents a medical consultation involving a patient and doctor.
 * @@author arsalanc-v2
 */
public class Consultation {

    // information fields
    private final Doctor doctor;
    private Appointment appointment;
    private String description;

    // datetime fields
    private final Date date;
    private final Time arrivalTime;
    private Time consultationTime;
    private Time endTime;

    // fields to be changed
    private String prescription;
    private final String patient;

    /**
     * Initializes a {@code Consultation} object with an {@code Appointment}.
     * This consultation is the result of a requisite appointment.
     * @param doctor The doctor assigned.
     * @param patient The patient to be examined.
     * @param date The date.
     * @param arrivalTime The arrival time of the patient at the clinic.
     * @param appointment Keeps track of information.
     */
    public Consultation(Doctor doctor, String patient, Date date, Time arrivalTime, Appointment appointment) {
        this.doctor = doctor;
        this.patient = patient;
        this.date = date;
        this.arrivalTime = arrivalTime;
        this.appointment = appointment;
    }

    /**
     * Initializes a {@code Consultation} object without an {@code Appointment}.
     * This consultation is a walk-in.
     * @param doctor The doctor assigned.
     * @param patient The patient to be examined.
     * @param date The date.
     * @param arrivalTime The arrival time of the patient at the clinic.
     */
    public Consultation(Doctor doctor, String patient, Date date, Time arrivalTime) {
        this.doctor = doctor;
        this.patient = patient;
        this.date = date;
        this.arrivalTime = arrivalTime;
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

    public boolean isAppointment() {
        return appointment != null;
    }
}

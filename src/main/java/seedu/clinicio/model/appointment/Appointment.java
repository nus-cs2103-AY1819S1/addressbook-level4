package seedu.clinicio.model.appointment;

import static seedu.clinicio.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.clinicio.model.person.Person;

/**
 * Contains details regarding appointment.
 */
public class Appointment {

    //constants for status
    private static final int APPROVED = 1;
    private static final int CANCELLED = 0;
    //constants for type
    private static final int NEW = 0;
    //an appointment is a follow up if it results directly from a consultation.
    // private static final int FOLLOW_UP = 1;

    //fields used for making appointment
    private final Date appointmentDate;
    private final Time appointmentTime;
    private int appointmentStatus;
    private int appointmentType;

    //to replace with patient class later on
    private final Person patient;

    //to retrieve from patient class later on
    //private final Doctor assignedDoctor;

    public Appointment(Date date, Time time, Person patient, int appointmentType) {
        requireAllNonNull(date, time, patient);
        this.appointmentDate = date;
        this.appointmentTime = time;
        this.patient = patient;
        this.appointmentStatus = APPROVED;
        this.appointmentType = appointmentType;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public Time getAppointmentTime() {
        return appointmentTime;
    }

    public int getAppointmentStatus() {
        return appointmentStatus;
    }

    public Person getPatient() {
        return patient;
    }

    //@@author arsalanc-v2
    public int getAppointmentType() {
        return appointmentType;
    }

    //public Doctor getassignedDoctor() { return assignedDoctor; }

    /**
     * Cancels appointment.
     */
    public void cancelAppointment() {
        this.appointmentStatus = CANCELLED;
    }

    /**
     * Returns true if the appointment has the same date and time slot.
     * @param other Appointment to compare with.
     */
    public boolean isSameSlot(Appointment other) {
        //TODO Add assigned doctor
        return other.getAppointmentDate().equals(getAppointmentDate())
                && other.getAppointmentTime().equals(getAppointmentTime());
    }

    /**
     * Returns true if the appointment has the same patient.
     * @param other Appointment to compare with.
     */
    public boolean isSamePatient(Appointment other) {
        return other.getPatient().equals(getPatient());
    }

    /**
     * Returns true if the appointments are the same.
     * @param  other Appointment to compare with.
     */
    public boolean isSameAppointment(Appointment other) {
        if (other == this) {
            return true;
        }
        return isSamePatient(other) && isSameSlot(other);
    }

    /**
     * Returns true if appointment is cancelled.
     */
    public boolean isCancelled() {
        return appointmentStatus == CANCELLED;
    }

    /**
     * Converts status to string.
     * @return String form of status.
     */
    public String statusToString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (appointmentStatus == APPROVED) {
            stringBuilder.append("APPROVED");
        } else {
            stringBuilder.append("CANCELLED");
        }
        return stringBuilder.toString();
    }

    /**
     * Converts type to string.
     * @return String form of type.
     * @@author arsalanc-v2
     */
    public String typeToString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (appointmentType == NEW) {
            stringBuilder.append("NEW");
        } else {
            stringBuilder.append("FOLLOW-UP");
        }
        return stringBuilder.toString();
    }


    /**
     * Returns true if appointments have the same date, time, patient and status.
     */
    @Override
    public boolean equals(Object other) {
        //TODO Add assignedDoctor
        if (other == this) {
            return true;
        }
        if (!(other instanceof Appointment)) {
            return false;
        }
        Appointment otherAppointment = (Appointment) other;
        return otherAppointment.getAppointmentDate().equals(getAppointmentDate())
                && otherAppointment.getAppointmentTime().equals(getAppointmentTime())
                && otherAppointment.getPatient().equals(getPatient())
                && (otherAppointment.getAppointmentStatus() == getAppointmentStatus())
                && (otherAppointment.getAppointmentType() == getAppointmentType());
    }

    @Override
    public int hashCode() {
        //TODO Add assignedDoctor
        return Objects.hash(appointmentDate, appointmentTime, patient, appointmentStatus, appointmentType);
    }

    @Override
    public String toString() {
        //TODO Add assignedDoctor
        final StringBuilder builder = new StringBuilder();
        builder.append(appointmentDate.toString())
                .append("\n")
                .append(appointmentTime.toString())
                .append("\n")
                .append(patient.toString())
                .append("\n")
                .append(statusToString())
                .append(typeToString());
        return builder.toString();
    }
}

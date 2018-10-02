package seedu.address.model.appointment;

import seedu.address.model.person.Person;

import java.util.Objects;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Contains details regarding appointment.
 */
public class Appointment {

    //fields used for making appointment
    private final Date appointmentDate;
    private final Time appointmentTime;
    private int appointmentStatus;

    private static final int APPROVED = 1;
    private static final int CANCELLED = 0;

    //to replace with patient class later on
    private final Person patient;

    public Appointment(Date date, Time time, Person patient) {
        requireAllNonNull(date, time, patient);
        this.appointmentDate = date;
        this.appointmentTime = time;
        this.patient = patient;
        this.appointmentStatus = APPROVED;
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
     * Returns true if appointments have the same date, time, patient and status.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other instanceof Appointment) {
            return false;
        }
        Appointment otherAppointment = (Appointment) other;
        return otherAppointment.getAppointmentDate().equals(getAppointmentDate())
                && otherAppointment.getAppointmentTime().equals(getAppointmentTime())
                && otherAppointment.getPatient().equals(getPatient())
                && (otherAppointment.getAppointmentStatus() == getAppointmentStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(appointmentDate, appointmentTime, patient, appointmentStatus);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(appointmentDate.toString())
                .append("\n")
                .append(appointmentTime.toString())
                .append("\n")
                .append(patient.toString())
                .append("\n")
                .append(statusToString());
        return builder.toString();
    }
}

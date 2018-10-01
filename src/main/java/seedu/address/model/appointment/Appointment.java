package seedu.address.model.appointment;

import seedu.address.model.person.Person;

import java.util.Objects;

/**
 * Contains details regarding appointment.
 */
public class Appointment {

    //fields used for making appointment
    private static Date appointmentDate;
    private static Time appointmentTime;
    private static int appointmentStatus;

    private static final int APPROVED = 1;
    private static final int CANCELLED = 0;

    //to replace with patient class later on
    private static Person patient;

    public Appointment(Date date, Time time, Person patient) {
        this.appointmentDate = date;
        this.appointmentTime = time;
        this.patient = patient;
        this.appointmentStatus = APPROVED;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public static Time getAppointmentTime() {
        return appointmentTime;
    }

    public static int getAppointmentStatus() {
        return appointmentStatus;
    }

    public static Person getPatient() {
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
        if (appointmentStatus == CANCELLED) {
            return true;
        }
        return false;
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
        return builder.toString();
    }
}

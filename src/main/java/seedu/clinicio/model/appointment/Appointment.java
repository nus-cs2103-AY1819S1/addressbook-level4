package seedu.clinicio.model.appointment;

import static seedu.clinicio.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
import java.util.Optional;

import seedu.clinicio.model.patient.Patient;
import seedu.clinicio.model.staff.Staff;

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
    private static final int FOLLOW_UP = 1;

    //fields used for making appointment
    private final Date appointmentDate;
    private final Time appointmentTime;
    private final Patient patient;
    private int appointmentStatus;
    private int appointmentType;
    private Optional<Staff> assignedStaff;

    public Appointment(Date date, Time time, Patient patient, int appointmentType) {
        requireAllNonNull(date, time, patient);
        this.appointmentDate = date;
        this.appointmentTime = time;
        this.patient = patient;
        this.appointmentStatus = APPROVED;
        this.appointmentType = appointmentType;
        this.assignedStaff = patient.getPreferredDoctor();
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

    public Patient getPatient() {
        return patient;
    }

    //@@author arsalanc-v2
    public int getAppointmentType() {
        return appointmentType;
    }

    public Optional<Staff> getAssignedStaff() {
        return assignedStaff;
    }

    public void setAssignedStaff(Staff staff) {
        assignedStaff = Optional.ofNullable(staff);
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
     * Returns true if the appointment has the same assigned staff.
     * @param other Appointment to compare with.
     */
    public boolean isSameDoctor(Appointment other) {
        return other.getAssignedStaff().equals(getAssignedStaff());
    }

    /**
     * Returns true if the appointments are the same.
     * Status is not considered. Appointments with no assigned doctor may not be the same (TODO).
     * @param  other Appointment to compare with.
     */
    public boolean isSameAppointment(Appointment other) {
        if (other == this) {
            return true;
        }
        return isSamePatient(other) && isSameSlot(other) && isSameDoctor(other);
    }

    /**
     * Returns true if the {@code toCheck} appointment's time slot encroaches {@code this} appointment's duration.
     * Maximum appointment duration is 1 hour. Appointments with no assigned doctor may not be the same (TODO).
     * @param toCheck Appointment to compare with.
     */
    public boolean isOverlapAppointment(Appointment toCheck) {
        if (toCheck == this) {
            return true;
        }
        return (toCheck.getAppointmentTime().subtractMinutes(this.getAppointmentTime()) < 60)
                && (toCheck.getAppointmentTime().subtractMinutes(this.getAppointmentTime()) > -60)
                && isSameDoctor(toCheck);
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
        stringBuilder.append("Status: ");
        if (appointmentStatus == APPROVED) {
            stringBuilder.append("APPROVED");
        } else {
            stringBuilder.append("CANCELLED");
        }
        return stringBuilder.toString();
    }

    //@@author arsalanc-v2
    /**
     * Converts type to string.
     * @return String form of type.
     */
    public String typeToString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Type: ");
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
                && (otherAppointment.getAppointmentType() == getAppointmentType())
                && otherAppointment.getAssignedStaff().equals(getAssignedStaff());
    }

    @Override
    public int hashCode() {
        return Objects.hash(appointmentDate, appointmentTime, patient,
                appointmentStatus, appointmentType, assignedStaff);
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
                .append(statusToString())
                .append("\n")
                .append(typeToString());
        if (assignedStaff.isPresent()) {
            builder.append("\n").append(assignedStaff.toString());
        }
        return builder.toString();
    }
}

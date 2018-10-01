package seedu.address.model.appointment;

import seedu.address.model.person.Person;

/**
 * Contains details regarding appointment.
 */
public class Appointment {

    //fields used for making appointment
    private static int appointmentDate;
    private static int appointmentTime;
    private static int appointmentStatus;

    private static final int APPROVED = 1;
    private static final int CANCELLED = 0;

    //to replace with patient later on
    private static Person patient;

    /**
     * Constructs appointment.
     * appointmentStatus is set to APPROVED as default.
     */
    public Appointment(int date, int time, Person patient) {
        this.appointmentDate = date;
        this.appointmentTime = time;
        this.patient = patient;
        this.appointmentStatus = APPROVED;
    }

    public int getAppointmentDate() {
        return appointmentDate;
    }

    public static int getAppointmentTime() {
        return appointmentTime;
    }

    public static int getAppointmentStatus() {
        return appointmentStatus;
    }

    public static Person getPatient() {
        return patient;
    }

    /**
     * Returns true if appointments have the same time, date, patient and status.
     *
     */
    public boolean isSameAppointment() {
        return false;
    }

    /**
     * Returns true if the appointment has the same date and time slot.
     */
    public boolean isSameSlot() {
        return false;
    }

    /**
     * Returns true if appointment is cancelled.
     */
    public boolean isCancelled() {
        return false;
    }
}

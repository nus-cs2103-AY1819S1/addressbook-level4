package seedu.address.model.doctor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;

/**
 * Represents a Doctor in the health book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Doctor extends Person {
    // Variables
    private List<Appointment> upcomingAppointments;

    // Constructor
    public Doctor(Name name, Phone phone, Email email, Address address, Remark remark,
                  Set<Tag> tags) {
        super(name, phone, email, address, remark, tags);
        upcomingAppointments = new ArrayList<>();
    }

    public Doctor(Name name, Phone phone, Email email, Address address, Remark remark,
                  Set<Tag> tags, List<Appointment> upcomingAppointments) {
        super(name, phone, email, address, remark, tags);
        this.upcomingAppointments = upcomingAppointments;
    }

    /**
     * Adds an upcoming appointment to the doctor's queue of upcoming appointment.
     * The appointment is also added into Doctor's google calendar
     */
    public void addUpcomingAppointment(Appointment appointment) {
        upcomingAppointments.add(appointment);
    }

    public List<Appointment> getUpcomingAppointments() {
        return upcomingAppointments;
    }

    /**
     * Deletes appointment from patient's queue of upcoming appointment.
     */
    public void deleteAppointment(Appointment appointment) {
        upcomingAppointments.remove(appointment);
    }

    /**
     * Completes the latest appointment of the doctor, placing the records of the appointment in to the stack of
     * appointments
     */
    public void completeUpcomingAppointment(Appointment targetAppointment) {
        Appointment appointmentToRemove = null;
        for (Appointment app : upcomingAppointments) {
            if (app.isSameAppointment(targetAppointment)) {
                appointmentToRemove = app;
            }
        }
        appointmentToRemove.completeAppointment();
        upcomingAppointments.remove(appointmentToRemove);
    }
    /**
     * Check if the doctor contains a certain appointment by {@code appointmentId}
     */
    public boolean hasAppointment(int appointmentId) {
        for (Appointment app : upcomingAppointments) {
            if (app.getAppointmentId() == appointmentId) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if appointment clash with any upcoming appointments by {@code targetAppointment}
     */
    public boolean hasClashForAppointment(Appointment appointmentToCheck) {
        for (Appointment app : upcomingAppointments) {
            if (app.hasClashAppointment(appointmentToCheck)) {
                return true; // There is a clash in the appointments
            }
        }
        return false;
    }
}

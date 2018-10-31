package seedu.address.model.doctor;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.PriorityQueue;
import java.util.Set;

import seedu.address.calendar.CalendarManager;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.exceptions.InvalidInputOutputException;
import seedu.address.model.appointment.exceptions.InvalidSecurityAccessException;
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
    private PriorityQueue<Appointment> upcomingAppointments;

    // Constructor
    public Doctor(Name name, Phone phone, Email email, Address address, Remark remark,
                  Set<Tag> tags) {
        super(name, phone, email, address, remark, tags);
        upcomingAppointments = new PriorityQueue<>();
    }

    public Doctor(Name name, Phone phone, Email email, Address address, Remark remark,
                  Set<Tag> tags, PriorityQueue<Appointment> upcomingAppointments) {
        super(name, phone, email, address, remark, tags);
        this.upcomingAppointments = upcomingAppointments;
    }

    /**
     * Adds an upcoming appointment to the doctor's queue of upcoming appointment.
     * The appointment is also added into Doctor's google calendar
     */
    public void addUpcomingAppointment(Appointment appointment) {
        upcomingAppointments.add(appointment);
        CalendarManager calendarManager = new CalendarManager();
        try {
            calendarManager.addAppointment(this.getName().toString(), appointment);
        } catch (GeneralSecurityException e) {
            throw new InvalidSecurityAccessException();
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public PriorityQueue<Appointment> getUpcomingAppointments() {
        return upcomingAppointments;
    }

    /**
     * Deletes appointment from patient's queue of upcoming appointment.
     */
    public void deleteAppointment(Appointment appointment) {
        upcomingAppointments.remove(appointment);
        CalendarManager calendarManager = new CalendarManager();
        try {
            calendarManager.deleteAppointment(this.getName().toString(), appointment);
        } catch (GeneralSecurityException e) {
            throw new InvalidSecurityAccessException();
        } catch (IOException e) {
            throw new InvalidInputOutputException();
        }
    }

    /**
     * Completes the latest appointment of the doctor, placing the records of the appointment in to the stack of
     * appointments
     */
    public void completeUpcomingAppointment(Appointment targetAppointment) {
        for (Appointment app : upcomingAppointments) {
            if (app.isSameAppointment(targetAppointment)) {
                app.completeAppointment();
                upcomingAppointments.remove(app);
                CalendarManager calendarManager = new CalendarManager();
                try {
                    calendarManager.deleteAppointment(this.getName().toString(), app);
                } catch (GeneralSecurityException e) {
                    throw new InvalidSecurityAccessException();
                } catch (IOException e) {
                    throw new InvalidInputOutputException();
                }
            }
        }
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
}

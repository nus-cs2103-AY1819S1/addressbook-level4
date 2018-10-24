package seedu.address.model.doctor;

import java.util.PriorityQueue;
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
    private PriorityQueue<Appointment> upcomingAppointments;

    // Constructor
    public Doctor(Name name, Phone phone, Email email, Address address, Remark remark,
                  Set<Tag> tags) {
        super(name, phone, email, address, remark, tags);
        upcomingAppointments = new PriorityQueue<>();
    }

    /**
     * Adds an upcoming appointment to the doctor's queue of upcoming appointment.
     */
    public void addUpcomingAppointment(Appointment appointment) {
        upcomingAppointments.add(appointment);
    }

    public PriorityQueue<Appointment> getUpcomingAppointments() {
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
    public void completeUpcomingAppointment() {
        Appointment completedAppointment = upcomingAppointments.remove();
    }
}

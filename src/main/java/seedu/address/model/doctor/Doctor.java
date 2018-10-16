package seedu.address.model.doctor;

import java.util.LinkedList;
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
    private String googleOAuth; // TO BE CHANGED TO JSON FILE
    private LinkedList<Appointment> upcomingAppointments;

    // Constructor
    public Doctor(Name name, Phone phone, Email email, Address address, Remark remark,
                   Set<Tag> tags, String googleOAuth) {
        super(name, phone, email, address, remark, tags);
        setGoogleOAuth(googleOAuth);
        upcomingAppointments = new LinkedList<>();
    }

    public void setGoogleOAuth(String googleOAuth) {
        this.googleOAuth = googleOAuth;
    }

    /**
     * Adds an upcoming appointment to the doctor's queue of upcoming appointment.
     */
    public void addUpcomingAppointment(Appointment appointment) {
        Appointment currentAppointment;
        for (int i = 0; i < upcomingAppointments.size(); i++) {
            currentAppointment = upcomingAppointments.get(i);
            if (!appointment.isLaterThan(currentAppointment)) {
                upcomingAppointments.add(i, appointment);
                break;
            }
        }
        // Add appointment to google calendar
        // TO BE COMPLETED
    }

    /**
     * Completes the latest appointment of the doctor, placing the records of the appointment in to the stack of
     * appointments
     */
    public void completeUpcomingAppointment() {
        Appointment completedAppointment = upcomingAppointments.remove();
    }
}

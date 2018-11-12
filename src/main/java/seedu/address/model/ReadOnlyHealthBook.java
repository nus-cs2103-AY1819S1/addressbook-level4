package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Person;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyHealthBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

    /**
     * Returns an unmodifiable view of the appointment list.
     * This list will not contain any duplicate appointment.
     */
    ObservableList<Appointment> getAppointmentList();

    /**
     * Returns an current appointment.
     */
    int getAppointmentCounter();

}

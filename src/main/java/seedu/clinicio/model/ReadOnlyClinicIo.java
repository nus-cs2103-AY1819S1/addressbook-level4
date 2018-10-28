package seedu.clinicio.model;

import javafx.collections.ObservableList;
import seedu.clinicio.model.appointment.Appointment;
import seedu.clinicio.model.doctor.Doctor;
import seedu.clinicio.model.person.Person;

/**
 * Unmodifiable view of a ClinicIO
 */
public interface ReadOnlyClinicIo {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

    /**
     * Returns an unmodifiable view of the doctors list.
     * This list will not contain any duplicate doctors.
     */
    ObservableList<Doctor> getDoctorList();

    /**
     * Returns an unmodifiable view of the appointment list.
     * This list will not contain any duplicate appointments.
     */
    ObservableList<Appointment> getAppointmentList();
}

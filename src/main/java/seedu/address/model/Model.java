package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;

import seedu.address.model.appointment.Appointment;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    Predicate<Appointment> PREDICATE_SHOW_ALL_APPOINTMENTS = unused -> true;

    /**
     * Clears existing backing model and replaces with the provided new data.
     */
    void resetData(ReadOnlyAddressBook newData);

    /**
     * Returns the AddressBook
     */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the health book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the health book.
     */
    void addPerson(Person person);

    /**
     * Adds the given patient.
     * {@code patient} must not already exist in the health book.
     */
    void addPatient(Patient patient);

    /**
     * Adds the given doctor.
     * {@code doctor} must not already exist in the health book.
     */
    void addDoctor(Doctor doctor);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the health book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the health book.
     */
    void updatePerson(Person target, Person editedPerson);

    /**
     * Replaces the given appointment {@code target} with {@code editedAppointment}
     * {@code target} must exist in the health book.
     * The appointment identity of {@code editedAppointment} must not be the same as another existing appointment
     * in the health book.
     */
    void updateAppointment(Appointment target, Appointment editedAppointment);

    /**
     * Returns an unmodifiable view of the filtered person list
     */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Returns an unmodifiable view of the filtered appointment list
     */
    ObservableList<Appointment> getFilteredAppointmentList();

    /**
     * Updates the filter of the filtered appointment list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredAppointmentList(Predicate<Appointment> predicate);

    /**
     * Returns current appointmentCounter
     */
    int getAppointmentCounter();

    /**
     * Increase the Appointment Counter
     */
    void incrementAppointmentCounter();

    /**
     * Returns true if the model has previous health book states to restore.
     */
    boolean canUndoAddressBook();

    /**
     * Returns true if the model has undone health book states to restore.
     */
    boolean canRedoAddressBook();

    /**
     * Restores the model's health book to its previous state.
     */
    void undoAddressBook();

    /**
     * Restores the model's health book to its previously undone state.
     */
    void redoAddressBook();

    /**
     * Adds appointment.
     */
    void addAppointment(Appointment appointment);

    /**
     * Deletes appointment.
     */
    void deleteAppointment(Appointment appointment, Patient patient, Doctor doctor);

    /**
     * Completes appointment.
     */
    void completeAppointment(Appointment appointment, Patient patient, Doctor doctor);

    /**
     * Saves the current address book state for undo/redo.
     */
    void commitAddressBook();
}

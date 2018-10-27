package seedu.clinicio.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;

import seedu.clinicio.model.appointment.Appointment;
import seedu.clinicio.model.doctor.Doctor;
import seedu.clinicio.model.person.Person;
import seedu.clinicio.model.receptionist.Receptionist;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Doctor> PREDICATE_SHOW_ALL_DOCTORS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Receptionist> PREDICATE_SHOW_ALL_RECEPTIONISTS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Appointment> PREDICATE_SHOW_ALL_APPOINTMENTS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyClinicIo newData);

    /** Returns the ClinicIo */
    ReadOnlyClinicIo getClinicIo();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the ClinicIO.
     */
    boolean hasPerson(Person person);

    /**
     * Returns true if a doctor with the same identity as {@code doctor} exists in the ClinicIO.
     */
    boolean hasDoctor(Doctor doctor);

    /**
     * Returns true if a receptionist with the same identity as {@code receptionist} exists in ClinicIO.
     */
    boolean hasReceptionist(Receptionist receptionist);

    /**
     * Deletes the given person.
     * The person must exist in the ClinicIO.
     */
    void deletePerson(Person target);

    /**
     * Deletes the given doctor.
     * The doctor must exist in the ClinicIO.
     */
    void deleteDoctor(Doctor doctor);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the ClinicIO.
     */
    void addPerson(Person person);

    /**
     * Adds the given doctor.
     * {@code doctor} must not already exist in the ClinicIO.
     */
    void addDoctor(Doctor doctor);

    /**
     * Adds the given receptionist.
     * {@code receptionist} must not already exist in the address book.
     */
    void addReceptionist(Receptionist receptionist);

    /**
     * Retrieve the given doctor
     * {@code doctor} must exist in ClinicIO.
     */
    Doctor getDoctor(Doctor doctor);

    /**
     * Retrieve the given receptionist
     * {@code receptionist} must exist in ClinicIO.
     */
    Receptionist getReceptionist(Receptionist receptionist);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the ClinicIO.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the ClinicIO.
     */
    void updatePerson(Person target, Person editedPerson);

    /**
     * Replaces the given doctor {@code target} with {@code editedDoctor}.
     * {@code target} must exist in the ClinicIO.
     * The doctor identity of {@code editedDoctor} must not be the same as another existing doctor in the ClinicIO.
     */
    void updateDoctor(Doctor target, Doctor editedDoctor);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /** Returns an unmodifiable view of the filtered doctor list */
    ObservableList<Doctor> getFilteredDoctorList();

    /** Returns an unmodifiable view of the filtered receptionist list */
    ObservableList<Receptionist> getFilteredReceptionistList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Updates the filter of the filtered doctor list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredDoctorList(Predicate<Doctor> predicate);

    /**
<<<<<<< HEAD:src/main/java/seedu/address/model/Model.java
     * Updates the filter of the filtered receptionist list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredReceptionistList(Predicate<Receptionist> predicate);

    /**
     * Returns true if the model has previous address book states to restore.
=======
     * Returns true if the model has previous ClinicIO states to restore.
>>>>>>> c74e2f3c11aa4c44f85f91717dbf4b8bea4546f0:src/main/java/seedu/clinicio/model/Model.java
     */
    boolean canUndoClinicIo();

    /**
     * Returns true if the model has undone ClinicIO states to restore.
     */
    boolean canRedoClinicIo();

    /**
     * Restores the model's ClinicIO to its previous state.
     */
    void undoClinicIo();

    /**
     * Restores the model's ClinicIO to its previously undone state.
     */
    void redoClinicIo();

    /**
     * Saves the current ClinicIO state for undo/redo.
     */
    void commitClinicIo();

    /**
     * Returns true if an appointment with the same identity as {@code appointment} exists in the schedule.
     */
    boolean hasAppointment(Appointment appt);

    /**
     * Returns true if an appointment clashes with another appointment.
     */
    boolean hasAppointmentClash(Appointment appt);
    /**
     * Deletes the given appointment.
     * Not to be directly accessed by the user.
     */
    void deleteAppointment(Appointment target);

    /**
     * Cancels the given appointment.
     * The appointment must exist in the schedule.
     */
    void cancelAppointment(Appointment target);

    /**
     * Adds the given appointment.
     * {@code appt} must not already exist in the schedule.
     */
    void addAppointment(Appointment appt);

    /**
     * Replaces the given appointment {@code appt} with {@code editedAppt}.
     * {@code appt} must exist in the schedule.
     * The appointment timing of {@code editedAppt} must not be the same as another existing appointment.
     */
    void updateAppointment(Appointment appt, Appointment editedAppt);

    /** Returns an unmodifiable view of the appointment list. */
    ObservableList<Appointment> getFilteredAppointmentList();

    /**
     * Updates the filter of the filtered appointment list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredAppointmentList(Predicate<Appointment> predicate);

    //@@author iamjackslayer
    /**
     * Enqueues the given person.
     * TODO Change Person object to Patient Object
     */
    void enqueue(Person patient);

    //@@author iamjackslayer
    /**
     * Enqueues the given person into preference queue.
     */
    // TODO Change Person object to Patient Object
    void enqueueIntoPreferenceQueue(Person patient);

    //@@author iamjackslayer
    /**
     * Check if patient exists in the patient queue.
     */
    boolean hasPatientInMainQueue();

    //@@author iamjackslayer
    /**
     * Check if patient exists in the patient queue.
     */
    boolean hasPatientInPreferenceQueue();

    /**
     * Check if patient exists in the patient queue.
     */
    boolean hasPatientInPatientQueue();
}

package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.person.Person;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedAddressBook versionedAddressBook;
    private final FilteredList<Person> filteredPersons;
    private final PatientQueue<Person> patientQueue;
    private final FilteredList<Doctor> filteredDoctors;


    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        versionedAddressBook = new VersionedAddressBook(addressBook);
        //@@author jjlee050
        filteredPersons = new FilteredList<>(versionedAddressBook.getPersonList());
        filteredDoctors = new FilteredList<>(versionedAddressBook.getDoctorList());
        patientQueue = new PatientQueue();

    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        versionedAddressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return versionedAddressBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(versionedAddressBook));
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return versionedAddressBook.hasPerson(person);
    }

    //@@author jjlee050
    @Override
    public boolean hasDoctor(Doctor doctor) {
        requireNonNull(doctor);
        return versionedAddressBook.hasDoctor(doctor);
    }

    @Override
    public void deletePerson(Person target) {
        versionedAddressBook.removePerson(target);
        indicateAddressBookChanged();
    }

    //@@author jjlee050
    @Override
    public void deleteDoctor(Doctor target) {
        versionedAddressBook.removeDoctor(target);
        indicateAddressBookChanged();
    }

    @Override
    public void addPerson(Person person) {
        versionedAddressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    //@@author jjlee050
    @Override
    public void addDoctor(Doctor doctor) {
        versionedAddressBook.addDoctor(doctor);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }
    @Override
    public void enqueue(Person target) {
        patientQueue.add(target);
    }
    @Override
    public void updatePerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);
        versionedAddressBook.updatePerson(target, editedPerson);
        indicateAddressBookChanged();
    }
    @Override
    public boolean hasPatientInPatientQueue() {
        return patientQueue.hasPatient();
    }
    //@@author jjlee050
    @Override
    public void updateDoctor(Doctor target, Doctor editedDoctor) {
        requireAllNonNull(target, editedDoctor);
        versionedAddressBook.updateDoctor(target, editedDoctor);
        indicateAddressBookChanged();
    }
    //=========== Filtered Person List Accessors =============================================================
    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(filteredPersons);
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    //=========== Filtered Doctor List Accessors =============================================================

    //@@author jjlee050
    /**
     * Returns an unmodifiable view of the list of {@code Doctor} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Doctor> getFilteredDoctorList() {
        return FXCollections.unmodifiableObservableList(filteredDoctors);
    }

    //@@author jjlee050
    @Override
    public void updateFilteredDoctorList(Predicate<Doctor> predicate) {
        requireNonNull(predicate);
        filteredDoctors.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoAddressBook() {
        return versionedAddressBook.canUndo();
    }

    @Override
    public boolean canRedoAddressBook() {
        return versionedAddressBook.canRedo();
    }

    @Override
    public void undoAddressBook() {
        versionedAddressBook.undo();
        indicateAddressBookChanged();
    }

    @Override
    public void redoAddressBook() {
        versionedAddressBook.redo();
        indicateAddressBookChanged();
    }

    @Override
    public void commitAddressBook() {
        versionedAddressBook.commit();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        //@@author jjlee050
        return versionedAddressBook.equals(other.versionedAddressBook)
                && filteredPersons.equals(other.filteredPersons)
                && filteredDoctors.equals(other.filteredDoctors);
    }

    //=========== For scheduling appointments =================================================================
    //Everything below is TODO

    /** Clears existing backing model and replaces with the provided new data. */
    //void resetData(ReadOnlySchedule newData);

    /** Returns the schedule */
    //ReadOnlySchedule getSchedule();

    @Override
    public boolean hasAppointment(Appointment appt) {
        //to do
        return false;
    }

    @Override
    public void cancelAppointment(Appointment appt) {
        //to do
    }

    @Override
    public void addAppointment(Appointment appt) {
        //to do
    }

    @Override
    public void updateAppointment(Appointment appt, Appointment editedAppt) {
        //to do
    }

    @Override
    public ObservableList<Appointment> getFilteredAppointmentList() {
        //to do
        return null;
    }

    @Override
    public void updateFilteredAppointmentList(Predicate<Person> predicate) {
        //to do
    }

    @Override
    public boolean canUndoSchedule() {
        //to do
        return false;
    }

    @Override
    public boolean canRedoSchedule() {
        //to do
        return false;
    }

    @Override
    public void undoSchedule() {
        //to do
    }

    @Override
    public void redoSchedule() {
        //to do
    }

    @Override
    public void commitSchedule() {
        //to do
    }
}

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
import seedu.address.commons.events.model.TranscriptChangedEvent;
import seedu.address.model.capgoal.CapGoal;
import seedu.address.model.module.Code;
import seedu.address.model.module.Grade;
import seedu.address.model.module.Module;
import seedu.address.model.module.Semester;
import seedu.address.model.module.Year;
import seedu.address.model.module.exceptions.ModuleNotFoundException;
import seedu.address.model.module.exceptions.MultipleModuleEntryFoundException;
import seedu.address.model.person.Person;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger =
            LogsCenter.getLogger(ModelManager.class);

    //TODO: REMOVE LEGACY CODE
    private final VersionedAddressBook versionedAddressBook;
    private final FilteredList<Person> filteredPersons;

    //@@author alexkmj
    private final VersionedTranscript versionedTranscript;
    private final FilteredList<Module> filteredModules;

    /**
     * Initializes a ModelManager with the given transcript and userPrefs.
     */
    public ModelManager(ReadOnlyTranscript transcript, UserPrefs userPrefs) {
        super();
        requireAllNonNull(transcript, userPrefs);

        logger.fine("Initializing with transcript: "
                + transcript
                + " and user prefs "
                + userPrefs);

        versionedTranscript = new VersionedTranscript(transcript);
        filteredModules = new FilteredList<>(
                versionedTranscript.getModuleList());

        //TODO: REMOVE LEGACY CODE
        versionedAddressBook = new VersionedAddressBook(new AddressBook());
        filteredPersons = new FilteredList<>(
                versionedAddressBook.getPersonList());
    }

    public ModelManager() {
        this(new Transcript(), new UserPrefs());
    }

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     * TODO: REMOVE LEGACY CODE
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook
                + " and user prefs " + userPrefs);

        versionedAddressBook = new VersionedAddressBook(addressBook);
        filteredPersons = new FilteredList<>(
                versionedAddressBook.getPersonList());
        versionedTranscript = new VersionedTranscript(new Transcript());
        filteredModules = new FilteredList<>(
                versionedTranscript.getModuleList());
    }

    /**
     * Clears existing backing model and replaces with the newly provided data.
     *
     * @param replacement the replacement.
     */
    @Override
    public void resetData(ReadOnlyTranscript replacement) {
        versionedTranscript.resetData(replacement);
        indicateTranscriptChanged();
    }

    //TODO: REMOVE LEGACY CODE
    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        versionedAddressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    /**
     * Returns the Transcript.
     *
     * @return read only version of the transcript
     */
    @Override
    public ReadOnlyTranscript getTranscript() {
        return versionedTranscript;
    }

    /**
     * Raises an event to indicate the model has changed.
     */
    private void indicateTranscriptChanged() {
        raise(new TranscriptChangedEvent(versionedTranscript));
    }

    /**
     * Returns true if a module with the same identity as {@code module} exists
     * in the transcript.
     *
     * @param module module to find in the transcript
     * @return true if module exists in transcript
     */
    @Override
    public boolean hasModule(Module module) {
        requireNonNull(module);
        return versionedTranscript.hasModule(module);
    }

    /**
     * Returns the matching module entry.
     * <p>
     * Finds the module with {@code targetCode}, {@code targetYear} if
     * {@code targetYear} is not null, and {@code targetSemester} if
     * {@code targetSemester} is not null.
     *
     * @param targetCode code to match
     * @param targetYear year to match if not null
     * @param targetSemester semester to match if not null
     * @return the matching module
     * @throws ModuleNotFoundException thrown when no entries match the
     * parameters.
     * @throws MultipleModuleEntryFoundException thrown when multiple entries
     * match the parameters.
     */
    @Override
    public Module getOnlyOneModule(Code targetCode, Year targetYear,
            Semester targetSemester)
            throws ModuleNotFoundException, MultipleModuleEntryFoundException {
        return versionedTranscript.getOnlyOneModule(targetCode,
                targetYear,
                targetSemester);
    }

    /**
     * Adds the given module.
     * <p>
     * {@code module} must not already exist in the transcript.
     *
     * @param module module to be added into the transcript
     */
    @Override
    public void addModule(Module module) {
        versionedTranscript.addModule(module);
        updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
        indicateTranscriptChanged();
    }

    /**
     * Deletes the given module.
     * <p>
     * The module must exist in the transcript.
     *
     * @param target module to be deleted from the transcript
     */
    @Override
    public void deleteModule(Module target) {
        versionedTranscript.removeModule(target);
        indicateTranscriptChanged();
    }

    /**
     * Replaces the given module {@code target} with {@code editedModule}.
     * {@code target} must exist in the transcript. The module identity of
     * {@code editedModule} must not be the same as another existing module in
     * the transcript.
     *
     * @param target module to be updated
     * @param editedModule the updated version of the module
     */
    @Override
    public void updateModule(Module target, Module editedModule) {
        requireAllNonNull(target, editedModule);

        versionedTranscript.updateModule(target, editedModule);
        indicateTranscriptChanged();
    }

    //=========== Filtered Module List Accessors ===============================

    /**
     * Returns an unmodifiable view of the list of {@code Module} backed by the
     * internal list of {@code versionedTranscript}
     */
    @Override
    public ObservableList<Module> getFilteredModuleList() {
        return FXCollections.unmodifiableObservableList(filteredModules);
    }

    /**
     * Updates the filter of the filtered module list to filter by the given
     * {@code predicate}.
     */
    @Override
    public void updateFilteredModuleList(Predicate<Module> predicate) {
        requireNonNull(predicate);
        filteredModules.setPredicate(predicate);
    }

    //=========== Undo/Redo ====================================================

    /**
     * Returns true if the model has previous transcript states to restore.
     *
     * @return true if transcript can be undone
     */
    @Override
    public boolean canUndoTranscript() {
        return versionedTranscript.canUndo();
    }

    /**
     * Returns true if the model has undone transcript states to restore.
     *
     * @return true if transcript can be redone
     */
    @Override
    public boolean canRedoTranscript() {
        return versionedTranscript.canRedo();
    }

    /**
     * Restores the model's transcript to its previous state.
     */
    @Override
    public void undoTranscript() {
        versionedTranscript.undo();
        indicateTranscriptChanged();
    }

    /**
     * Restores the model's transcript to its previously undone state.
     */
    @Override
    public void redoTranscript() {
        versionedTranscript.redo();
        indicateTranscriptChanged();
    }

    /**
     * Saves the current transcript state for undo/redo.
     */
    @Override
    public void commitTranscript() {
        versionedTranscript.commit();
    }
    //@@author

    //@@author jeremiah-ang
    @Override
    public CapGoal getCapGoal() {
        return versionedTranscript.getCapGoal();
    }

    @Override
    public void updateCapGoal(double capGoal) {
        versionedTranscript.setCapGoal(capGoal);
        indicateTranscriptChanged();
    }

    @Override
    public double getCap() {
        return versionedTranscript.getCurrentCap();
    }

    @Override
    public ObservableList<Module> getCompletedModuleList() {
        return versionedTranscript.getCompletedModuleList();
    }

    @Override
    public ObservableList<Module> getIncompleteModuleList() {
        return versionedTranscript.getIncompleteModuleList();
    }
  
    @Override
    public Module adjustModule(Module targetModule, Grade adjustGrade) {
        Module adjustedModule = versionedTranscript.adjustModule(targetModule, adjustGrade);
        indicateTranscriptChanged();
        return adjustedModule;
    }
    //@@author

    //@@author alexkmj
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
        return versionedAddressBook.equals(other.versionedAddressBook)
                && filteredPersons.equals(other.filteredPersons) // TODO: REMOVE
                && filteredModules.equals(other.filteredModules);
    }
    //@@author

    //TODO: REMOVE LEGACY CODES BELOW

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

    @Override
    public void deletePerson(Person target) {
        versionedAddressBook.removePerson(target);
        indicateAddressBookChanged();
    }

    @Override
    public void addPerson(Person person) {
        versionedAddressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    @Override
    public void updatePerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        versionedAddressBook.updatePerson(target, editedPerson);
        indicateAddressBookChanged();
    }

    //=========== Filtered Person List Accessors ===============================

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(filteredPersons);
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    //=========== Undo/Redo ====================================================

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
}

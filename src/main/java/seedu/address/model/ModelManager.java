package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.commons.util.TypeUtil.PERSON;

import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.util.TypeUtil;
import seedu.address.model.module.Module;
import seedu.address.model.occasion.Occasion;
import seedu.address.model.person.Person;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedAddressBook versionedAddressBook;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Occasion> filteredOccasions;
    private final FilteredList<Module> filteredModules;
    private TypeUtil activeType;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        versionedAddressBook = new VersionedAddressBook(addressBook);
        filteredPersons = new FilteredList<>(versionedAddressBook.getPersonList());
        filteredModules = new FilteredList<>(versionedAddressBook.getModuleList());
        filteredOccasions = new FilteredList<>(versionedAddressBook.getOccasionList());
        activeType = PERSON;
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
    public void importData(ReadOnlyAddressBook importedData) {
        ObservableList<Person> importedPerson = importedData.getPersonList();
        ObservableList<Module> importedModule = importedData.getModuleList();
        ObservableList<Occasion> importedOccasion = importedData.getOccasionList();

        for (Person p : importedPerson) {
            if (this.hasPerson(p)) {
                continue;
            }
            versionedAddressBook.addPerson(p);
        }

        for (Module m : importedModule) {
            if (this.hasModule(m)) {
                continue;
            }
            versionedAddressBook.addModule(m);
        }

        for (Occasion o : importedOccasion) {
            if (this.hasOccasion(o)) {
                continue;
            }
            versionedAddressBook.addOccasion(o);
        }

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
    public TypeUtil getActiveType() {
        return activeType;
    }

    @Override
    public void setActiveType(TypeUtil newActiveType) {
        activeType = newActiveType;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return versionedAddressBook.hasPerson(person);
    }

    @Override
    public boolean hasModule(Module module) {
        requireNonNull(module);
        return versionedAddressBook.hasModule(module);
    }

    @Override
    public boolean hasOccasion(Occasion occasion) {
        requireNonNull(occasion);
        return versionedAddressBook.hasOccasion(occasion);
    }


    @Override
    public void addPerson(Person person) {
        versionedAddressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    @Override
    public void addOccasion(Occasion occasion) {
        versionedAddressBook.addOccasion(occasion);
        updateFilteredOccasionList(PREDICATE_SHOW_ALL_OCCASIONS);
        indicateAddressBookChanged();
    }

    @Override
    public void addModule(Module module) {
        versionedAddressBook.addModule(module);
        updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
        indicateAddressBookChanged();
    }

    @Override
    public void updatePerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        versionedAddressBook.updatePerson(target, editedPerson);
        indicateAddressBookChanged();
    }

    @Override
    public void updateModule(Module target, Module editedModule) {
        requireAllNonNull(target, editedModule);

        versionedAddressBook.updateModule(target, editedModule);
        indicateAddressBookChanged();
    }

    @Override
    public void updateOccasion(Occasion target, Occasion editedOccasion) {
        requireAllNonNull(target, editedOccasion);

        versionedAddressBook.updateOccasion(target, editedOccasion);
        indicateAddressBookChanged();
    }

    @Override
    public void deletePerson(Person target) {
        versionedAddressBook.removePerson(target);
        indicateAddressBookChanged();
    }

    @Override
    public void deleteModule(Module target) {
        versionedAddressBook.removeModule(target);
        indicateAddressBookChanged();
    }

    @Override
    public void deleteOccasion(Occasion target) {
        versionedAddressBook.removeOccasion(target);
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

    //=========== Filtered Module List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Module} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Module> getFilteredModuleList() {
        return FXCollections.unmodifiableObservableList(filteredModules);
    }

    @Override
    public void updateFilteredModuleList(Predicate<Module> predicate) {
        requireNonNull(predicate);
        filteredModules.setPredicate(predicate);
    }

    //=========== Filtered Occasion List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Occasion} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Occasion> getFilteredOccasionList() {
        return FXCollections.unmodifiableObservableList(filteredOccasions);
    }

    @Override
    public void updateFilteredOccasionList(Predicate<Occasion> predicate) {
        requireNonNull(predicate);
        filteredOccasions.setPredicate(predicate);
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
        return versionedAddressBook.equals(other.versionedAddressBook)
                && filteredPersons.equals(other.filteredPersons)
                && filteredModules.equals(other.filteredModules)
                && filteredOccasions.equals(other.filteredOccasions);
    }

}

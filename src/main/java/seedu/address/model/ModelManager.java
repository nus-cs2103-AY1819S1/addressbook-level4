package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.AddressBookExportEvent;
import seedu.address.commons.events.model.UserPrefsChangeEvent;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;
import seedu.address.model.person.util.PersonPropertyComparator;
import seedu.address.model.tag.Tag;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedAddressBook versionedAddressBook;
    private final FilteredList<Person> filteredPersons;
    private final UserPrefs userPrefs;
    private final SortedList<Person> sortedPersons;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        versionedAddressBook = new VersionedAddressBook(addressBook);
        filteredPersons = new FilteredList<>(versionedAddressBook.getPersonList());
        this.userPrefs = userPrefs;
        sortedPersons = new SortedList<>(filteredPersons);
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

    @Override
    public void updateGroup(Group target, Group editedGroup) {
        requireAllNonNull(target, editedGroup);

        versionedAddressBook.updateGroup(target, editedGroup);
        indicateAddressBookChanged();
    }


    //=========== AddGroup / RemoveGroup =====================================================================

    @Override
    public void addGroup(Person target, Tag newGroup) {
        //TODO
    }

    @Override
    public void removeGroup(Person target, Tag oldGroup) {
        //TODO
    }

    // @@author NyxF4ll
    @Override
    public boolean hasGroup(Group group) {
        return versionedAddressBook.getGroupList().stream().anyMatch(group::isSameGroup);
    }

    @Override
    public ObservableList<Group> getGroupList() {
        return versionedAddressBook.getGroupList();
    }
    // @@author

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

    //=========== Sorted Person List Accessors ==============================================================

    @Override
    public ObservableList<Person> getSortedPersonList() {
        return FXCollections.unmodifiableObservableList(sortedPersons);
    }

    @Override
    public void updateSortedPersonList(PersonPropertyComparator personPropertyComparator) {
        requireNonNull(personPropertyComparator);
        sortedPersons.setComparator(personPropertyComparator.getComparator());
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

    // ================= Export/Import =======================================================================

    @Override
    public void exportAddressBook(Path filepath) {
        raise(new AddressBookExportEvent(versionedAddressBook, filepath));
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void changeUserPrefs(Path filepath) {
        Path currentPath = userPrefs.getAddressBookFilePath();
        userPrefs.setAddressBookFilePath(filepath);
        raise(new UserPrefsChangeEvent(userPrefs, versionedAddressBook, currentPath, filepath));
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
                && sortedPersons.equals(other.sortedPersons);
    }

}

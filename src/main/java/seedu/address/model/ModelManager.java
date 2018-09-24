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
import seedu.address.model.person.Task;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedTaskManager versionedAddressBook;
    private final FilteredList<Task> filteredPersons;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyTaskManager addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        versionedAddressBook = new VersionedTaskManager(addressBook);
        filteredPersons = new FilteredList<>(versionedAddressBook.getTaskList());
    }

    public ModelManager() {
        this(new TaskManager(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyTaskManager newData) {
        versionedAddressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyTaskManager getTaskManager() {
        return versionedAddressBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(versionedAddressBook));
    }

    @Override
    public boolean hasTask(Task task) {
        requireNonNull(task);
        return versionedAddressBook.hasTask(task);
    }

    @Override
    public void deleteTask(Task target) {
        versionedAddressBook.removeTask(target);
        indicateAddressBookChanged();
    }

    @Override
    public void addTask(Task task) {
        versionedAddressBook.addTask(task);
        updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        indicateAddressBookChanged();
    }

    @Override
    public void updateTask(Task target, Task editedTask) {
        requireAllNonNull(target, editedTask);

        versionedAddressBook.updateTask(target, editedTask);
        indicateAddressBookChanged();
    }

    //=========== Filtered Task List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Task} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Task> getFilteredTaskList() {
        return FXCollections.unmodifiableObservableList(filteredPersons);
    }

    @Override
    public void updateFilteredTaskList(Predicate<Task> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoTaskManager() {
        return versionedAddressBook.canUndo();
    }

    @Override
    public boolean canRedoTaskManager() {
        return versionedAddressBook.canRedo();
    }

    @Override
    public void undoTaskManager() {
        versionedAddressBook.undo();
        indicateAddressBookChanged();
    }

    @Override
    public void redoTaskManager() {
        versionedAddressBook.redo();
        indicateAddressBookChanged();
    }

    @Override
    public void commitTaskManager() {
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
                && filteredPersons.equals(other.filteredPersons);
    }

}

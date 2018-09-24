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
 * Represents the in-memory model of the task manager data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedTaskManager versionedTaskManager;
    private final FilteredList<Task> filteredTasks;

    /**
     * Initializes a ModelManager with the given taskManager and userPrefs.
     */
    public ModelManager(ReadOnlyTaskManager taskManager, UserPrefs userPrefs) {
        super();
        requireAllNonNull(taskManager, userPrefs);

        logger.fine("Initializing with task manager: " + taskManager + " and user prefs " + userPrefs);

        versionedTaskManager = new VersionedTaskManager(taskManager);
        filteredTasks = new FilteredList<>(versionedTaskManager.getTaskList());
    }

    public ModelManager() {
        this(new TaskManager(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyTaskManager newData) {
        versionedTaskManager.resetData(newData);
        indicateTaskManagerChanged();
    }

    @Override
    public ReadOnlyTaskManager getTaskManager() {
        return versionedTaskManager;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateTaskManagerChanged() {
        raise(new AddressBookChangedEvent(versionedTaskManager));
    }

    @Override
    public boolean hasTask(Task task) {
        requireNonNull(task);
        return versionedTaskManager.hasTask(task);
    }

    @Override
    public void deleteTask(Task target) {
        versionedTaskManager.removeTask(target);
        indicateTaskManagerChanged();
    }

    @Override
    public void addTask(Task task) {
        versionedTaskManager.addTask(task);
        updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        indicateTaskManagerChanged();
    }

    @Override
    public void updateTask(Task target, Task editedTask) {
        requireAllNonNull(target, editedTask);

        versionedTaskManager.updateTask(target, editedTask);
        indicateTaskManagerChanged();
    }

    //=========== Filtered Task List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Task} backed by the internal list of
     * {@code versionedTaskManager}
     */
    @Override
    public ObservableList<Task> getFilteredTaskList() {
        return FXCollections.unmodifiableObservableList(filteredTasks);
    }

    @Override
    public void updateFilteredTaskList(Predicate<Task> predicate) {
        requireNonNull(predicate);
        filteredTasks.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoTaskManager() {
        return versionedTaskManager.canUndo();
    }

    @Override
    public boolean canRedoTaskManager() {
        return versionedTaskManager.canRedo();
    }

    @Override
    public void undoTaskManager() {
        versionedTaskManager.undo();
        indicateTaskManagerChanged();
    }

    @Override
    public void redoTaskManager() {
        versionedTaskManager.redo();
        indicateTaskManagerChanged();
    }

    @Override
    public void commitTaskManager() {
        versionedTaskManager.commit();
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
        return versionedTaskManager.equals(other.versionedTaskManager)
                && filteredTasks.equals(other.filteredTasks);
    }

}

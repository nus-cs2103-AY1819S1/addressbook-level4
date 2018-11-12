package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.ExportRequestEvent;
import seedu.address.commons.events.model.ImportRequestEvent;
import seedu.address.commons.events.model.TaskCollectionChangedEvent;
import seedu.address.commons.events.storage.ImportDataAvailableEvent;
import seedu.address.model.task.Task;


/**
 * Represents the in-memory model of the deadline manager data.
 */
public class ModelManager extends ComponentManager implements Model {

    /**
     * An enum representing the possible conflict resolvers.
     */
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedTaskCollection versionedTaskCollection;
    private final FilteredList<Task> filteredTasks;

    private String lastError;
    private ImportConflictResolver conflictResolver;

    /**
     * Initializes a ModelManager with the given taskCollection and userPrefs.
     */
    public ModelManager(ReadOnlyTaskCollection taskCollection, UserPrefs userPrefs) {
        super();
        requireAllNonNull(taskCollection, userPrefs);

        logger.fine("Initializing with deadline manager: " + taskCollection + " and user prefs " + userPrefs);

        versionedTaskCollection = new VersionedTaskCollection(taskCollection);
        filteredTasks = new FilteredList<>(versionedTaskCollection.getTaskList());
        lastError = null;
    }

    public ModelManager() {
        this(new TaskCollection(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyTaskCollection newData) {
        versionedTaskCollection.resetData(newData);
        indicateTaskCollectionChanged();
    }

    @Override
    public ReadOnlyTaskCollection getTaskCollection() {
        return versionedTaskCollection;
    }

    /**
     * Raises an event to indicate the model has changed
     */
    private void indicateTaskCollectionChanged() {
        raise(new TaskCollectionChangedEvent(versionedTaskCollection));
    }

    @Override
    public boolean hasTask(Task task) {
        requireNonNull(task);
        return versionedTaskCollection.hasTask(task);
    }

    @Override
    public void deleteTask(Task target) {
        versionedTaskCollection.removeTask(target);
        indicateTaskCollectionChanged();
    }

    @Override
    public void addTask(Task task) {
        versionedTaskCollection.addTask(task);
        updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        indicateTaskCollectionChanged();
    }

    @Override
    public void updateTask(Task target, Task editedTask) {
        requireAllNonNull(target, editedTask);

        versionedTaskCollection.updateTask(target, editedTask);
        indicateTaskCollectionChanged();
    }

    @Override
    public void updateSortedTaskList(Comparator<Task> comparator) {
        requireNonNull(comparator);
        versionedTaskCollection.sort(comparator);
    }

    //=========== Filtered Task List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Task} backed by the internal list of
     * {@code versionedTaskCollection}
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
    public boolean canUndoTaskCollection() {
        return versionedTaskCollection.canUndo();
    }

    @Override
    public boolean canRedoTaskCollection() {
        return versionedTaskCollection.canRedo();
    }

    @Override
    public void undoTaskCollection() {
        versionedTaskCollection.undo();
        indicateTaskCollectionChanged();
    }

    @Override
    public void redoTaskCollection() {
        versionedTaskCollection.redo();
        indicateTaskCollectionChanged();
    }

    @Override
    public void commitTaskCollection() {
        versionedTaskCollection.commit();
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
        return versionedTaskCollection.equals(other.versionedTaskCollection)
                && filteredTasks.equals(other.filteredTasks);
    }

    //==========Import/Export===================================================================

    @Override
    public void exportTaskCollection(String filename, boolean shouldOverwrite, boolean isCsvFormat) {
        requireNonNull(filename);
        List<Task> lastShownList = getFilteredTaskList();
        TaskCollection exportCollection = new TaskCollection();
        exportCollection.setTasks(lastShownList);
        raise(new ExportRequestEvent(exportCollection, filename, shouldOverwrite, isCsvFormat));
    }

    @Override
    public void importTaskCollection(String filename, ImportConflictResolver mode) {
        requireNonNull(filename);
        requireNonNull(mode);
        conflictResolver = mode;
        raise(new ImportRequestEvent(filename));
    }

    @Override
    @Subscribe
    public void handleImportDataAvailableEvent(ImportDataAvailableEvent event) {
        //Handle merge conflict and what not
        ReadOnlyTaskCollection importData = event.data;
        for (Task task : importData.getTaskList()) {
            resolveImportConflict(task);
        }
        commitTaskCollection();
        updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
    }


    /**
     * Use the appropriate import conflict handler to resolve a conflict.
     * If there is no conflict, simply add it to the current TaskCollection.
     *
     * @param task the task to deconflict
     */
    private void resolveImportConflict(Task task) {
        if (!hasTask(task)) {
            addTask(task);
            return;
        }
        if (conflictResolver == null) {
            return;
        }
        conflictResolver.resolve(this::addTask, this::deleteTask, task);
    }
}

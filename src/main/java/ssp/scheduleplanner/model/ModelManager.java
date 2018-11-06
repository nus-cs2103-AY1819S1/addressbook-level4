package ssp.scheduleplanner.model;

import static java.util.Objects.requireNonNull;
import static ssp.scheduleplanner.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import ssp.scheduleplanner.commons.core.ComponentManager;
import ssp.scheduleplanner.commons.core.LogsCenter;
import ssp.scheduleplanner.commons.events.model.SchedulePlannerChangedEvent;
import ssp.scheduleplanner.model.category.Category;
import ssp.scheduleplanner.model.tag.Tag;
import ssp.scheduleplanner.model.task.Task;

/**
 * Represents the in-memory model of the Schedule Planner data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedSchedulePlanner versionedSchedulePlanner;
    private final ObservableList<Category> categories;
    private final FilteredList<Task> filteredTasks;
    private final FilteredList<Task> filteredArchivedTasks;

    /**
     * Initializes a ModelManager with the given Schedule Planner and userPrefs.
     */
    public ModelManager(ReadOnlySchedulePlanner schedulePlanner, UserPrefs userPrefs) {
        super();
        requireAllNonNull(schedulePlanner, userPrefs);

        logger.fine("Initializing with Schedule Planner: " + schedulePlanner + " and user prefs " + userPrefs);

        versionedSchedulePlanner = new VersionedSchedulePlanner(schedulePlanner);
        categories = versionedSchedulePlanner.getCategoryList();
        filteredTasks = new FilteredList<>(versionedSchedulePlanner.getTaskList());
        filteredArchivedTasks = new FilteredList<>(versionedSchedulePlanner.getArchivedTaskList());
    }

    public ModelManager() {
        this(new SchedulePlanner(), new UserPrefs());
    }

    /**
     * Sort filtered tasks.
     * @return SortedList containing tasks sorted according to priority.
     */
    public SortedList<Task> sortFilteredTasks() {
        SortedList<Task> sortedTasks = filteredTasks.sorted((a, b) -> Task.compare(a, b));
        return sortedTasks;
    }

    @Override
    public void resetData(ReadOnlySchedulePlanner newData) {
        versionedSchedulePlanner.resetData(newData);
        indicateSchedulePlannerChanged();
    }

    @Override
    public ReadOnlySchedulePlanner getSchedulePlanner() {
        return versionedSchedulePlanner;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateSchedulePlannerChanged() {
        raise(new SchedulePlannerChangedEvent(versionedSchedulePlanner));
    }

    @Override
    public void autoDeleteArchived() {
        versionedSchedulePlanner.autoDeleteArchived();
    }
    @Override
    public boolean hasTask(Task task) {
        requireNonNull(task);
        return versionedSchedulePlanner.hasTask(task);
    }


    @Override
    public boolean hasArchivedTask(Task archivedTask) {
        requireNonNull(archivedTask);
        return versionedSchedulePlanner.hasArchivedTask(archivedTask);
    }

    @Override
    public boolean hasTagInCategory(Tag tag, Category category) {
        requireNonNull(tag);
        requireNonNull(category);
        return versionedSchedulePlanner.hasTagInCategory(tag, category);
    }

    @Override
    public void deleteTask(Task target) {
        versionedSchedulePlanner.removeTask(target);
        indicateSchedulePlannerChanged();
    }

    @Override
    public boolean hasCategory(String name) {
        return versionedSchedulePlanner.hasCategory(name);
    }

    @Override
    public void addTask(Task task) {
        versionedSchedulePlanner.addTask(task);
        updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        indicateSchedulePlannerChanged();
    }

    @Override
    public void archiveTask(Task completedTask) {
        versionedSchedulePlanner.archiveTask(completedTask);
        indicateSchedulePlannerChanged();
    }

    @Override
    public void addTag(Tag tag, String categoryName) {
        versionedSchedulePlanner.addTag(tag, categoryName);
        indicateSchedulePlannerChanged();
    }

    @Override
    public void addCategory(String name) {
        Category category = new Category(name);
        versionedSchedulePlanner.addCategory(category);
        indicateSchedulePlannerChanged();
    }

    @Override
    public Category getCategory(String name) {
        return versionedSchedulePlanner.getCategory(name);
    }

    @Override
    public void updateTask(Task target, Task editedTask) {
        requireAllNonNull(target, editedTask);

        versionedSchedulePlanner.updateTask(target, editedTask);
        indicateSchedulePlannerChanged();
    }

    //=========== Filtered Task List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Task} backed by the internal list of
     * {@code versionedSchedulePlanner}
     */
    @Override
    public ObservableList<Task> getFilteredTaskList() {
        return FXCollections.unmodifiableObservableList(sortFilteredTasks());
    }

    @Override
    public ObservableList<Task> getFilteredArchivedTaskList() {
        return FXCollections.unmodifiableObservableList(filteredArchivedTasks);
    }

    @Override
    public ObservableList<Category> getCategoryList() {
        return categories;
    }

    @Override
    public void updateFilteredTaskList(Predicate<Task> predicate) {
        requireNonNull(predicate);
        filteredTasks.setPredicate(predicate);
    }

    @Override
    public void updateFilteredArchivedTaskList(Predicate<Task> predicate) {
        requireNonNull(predicate);
        filteredArchivedTasks.setPredicate(predicate);
    }


    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoSchedulePlanner() {
        return versionedSchedulePlanner.canUndo();
    }

    @Override
    public boolean canRedoSchedulePlanner() {
        return versionedSchedulePlanner.canRedo();
    }

    @Override
    public void undoSchedulePlanner() {
        versionedSchedulePlanner.undo();
        indicateSchedulePlannerChanged();
    }

    @Override
    public void redoSchedulePlanner() {
        versionedSchedulePlanner.redo();
        indicateSchedulePlannerChanged();
    }

    @Override
    public void commitSchedulePlanner() {
        versionedSchedulePlanner.commit();
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
        return versionedSchedulePlanner.equals(other.versionedSchedulePlanner)
                && (filteredTasks.equals(other.filteredTasks)
                || this.sortFilteredTasks().equals(other.sortFilteredTasks()));
    }

}

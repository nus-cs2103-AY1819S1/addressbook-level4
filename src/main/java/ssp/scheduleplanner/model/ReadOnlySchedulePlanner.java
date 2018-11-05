package ssp.scheduleplanner.model;

import javafx.collections.ObservableList;
import ssp.scheduleplanner.model.category.Category;
import ssp.scheduleplanner.model.task.Task;

/**
 * Unmodifiable view of an Schedule Planner
 */
public interface ReadOnlySchedulePlanner {

    /**
     * Returns an unmodifiable view of the tasks list.
     * This list will not contain any duplicate tasks.
     */
    ObservableList<Task> getTaskList();

    /**
     * Returns an unmodifiable view of the archived tasks list.
     */
    ObservableList<Task> getArchivedTaskList();

    /**
     * Returns an unmodifiable view of the categories.
     */
    ObservableList<Category> getCategoryList();


}


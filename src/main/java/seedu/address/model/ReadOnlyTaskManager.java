package seedu.address.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javafx.collections.ObservableList;
import seedu.address.model.achievement.AchievementRecord;
import seedu.address.model.task.Task;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyTaskManager {

    /**
     * Returns an unmodifiable view of the tasks list.
     * This list will not contain any duplicate tasks.
     */
    ObservableList<Task> getTaskList();

    /**
     * Returns a copy of the task manager's {@code AchievementRecord}.
     */
    AchievementRecord getAchievementRecord();

    /**
     * Returns list according to topological ordering of task
     */
    List<Task> getTopologicalOrder();

    /**
     * Returns the earliest time of all dependencies of a task according to the dependencies specified
     */
    Map<Task, Date> getEarliestDependentTime();

}

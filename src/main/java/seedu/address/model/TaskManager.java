package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.collections.ObservableList;
import seedu.address.model.achievement.AchievementRecord;
import seedu.address.model.achievement.Level;
import seedu.address.model.game.GameManager;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;

/**
 * Wraps all data at the task-manager level
 * Duplicates are not allowed (by .isSameTask comparison)
 */
public class TaskManager implements ReadOnlyTaskManager {

    private final UniqueTaskList tasks;
    private final AchievementRecord achievements;
    private final GameManager gameManager;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        tasks = new UniqueTaskList();
        achievements = new AchievementRecord();
        gameManager = new GameManager();
    }

    public TaskManager() {
    }

    /**
     * Creates an TaskManager using the Tasks in the {@code toBeCopied}
     */
    public TaskManager(ReadOnlyTaskManager toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// task list and achievement overwrite operations

    /**
     * Replaces the contents of the task list with {@code tasks}.
     * {@code tasks} must not contain duplicate tasks.
     */
    public void setTasks(List<Task> tasks) {
        this.tasks.setTasks(tasks);
    }

    /**
     * Replaces the contents of the task list with {@code tasks}.
     * {@code tasks} must not contain duplicate tasks.
     */
    public void setAchievements(AchievementRecord achievements) {
        this.achievements.resetData(achievements);
    }

    /**
     * Resets the existing data of this {@code TaskManager} with {@code newData}.
     */
    public void resetData(ReadOnlyTaskManager newData) {
        requireNonNull(newData);

        setTasks(newData.getTaskList());
        setAchievements(newData.getAchievementRecord());
    }

    //// task list related operation(s)


    /**
     * Check if any of the completed task has any invalid dependencies.
     *
     * @return true if there are any invalid dependencies.
     */
    public boolean hasInvalidDependencies() {

        Predicate<Task> isCompleteTask = Task::isStatusCompleted;

        // this.getTaskList() is called twice deliberately to ensure
        // usage of two different immutable lists to create streams.
        // Although referencing the same list and creating a stream
        // might be workable in this scenario, it is not done in this
        // to prevent additional bugs.

        // Stream of completed tasks
        Stream<Task> allCompleted =
            this.getTaskList().stream().filter(isCompleteTask);

        // Stream of uncompleted tasks
        Stream<Task> allUncompleted =
            this.getTaskList().stream().filter(isCompleteTask.negate());

        // Checks if any of the uncompleted tasks are dependencies of
        // completed tasks. Returns true if yes.
        return allUncompleted
            .anyMatch(uncompletedTask ->
                allCompleted
                    .anyMatch(completedTask ->
                        completedTask
                            .isDependentOn(uncompletedTask)));
    }

    //// task-level operations

    /**
     * Returns true if a task with the same identity as {@code task} exists in the task manager.
     */
    public boolean hasTask(Task task) {
        requireNonNull(task);
        return tasks.contains(task);
    }

    /**
     * Adds a task to the task manager.
     * The task must not already exist in the task manager.
     */
    public void addTask(Task p) {
        tasks.add(p);
    }

    /**
     * Replaces the given task {@code target} in the list with {@code editedTask}.
     * {@code target} must exist in the task manager.
     * The task identity of {@code editedTask} must not be the same as another existing task in the task manager.
     */
    public void updateTask(Task target, Task editedTask) {
        requireNonNull(editedTask);

        tasks.setTask(target, editedTask);

    }

    /**
     * Removes {@code key} from this {@code TaskManager}.
     * {@code key} must exist in the task manager.
     */
    public void removeTask(Task key) {
        tasks.remove(key);
    }

    /**
     * Updates tasks that are overdue
     */
    public void updateIfOverdue() {
        tasks.updateOverdue();

    }

    //// achievement related operation

    /**
     * @return the user's current level to the user.
     */
    public Level getLevel() {
        return achievements.getLevel();
    }

    /**
     * Updates the displayOption of the achievement record of the task manager.
     *
     * @param displayOption may take the value of 1, 2 or 3,
     *                      indicating all-time's, today's or this week's achievements are displayed on UI.
     */
    public void updateAchievementDisplayOption(int displayOption) {
        assert AchievementRecord.isValidDisplayOption(displayOption);

        achievements.setDisplayOption(displayOption);
    }

    /**
     * @return the {@code int} value representing the Xp.
     */
    public int getXpValue() {
        return achievements.getXpValue();
    }

    /**
     * Add the Xp for completing a new task to the {@code AchievementRecord} of the {@code TaskManager}.
     * Relevant fields in {@code AchievementRecord}, such as xp, level, number of tasks completed and the time-based
     * achievement fields are updated accordingly.
     */
    public void addXp(Integer xp) {
        requireNonNull(xp);

        achievements.incrementAchievementsWithNewXp(xp);
    }

    /**
     * Calculates the amount of XP that would be gained by changing taskFrom into taskTo.
     *
     * @param taskFrom The initial task.
     * @param taskTo   The resultant task.
     * @return The XP gained.
     */
    public int appraiseXpChange(Task taskFrom, Task taskTo) {
        if (taskFrom == null || taskTo == null) {
            throw new NullPointerException();
        }

        return gameManager.appraiseXpChange(taskFrom, taskTo);
    }

    public List<Task> getTopologicalOrder() {
        DependencyGraph dg = new DependencyGraph(this.getTaskList());
        List<String> hashes = dg.topologicalSort();
        return getTasksFromHashes(hashes);
    }

    public List<Task> getTasksFromHashes(List<String> hashes) {
        HashMap<String, Task> tasks = new HashMap<>();
        for (Task task : this.getTaskList()) {
            tasks.put(Integer.toString(task.hashCode()), task);
        }
        return hashes.stream().map((hash) -> tasks.get(hash)).collect(Collectors.toList());
    }

    //// util methods

    @Override
    public String toString() {
        return tasks.asUnmodifiableObservableList().size() + " tasks";
        // TODO: refine later
    }

    @Override
    public ObservableList<Task> getTaskList() {
        return tasks.asUnmodifiableObservableList();
    }

    @Override
    public AchievementRecord getAchievementRecord() {
        AchievementRecord copy = new AchievementRecord();
        copy.resetData(achievements);
        return copy;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof TaskManager // instanceof handles nulls
            && tasks.equals(((TaskManager) other).tasks)
            && achievements.equals(((TaskManager) other).achievements));
    }

    @Override
    public int hashCode() {
        return Objects.hash(tasks, achievements);
    }
}

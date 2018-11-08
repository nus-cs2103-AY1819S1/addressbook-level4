package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.address.model.achievement.AchievementRecord;
import seedu.address.model.achievement.Level;
import seedu.address.model.game.GameManager;
import seedu.address.model.task.DueDate;
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
     * @return  the user's current level to the user.
     */
    public Level getLevel() {
        return achievements.getLevel();
    }

    /**
     * Updates the displayOption of the achievement record of the task manager.
     *
     * @param displayOption may take the value of 1, 2 or 3,
     * indicating all-time's, today's or this week's achievements are displayed on UI.
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
     * @param taskFrom The initial task.
     * @param taskTo The resultant task.
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
        for (Task task: this.getTaskList()) {
            tasks.put(Integer.toString(task.hashCode()), task);
        }
        return hashes.stream().map((hash) -> tasks.get(hash)).collect(Collectors.toList());
    }

    /**
     * Returns the earliest time among all the dependencies of a task
     * @return
     */
    public Map<Task, DueDate> getEarliestDependentTime() {
        DependencyGraph dg = new DependencyGraph(this.getTaskList());

        Map<Task, Set<Task>> graph = getGraphOfTasksFromGraphOfHash(dg.getPrunedGraph());
        HashMap<Task, DueDate> visited = new HashMap<Task, DueDate>();
        for (Task task: graph.keySet()) {
            if (!visited.containsKey(task)) {
                getEarliestDependentTimeHelper(graph, visited, task);
            }
        }
        return visited;
    }
    /**
     * Returns the earliest time among all the dependencies of a task
     * @return
     */
    public DueDate getEarliestDependentTimeForNode(Task node) {
        DependencyGraph dg = new DependencyGraph(this.getTaskList());

        Map<Task, Set<Task>> graph = getGraphOfTasksFromGraphOfHash(dg.getPrunedGraph());
        HashMap<Task, DueDate> visited = new HashMap<>();
        return getEarliestDependentTimeHelper(graph, visited, node);

    }

    /**
     * Helper performs a dfs on the dependancy graph to find the earliest time of a task dependant among all its
     * dependencies
     * @param graph
     * @param result
     * @param node
     * @return
     */
    public DueDate getEarliestDependentTimeHelper(Map<Task, Set<Task>> graph, Map<Task, DueDate> result, Task node) {
        if (result.containsKey(node)) {
            return result.get(node);
        }
        DueDate earliestDate = node.getDueDate();
        for (Task dependee: graph.get(node)) {
            DueDate consideredDate = getEarliestDependentTimeHelper(graph, result, dependee);
            if (earliestDate == null || consideredDate.compareTo(earliestDate) < 0) {
                earliestDate = consideredDate;
            }
        }

        result.put(node, earliestDate);
        return earliestDate;
    }

    public Map<Task, Set<Task>> getGraphOfTasksFromGraphOfHash(Map<String, Set<String>> preGraph) {
        HashMap<String, Task> tasks = new HashMap<>();
        for (Task task: this.getTaskList()) {
            tasks.put(Integer.toString(task.hashCode()), task);
        }
        HashMap<Task, Set<Task>> result = new HashMap<>();
        for (Map.Entry<String, Set<String>> entry: preGraph.entrySet()) {
            String hash = entry.getKey();
            Set<String> dependencies = entry.getValue();

            Set<Task> newDependencies = new HashSet<Task>();
            for (String dependency: dependencies) {
                newDependencies.add(tasks.get(dependency));
            }
            result.put(tasks.get(hash), newDependencies);
        }
        return result;
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

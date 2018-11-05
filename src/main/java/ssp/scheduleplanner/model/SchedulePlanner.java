package ssp.scheduleplanner.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import ssp.scheduleplanner.model.category.Category;
import ssp.scheduleplanner.model.category.UniqueCategoryList;
import ssp.scheduleplanner.model.tag.Tag;
import ssp.scheduleplanner.model.task.Task;
import ssp.scheduleplanner.model.task.TaskList;
import ssp.scheduleplanner.model.task.UniqueTaskList;

/**
 * Wraps all data at the schedule-planner level
 * Duplicates are not allowed (by .isSameTask comparison)
 */
public class SchedulePlanner implements ReadOnlySchedulePlanner {

    private final UniqueCategoryList categories;
    private final UniqueTaskList tasks;
    private final TaskList archivedTasks;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        categories = new UniqueCategoryList();
        tasks = new UniqueTaskList();
        archivedTasks = new TaskList();
    }

    public SchedulePlanner() {}

    /**
     * Creates an SchedulePlanner using the Tasks in the {@code toBeCopied}
     */
    public SchedulePlanner(ReadOnlySchedulePlanner toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the task list with {@code tasks}.
     * {@code tasks} must not contain duplicate tasks.
     */
    public void setTasks(List<Task> tasks) {
        this.tasks.setTasks(tasks);
    }

    /**
     * Replaces the contents of the archived task list with {@code archivedTasks}.
     */
    public void setArchivedTasks(List<Task> archivedTasks) {
        this.archivedTasks.setTasks(archivedTasks);
    }

    /**
     * Replaces the contents of the category list with {@code categories}.
     */
    public void setCategories(List<Category> categories) {
        this.categories.setCategories(categories);
    }

    /**
     * Resets the existing data of this {@code SchedulePlanner} with {@code newData}.
     */
    public void resetData(ReadOnlySchedulePlanner newData) {
        requireNonNull(newData);

        setCategories(newData.getCategoryList());
        setTasks(newData.getTaskList());
        setArchivedTasks(newData.getArchivedTaskList());

    }

    //// task-level operations

    /**
     * Returns true if a task with the same identity as {@code task} exists in the task list of schedule planner.
     */
    public boolean hasTask(Task task) {
        requireNonNull(task);
        return tasks.contains(task);
    }

    /**
     * Returns true if a archived task with the same identity as {@code archivedTask} exists in the archived task list
     * of schedule planner.
     */
    public boolean hasArchivedTask(Task archivedTask) {
        requireNonNull(archivedTask);
        return archivedTasks.contains(archivedTask);
    }

    /**
     * Returns true if given category is valid and contains given tag.
     * @param tag
     * @param category
     * @return
     */
    public boolean hasTagInCategory(Tag tag, Category category) {
        requireNonNull(tag);
        requireNonNull(category);
        return (hasCategory(category) && category.hasTag(tag));
    }

    /**
     * Returns true if a category with the same identity as {@code category} exists in existing categories of the
     * schedule planner.
     */
    public boolean hasCategory(Category category) {
        requireNonNull(category);
        return categories.contains(category);
    }

    /**
     * Returns if an existing category in schedule planner has the same name as {@code name}.
     */
    public boolean hasCategory(String name) {
        requireNonNull(name);

        return categories.contains(name);
    }
    /**
     * Adds a task to the task list of schedule planner.
     * The task must not already exist in the current task list of schedule planner.
     */
    public void addTask(Task p) {

        tasks.add(p);
    }

    /**
     * Archive a done task.
     */
    public void archiveTask(Task p) {
        archivedTasks.add(p);
        tasks.remove(p);
    }

    public void addCategory(Category cat) {
        categories.add(cat);
    }

    /**
     * Adds a tag to the given category of schedule planner.
     * The tag must not already exist under given category.
     */
    public void addTag(Tag tag, String categoryName) {


    }

    /**
     * Adds an archived task to archived task list.
     */
    public void addArchivedTask(Task p) {
        archivedTasks.add(p);
    }


    /**
     * Replaces the given task {@code target} in the list with {@code editedTask}.
     * {@code target} must exist in the address book.
     * The task identity of {@code editedTask} must not be the same as another existing task in the schedule planner.
     */
    public void updateTask(Task target, Task editedTask) {
        requireNonNull(editedTask);

        tasks.setTask(target, editedTask);
    }

    /**
     * Removes {@code key} from this {@code SchedulePlanner}.
     * {@code key} must exist in the address book.
     */
    public void removeTask(Task key) {
        tasks.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return (tasks.asUnmodifiableObservableList().size() + " tasks");
        // TODO: refine later
    }

    @Override
    public ObservableList<Task> getTaskList() {
        return tasks.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Task> getArchivedTaskList() {
        return archivedTasks.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Category> getCategoryList() {
        return categories.asUnmodifiableObservableList();
    }

    /**
     * Get corresponding category from schedule planner.
     * @param name
     * @return category with the same name as given name.
     */
    public Category getCategory(String name) {
        return categories.getCategory(name);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SchedulePlanner // instanceof handles nulls
                && categories.equals(((SchedulePlanner) other).categories)
                && tasks.equals(((SchedulePlanner) other).tasks))
                && archivedTasks.equals(((SchedulePlanner) other).archivedTasks);
    }

    @Override
    public int hashCode() {
        return tasks.hashCode();
    }
}

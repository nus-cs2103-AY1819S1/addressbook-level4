package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASKS;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.task.Status;
import seedu.address.model.task.Task;

/**
 * Completes command given by an {@code Index} or {@code Task<Predicate>}
 */
public class CompleteCommand extends Command {

    public static final String COMMAND_WORD = "complete";
    public static final String MESSAGE_SUCCESS = "Good job! You have completed your task:\n%1$s";
    public static final String MESSAGE_NO_COMPLETABLE_TASK_IDENTIFIED_BY_LABEL = "There are no tasks to "
        + "be found via your given label";
    public static final String MESSAGE_ALREADY_COMPLETED = "This task has already been completed";
    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Completes the task identified by the Index number used in the displayed task list or a"
        + " Label but not both.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + COMMAND_WORD + " 1\n"
        + "Parameters: l/LABEL\n"
        + "Example: " + COMMAND_WORD + " l/friends";

    // Execution of completion of tasks will differ based on whether it is intended to be a batch operation
    private final boolean isPredicateBasedBatchComplete;
    private Index targetIndex;
    private Predicate<Task> taskPredicate;

    /**
     * Constructor for single Index based execution
     */
    public CompleteCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.isPredicateBasedBatchComplete = false;
        this.targetIndex = targetIndex;
    }

    /**
     * Constructor for batch predicate based execution
     */
    public CompleteCommand(Predicate<Task> taskPredicate) {
        this.isPredicateBasedBatchComplete = true;
        this.taskPredicate = taskPredicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        String completedTasksOutput;

        if (isPredicateBasedBatchComplete) {
            completedTasksOutput = completeAllTasksReturnStringOfTasks(model);
        } else {
            List<Task> lastShownList = model.getFilteredTaskList();
            completedTasksOutput = completeOneTaskReturnStringOfTask(
                targetIndex, lastShownList, model);
        }

        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        model.commitTaskManager();
        return new CommandResult(String.format(MESSAGE_SUCCESS, completedTasksOutput));
    }

    /**
     * Completes all completable tasks that fulfills {@code Task<Predicate>}, and returns a String
     * representing all completed tasks.
     * If a {@code CommandException} is thrown during the execution, all preceding completed tasks
     * will be rollbacked.
     *
     * @param modelToUpdate model to update
     * @return {@code String} representation of all the completed {@code Task}
     *
     * @throws CommandException if there are no completable task that fulfills {@code taskPredicate}
     */
    private String completeAllTasksReturnStringOfTasks(Model modelToUpdate)
        throws CommandException {

        Iterator<Task> taskIterator = generateSetOfCompletableTasks(this.taskPredicate, modelToUpdate)
            .iterator();
        String completedTasks = "";

        // throws an exception if there are no completable tasks
        if (!taskIterator.hasNext()) {
            throw new CommandException(MESSAGE_NO_COMPLETABLE_TASK_IDENTIFIED_BY_LABEL);
        }

        try {
            while (taskIterator.hasNext()) {
                Task taskToComplete = taskIterator.next();
                completedTasks += completeOneTaskReturnStringOfTask(
                    taskToComplete,
                    modelToUpdate) + "\n";
            }
        } catch (CommandException ce) {
            modelToUpdate.rollbackTaskManager();
            throw ce;
        }

        return completedTasks.trim();
    }

    /**
     * Completes the task supplied, updates the model without committing the model and returns
     * the {@code String} representation of the {@code Task}.
     *
     * @return {@code String} representing the completed {@code Task}
     *
     * @throws CommandException if the given {@code taskToComplete} is already completed
     */
    private String completeOneTaskReturnStringOfTask(Task taskToComplete, Model modelToUpdate)
        throws CommandException {

        if (taskToComplete.isCompleted()) {
            throw new CommandException(MESSAGE_ALREADY_COMPLETED);
        }

        Task completedTask = createCompletedTask(taskToComplete);

        modelToUpdate.updateTask(taskToComplete, completedTask);
        return completedTask.toString();
    }

    /**
     * Completes a task as identified by its index, updates the model without committing and returns
     * the {@code String} representation of the {@code Task}.
     *
     * @param lastShownList {@code List} containing all the valid tasks to complete
     * @return {@code String} representing the completed {@code Task}
     *
     * @throws CommandException if Index is invalid or task is already completed
     */
    private String completeOneTaskReturnStringOfTask(Index targetIndex,
                                                     List<Task> lastShownList,
                                                     Model modelToUpdate)
        throws CommandException {

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Task taskToComplete = lastShownList.get(targetIndex.getZeroBased());
        Task completedTask = createCompletedTask(taskToComplete);

        if (taskToComplete.isCompleted()) {
            throw new CommandException(MESSAGE_ALREADY_COMPLETED);
        }

        modelToUpdate.updateTask(taskToComplete, completedTask);
        return completedTask.toString();
    }

    /**
     * Generates a set of tasks that can be completed which also satisfies the supplied predicate.
     */
    private Set<Task> generateSetOfCompletableTasks(Predicate<Task> pred, Model model) {
        // Preserve a shallow copy of original list of task to restore later on
        List<Task> originalTasks = new ArrayList<>(model.getFilteredTaskList());

        // Add all of the completable tasks to setOfTasks
        Set<Task> setOfTasks = new HashSet<>();
        model.updateFilteredTaskList(pred.and(task -> !task.isCompleted()));
        List<Task> filteredList = model.getFilteredTaskList();
        setOfTasks.addAll(filteredList);

        // Restore filteredTaskList to it's original view
        model.updateFilteredTaskList(task -> originalTasks.contains(task));

        return setOfTasks;
    }

    /**
     * Returns a {@code Task} with it's status set to {@code Status.COMPLETED}.
     *
     * @param toComplete An immutable task passed to have its attributes copied
     * @return A new immutable task similar to toComplete but status is set to
     * {@code Status.COMPLETED}
     */
    public Task createCompletedTask(Task toComplete) {
        return new Task(
                toComplete.getName(),
                toComplete.getDueDate(),
                toComplete.getPriorityValue(),
                toComplete.getDescription(),
                toComplete.getLabels(),
                Status.COMPLETED,
                toComplete.getDependency());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (obj instanceof CompleteCommand) {
            CompleteCommand other = (CompleteCommand) obj;

            // If either targetIndex or taskPredicate is null in one command but not the other
            // return false
            if ((targetIndex == null && targetIndex != other.targetIndex)
                || taskPredicate == null && taskPredicate != other.taskPredicate) {
                return false;
            } else {
                return (targetIndex == other.targetIndex // short circuits on match
                    || targetIndex.equals(other.targetIndex)
                        && (taskPredicate == other.taskPredicate || // short circuits on match
                        taskPredicate.equals(other.taskPredicate))
                        && isPredicateBasedBatchComplete == other.isPredicateBasedBatchComplete);
            }
        } else {
            // superclass's implementation might pass,
            // although in this instance it's a == relationship.
            return super.equals(obj);
        }
    }
}

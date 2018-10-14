package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASKS;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.task.Status;
import seedu.address.model.task.Task;

/**
 * Lists all the commands entered by user from the start of app launch.
 */
public class CompleteCommand extends Command {

    public static final String COMMAND_WORD = "complete";
    public static final String MESSAGE_SUCCESS = "Good job! You have completed your task:\n%1$s";
    public static final String MESSAGE_NO_TASK_IDENTIFIED_BY_LABEL = "There are no tasks to "
        + "be found via your given label";
    public static final String MESSAGE_ALREADY_COMPLETED = "This task has already been completed";
    //TODO: Change prompt to indicate usage with labels
    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Completes the task identified by the Index number used in the displayed task list.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + COMMAND_WORD + " 1";

    private final Set<Index> targetIndexes;

    public CompleteCommand(Index targetIndex) {
        // The below expression (Set.of(...)) functions as a requireNonNull,
        // a NullPointerException will be thrown if targetIndex is null,
        // thus requireNonNull is omitted.
        this(Set.of(targetIndex));
    }

    public CompleteCommand(Set<Index> setOfIndexes) {
        requireNonNull(setOfIndexes);
        this.targetIndexes = setOfIndexes;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Task> lastShownList = model.getFilteredTaskList();

        String completedTasksOutput = completeAllTasksReturnStringOfTasks(lastShownList, model);

        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        model.commitTaskManager();
        return new CommandResult(String.format(MESSAGE_SUCCESS, completedTasksOutput));
    }

    /**
     * Completes all tasks in a set, and returns a String representing all completed tasks.
     * If a {@code CommandException} is thrown during the execution, all preceding completed tasks
     * will be rollbacked.
     * @param lastShownList {@code List} containing all the valid tasks to complete
     * @param modelToUpdate model to update
     * @return {@code String} representation of all the completed {@code Tasks}
     * @throws CommandException
     */
    private String completeAllTasksReturnStringOfTasks(List<Task> lastShownList,
                                                       Model modelToUpdate)
        throws CommandException {

        if (this.targetIndexes.isEmpty()) {
            throw new CommandException(MESSAGE_NO_TASK_IDENTIFIED_BY_LABEL);
        }

        // Iterates through the valid set of indexes and stores results in completedTasks.
        Iterator<Index> indexIterator = this.targetIndexes.iterator();
        String completedTasks = "";
        try {
            while (indexIterator.hasNext()) {
                Index indexOfTaskToComplete = indexIterator.next();
                completedTasks += completeOneTaskReturnStringOfTask(
                    indexOfTaskToComplete,
                    lastShownList,
                    modelToUpdate) + "\n";
            }
        } catch (CommandException ce) {
            modelToUpdate.rollbackTaskManager();
            throw ce;
        }
        return completedTasks.trim();
    }

    /**
     * Completes a task as identified by its index, updates the model wihtout committing and returns
     * the {@code String} representation of the {@code Task}.
     * @param targetIndex {@code Index} of task to update
     * @param lastShownList {@code List} containing all the valid tasks to complete
     * @param modelToUpdate model to update
     * @return {@code String} representing the completed {@code Task}
     * @throws CommandException
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
            Status.COMPLETED);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (obj instanceof CompleteCommand) {
            return targetIndexes.equals(((CompleteCommand) obj).targetIndexes);
        } else {
            // superclass's implementation might pass,
            // although in this instance it's a == relationship.
            return super.equals(obj);
        }
    }
}

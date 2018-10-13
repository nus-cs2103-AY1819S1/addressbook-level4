package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASKS;

import java.util.List;

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
    public static final String MESSAGE_DUPLICATE_COMPLETED_TASK = "This had a duplicate completed counterpart, please "
            + "delete this instead of completing it again,";
    public static final String MESSAGE_ALREADY_COMPLETED = "This task has already been completed";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Completes the task identified by the targetIndex number used in the displayed task list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    private final Index targetIndex;

    public CompleteCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Task> lastShownList = model.getFilteredTaskList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Task taskToComplete = lastShownList.get(targetIndex.getZeroBased());
        Task completedTask = createCompletedTask(taskToComplete);

        if (taskToComplete.isCompleted()) {
            throw new CommandException(MESSAGE_ALREADY_COMPLETED);
        }

        model.updateTask(taskToComplete, completedTask);
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        model.commitTaskManager();
        return new CommandResult(String.format(MESSAGE_SUCCESS, completedTask));
    }

    /**
     * Returns a {@code Task} with it's status set to {@code Status.COMPLETED}.
     * @param toComplete The task to o
     * @return
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
            return targetIndex.equals(((CompleteCommand) obj).targetIndex);
        } else {
            // superclass's implementation might pass,
            // although in this instance it's a == relationship.
            return super.equals(obj);
        }
    }
}

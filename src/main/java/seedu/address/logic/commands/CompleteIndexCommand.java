package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.task.Task;

/**
 * Completes command given by an {@code Index}
 */
public class CompleteIndexCommand extends CompleteCommand {

    private final Index targetIndex;

    /**
     * Constructor for single Index based execution
     */
    public CompleteIndexCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    /**
     * Completes all tasks identified by index.
     * Side Effects: updates model but does not commit
     *
     * @param model model to be updated
     * @return {@code String} representing the completed tasks
     * @throws CommandException
     */
    public String executePrimitivePrime(Model model) throws CommandException {
        requireNonNull(model);

        List<Task> lastShownList = model.getFilteredTaskList();
        return completeOneTaskReturnStringOfTask(targetIndex, lastShownList, model);

    }

    /**
     * Completes a task as identified by its index, updates the model without committing and returns
     * the {@code String} representation of the {@code Task}.
     *
     * @param lastShownList {@code List} containing all the valid tasks to complete
     * @return {@code String} representing the completed {@code Task}
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

        if (taskToComplete.isStatusCompleted()) {
            throw new CommandException(MESSAGE_ALREADY_COMPLETED);
        }

        modelToUpdate.updateTaskStatus(taskToComplete, completedTask);
        return completedTask.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (obj instanceof CompleteIndexCommand) { // instanceof handles nulls
            CompleteIndexCommand other = (CompleteIndexCommand) obj;

            return targetIndex.equals(other.targetIndex);
        } else {
            // superclass's implementation might pass,
            // although in this instance it's a == relationship.
            return super.equals(obj);
        }
    }
}

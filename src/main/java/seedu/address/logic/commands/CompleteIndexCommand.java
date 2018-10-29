package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASKS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.achievement.Level;
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

    @Override
    public CommandResult executePrimitive(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        String completedTasksOutput;

        // gets oldXp before updating tasks.
        int oldXp = model.getXpValue();
        Level oldLevel = model.getLevel();

        List<Task> lastShownList = model.getFilteredTaskList();
        completedTasksOutput = completeOneTaskReturnStringOfTask(
                targetIndex, lastShownList, model);

        // calculate change in xp to report to the user.
        int newXp = model.getXpValue();
        int changeInXp = newXp - oldXp;
        Level newLevel = model.getLevel();

        // Comparison with != operator is valid since Level is an enum.
        boolean levelHasChanged = newLevel != oldLevel;

        // model related operations
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        model.commitTaskManager();

        if (levelHasChanged) {
            return new CommandResult(
                    String.format(MESSAGE_SUCCESS_WITH_LEVEL, newLevel, changeInXp, completedTasksOutput));
        } else {
            return new CommandResult(String.format(MESSAGE_SUCCESS, changeInXp, completedTasksOutput));
        }
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

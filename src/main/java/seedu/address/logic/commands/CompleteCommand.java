package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASKS;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.achievement.Level;
import seedu.address.model.task.Status;
import seedu.address.model.task.Task;

/**
 * Completes command given with it's target identified by:
 * 1.{@code Index} or
 * 2.{@code Task<Predicate>}
 * <p>
 * This abstract class can allow for more inputs depending on the implementations of the subclasses.
 */
public abstract class CompleteCommand extends Command {

    public static final String COMMAND_WORD = "complete";
    public static final String MESSAGE_SUCCESS = "Good job! Your points have changed by %1$d.\n"
        + "You have completed your task(s):\n%2$s";
    public static final String MESSAGE_SUCCESS_WITH_LEVEL = "Congratulations you are now %1$s \n"
        + "Your points have changed by %2$d.\n"
        + "You have completed your task(s):\n%3$s";
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

    @Override
    public CommandResult executePrimitive(Model model, CommandHistory history)
        throws CommandException {

        // gets oldXp before updating tasks.
        int oldXp = model.getXpValue();
        Level oldLevel = model.getLevel();

        String completedTasksOutput;

        try {
            completedTasksOutput = executePrimitivePrime(model, history);

            // model related operations
            // throws an exception if there are unfulfilled dependencies.
            if (model.hasInvalidDependencies()) {
                throw new CommandException("Cannot complete task(s) as there are unfulfilled dependencies");
            }

        } catch (CommandException ce) {
            model.rollbackTaskManager();
            throw ce;
        }

        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        model.commitTaskManager();

        // calculate change in xp to report to the user.
        int newXp = model.getXpValue();
        int changeInXp = newXp - oldXp;
        Level newLevel = model.getLevel();

        // Comparison with != operator is valid since Level is an enum.
        boolean hasChangeinLevel = newLevel != oldLevel;

        return createCommandResult(hasChangeinLevel, newLevel, changeInXp, completedTasksOutput);
    }

    /**
     *
     * @param hasChangeinLevel
     * @param newLevel
     * @param changeInXp
     * @param completedTasksOutput
     * @return
     */
    private CommandResult createCommandResult(boolean hasChangeinLevel, Level newLevel, int changeInXp,
                                              String completedTasksOutput) {
        if (hasChangeinLevel) {
            return new CommandResult(
                String.format(MESSAGE_SUCCESS_WITH_LEVEL, newLevel, changeInXp, completedTasksOutput));
        } else {
            return new CommandResult(String.format(MESSAGE_SUCCESS, changeInXp, completedTasksOutput));
        }
    }

    abstract String executePrimitivePrime(Model model, CommandHistory history)
        throws CommandException;

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

}

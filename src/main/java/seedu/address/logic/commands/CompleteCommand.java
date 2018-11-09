package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASKS;

import java.util.ArrayList;
import java.util.List;

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

        // Calculation of change in xp and level is inlined
        // as the viewing of the whole process as procedures that relies
        // side effects of the methods called in between is now more easily
        // done.

        // gets oldXp before updating tasks.
        int oldXp = model.getXpValue();
        Level oldLevel = model.getLevel();

        String completedTasksOutput = completeTasks(model);

        // calculate change in xp to report to the user.
        int newXp = model.getXpValue();
        int changeInXp = newXp - oldXp;
        Level newLevel = model.getLevel();

        // Comparison with != operator is valid since Level is an enum.
        boolean hasChangeInLevel = newLevel != oldLevel;

        return createCommandResult(hasChangeInLevel, newLevel, changeInXp, completedTasksOutput);
    }

    /**
     * Execute the underlying subclass implementation of completing and updating completed
     * tasks to a model.
     * Additionally wraps the method in a try catch block to rollback the model and it's
     * filtered task list's view should any CommandExceptions by raised.
     * Side Effects: Updates a model, possibly rollback a model and it's filteredTaskList's view
     *
     * @param model model to update
     * @return {@code String} representation of completed tasks.
     * @throws CommandException
     */
    private String completeTasks(Model model)
        throws CommandException {
        // Creates a Runnable that reverts the filteredTaskList state to the
        // old filteredTaskList. (Runnable is created for readability)
        // Note: new ArrayList(...) is used here to create a shallow copy.
        List<Task> oldTaskList = new ArrayList<>(model.getFilteredTaskList());
        Runnable restoreFilteredTaskList = () -> model
            .updateFilteredTaskList(task -> oldTaskList.contains(task));

        String completedTasksOutput;
        // methods with side effects
        try {
            completedTasksOutput = executePrimitivePrime(model);
            processModelSideEffects(model);
        } catch (CommandException ce) {
            model.rollbackTaskManager();
            restoreFilteredTaskList.run();
            throw ce;
        }


        return completedTasksOutput;
    }

    /**
     * Handles additional side effects to the model.
     * Checks for the presence of invalid dependencies.
     * If there are none, update the model's view of the filtered task list
     * and commit changes to the model.
     * Else, throw a command exception.
     * @param model model which has uncommitted states
     * @throws CommandException
     */
    private void processModelSideEffects(Model model) throws CommandException {
        if (model.hasInvalidDependencies()) {
            throw new CommandException("Cannot complete task(s) as there are unfulfilled dependencies");
        }

        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        model.commitTaskManager();
    }


    /**
     * Helper function to abstract the logic in creating a command result.
     *
     * @param hasChangeInLevel true if new level needs to be displayed/
     * @param newLevel level tied to the updated model
     * @param changeInXp difference in xp between old and new model
     * @param completedTasksOutput {@code String} representation of the completed tasks
     * @return {@code CommandResult} encapsulating user readable messages
     */
    private CommandResult createCommandResult(boolean hasChangeInLevel, Level newLevel, int changeInXp,
                                              String completedTasksOutput) {
        if (hasChangeInLevel) {
            return new CommandResult(
                String.format(MESSAGE_SUCCESS_WITH_LEVEL, newLevel, changeInXp, completedTasksOutput));
        } else {
            return new CommandResult(String.format(MESSAGE_SUCCESS, changeInXp, completedTasksOutput));
        }
    }

    abstract String executePrimitivePrime(Model model) throws CommandException;

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

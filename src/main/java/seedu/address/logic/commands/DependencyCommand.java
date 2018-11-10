package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASKS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.DependencyGraph;
import seedu.address.model.Model;
import seedu.address.model.task.Task;

/**
 * Toggles a dependency between a dependant task and a dependee task.
 * The dependent task is dependent on dependee task.
 */
public class DependencyCommand extends Command {
    public static final String COMMAND_WORD = "dependency";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Toggles dependency of dependant on dependee.\n"
            + "Parameters: Index of task dependant, Index of task dependee\n"
            + "Example: \"" + COMMAND_WORD + " 1 2\" will add/remove the dependency of task at index 1 to task "
            + "at index 2";
    public static final String MESSAGE_ADD_SUCCESS = "You have added dependency for :\n[%1$s] to [%2$s]\n"
            + "[NOTE] To remove dependency call command on the same tasks. \n"
            + "i.e. " + COMMAND_WORD + " %3$s %4$s";
    public static final String MESSAGE_REMOVE_SUCCESS = "You have removed dependency for :\n[%1$s] to [%2$s]\n"
            + "[NOTE] To add dependency call command on the same tasks. \n"
            + "i.e. " + COMMAND_WORD + " %3$s %4$s";
    public static final String MESSAGE_CYCLIC_DEPENDENCY_FAILURE = "Dependency rejected as new dependency will "
            + "introduce a cyclic dependency";
    private final Index dependantIndex;
    private final Index dependeeIndex;

    public DependencyCommand(Index dependantIndex, Index dependeeIndex) {
        requireNonNull(dependantIndex);
        requireNonNull(dependeeIndex);
        this.dependantIndex = dependantIndex;
        this.dependeeIndex = dependeeIndex;
    }

    @Override
    public CommandResult executePrimitive(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Task> lastShownList = model.getFilteredTaskList();
        //Checking if indexes are out of bounds
        if (checkIndexesPastCurrentBounds(lastShownList, dependantIndex, dependeeIndex)) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        //Creating a different task depending on whether the task dependency currently exists
        Task taskDependant = lastShownList.get(dependantIndex.getZeroBased());
        Task taskDependee = lastShownList.get(dependeeIndex.getZeroBased());

        Task updatedTask;
        String message;
        //Toggle dependency
        if (taskDependant.isDependentOn(taskDependee)) {
            updatedTask = handleDependencyRemoval(taskDependant, taskDependee);
            message = MESSAGE_REMOVE_SUCCESS;
        } else {
            updatedTask = handleDependencyAddition(taskDependant, taskDependee);
            message = MESSAGE_ADD_SUCCESS;
        }
        //Passes all checks
        model.updateTask(taskDependant, updatedTask);
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        model.commitTaskManager();
        return new CommandResult(String.format(message, updatedTask.getName(), taskDependee.getName(),
                dependantIndex.getOneBased(), dependeeIndex.getOneBased()));
    }

    private boolean checkIndexesPastCurrentBounds(List<Task> lastShownList, Index dependantIndex, Index dependeeIndex) {
        return dependantIndex.getZeroBased() >= lastShownList.size()
                || dependeeIndex.getZeroBased() >= lastShownList.size();
    }

    private Task handleDependencyRemoval(Task taskDependant, Task taskDependee) {
        //If taskDependant is already dependant on dependee, remove dependency
        return createUndependantTask(taskDependant, taskDependee);
    }

    private Task handleDependencyAddition(Task taskDependant, Task taskDependee) throws CommandException {
        //If taskDependant is not dependent on dependee, add dependency
        Task updatedTask = createDependantTask(taskDependant, taskDependee);
        DependencyGraph dg = new DependencyGraph(model.getTaskManager().getTaskList());
        //Checking if introducing dependency will create a cyclic dependency
        if (dg.checkCyclicDependency(updatedTask)) {
            throw new CommandException(MESSAGE_CYCLIC_DEPENDENCY_FAILURE);
        }
        return updatedTask;
    }

    /**
     * Returns a {@code Task} with the additional dependency added.
     * @param dependantTask An immutable task passed to have its attributes copied
     * @return A new immutable task similar to dependantTask but with additional dependency
     */
    public static Task createDependantTask(Task dependantTask, Task dependeeTask) {
        return new Task(
                dependantTask.getName(),
                dependantTask.getDueDate(),
                dependantTask.getPriorityValue(),
                dependantTask.getDescription(),
                dependantTask.getLabels(),
                dependantTask.getStatus(),
                dependantTask.getDependency().addDependency(dependeeTask)
        );
    }
    /**
     * Returns a {@code Task} with the dependency removed.
     * @param dependantTask An immutable task passed to have its attributes copied
     * @return A new immutable task similar to dependantTask but without dependency to dependee
     */
    public static Task createUndependantTask(Task dependantTask, Task dependeeTask) {
        return new Task(
                dependantTask.getName(),
                dependantTask.getDueDate(),
                dependantTask.getPriorityValue(),
                dependantTask.getDescription(),
                dependantTask.getLabels(),
                dependantTask.getStatus(),
                dependantTask.getDependency().removeDependency(dependeeTask)
        );
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (obj instanceof DependencyCommand) {
            return dependantIndex.equals(((DependencyCommand) obj).dependantIndex)
                    && dependeeIndex.equals(((DependencyCommand) obj).dependeeIndex);
        } else {
            // superclass's implementation might pass,
            // although in this instance it's a == relationship.
            return super.equals(obj);
        }
    }
}

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASKS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.task.Task;
/**
 * Initiates a depedency between a dependant task and a dependee task.
 * The dependent task is dependent on dependee task.
 */
public class DependencyCommand extends Command {
    public static final String COMMAND_WORD = "dependency";
    public static final String MESSAGE_SUCCESS = "You have added dependency for :\n%1$s";
    public static final String MESSAGE_ALREADY_DEPENDANT =
            "The dependee task is already dependent on the dependant task";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Dependency identified by hashcode of the task.\n"
            + "Parameters: Index of task dependee, Index of task dependant\n"
            + "Example: " + COMMAND_WORD + " 1 2";
    private final Index dependantIndex;
    private final Index dependeeIndex;
    public DependencyCommand(Index dependantIndex, Index dependeeIndex) {
        requireNonNull(dependantIndex);
        requireNonNull(dependeeIndex);
        this.dependantIndex = dependantIndex;
        this.dependeeIndex = dependeeIndex;
    }
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Task> lastShownList = model.getFilteredTaskList();
        if (dependantIndex.getZeroBased() >= lastShownList.size()
                || dependeeIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Task taskDependant = lastShownList.get(dependantIndex.getZeroBased());
        Task taskDependee = lastShownList.get(dependeeIndex.getZeroBased());
        if (taskDependee.getDependency().containsDependency(taskDependee)) {
            throw new CommandException(MESSAGE_ALREADY_DEPENDANT);
        }

        Task updatedTask = createDependantTask(taskDependant, taskDependee);
        model.updateTask(taskDependant, updatedTask);
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        model.commitTaskManager();
        return new CommandResult(String.format(MESSAGE_SUCCESS, updatedTask));
    }

    /**
     * Returns a {@code Task} with it's the additional dependency added.
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

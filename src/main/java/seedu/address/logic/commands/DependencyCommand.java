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
 * Lists all the commands entered by user from the start of app launch.
 */
//TODO: elaborate on what is dependant and dependee
public class DependencyCommand extends Command {
    public static final String COMMAND_WORD = "dependency";
    //TODO: Improve succcess message
    public static final String MESSAGE_SUCCESS = "You have added dependency for :\n%1$s";
    public static final String MESSAGE_ALREADY_DEPENDANT = "The dependee task is already dependent on the dependant task";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Dependency identified by hashcode of the task.\n"
            + "Parameters: Index of task dependee, Index of task dependant\n"
            + "Example: " + COMMAND_WORD + " 1 2";
    private final Index dependeeIndex;
    private final Index dependantIndex;
    public DependencyCommand(Index dependeeIndex, Index dependantIndex) {
        requireNonNull(dependeeIndex);
        requireNonNull(dependantIndex);
        this.dependeeIndex = dependeeIndex;
        this.dependantIndex = dependantIndex;
    }
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Task> lastShownList = model.getFilteredTaskList();
        if (dependeeIndex.getZeroBased() >= lastShownList.size() || dependantIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        Task taskDependee = lastShownList.get(dependeeIndex.getZeroBased());
        Task taskDependant = lastShownList.get(dependantIndex.getZeroBased());
        if (taskDependant.getDependency().containsDependency(taskDependant)) {
            throw new CommandException(MESSAGE_ALREADY_DEPENDANT);
        }
        Task updatedTask = createDependeeTask(taskDependee, taskDependant);


        model.updateTask(taskDependee, updatedTask);
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        model.commitTaskManager();
        return new CommandResult(String.format(MESSAGE_SUCCESS, updatedTask));
    }
    /**
     * Returns a {@code Task} with it's the additional dependancy added.
     * @param dependeeTask An immutable task passed to have its attributes copied
     * @return A new immutable task similar to dependeeTask but with additional dependency
     */
    public static Task createDependeeTask(Task dependeeTask, Task dependantTask) {
        return new Task(
                dependeeTask.getName(),
                dependeeTask.getDueDate(),
                dependeeTask.getPriorityValue(),
                dependeeTask.getDescription(),
                dependeeTask.getLabels(),
                dependeeTask.getStatus(),
                dependeeTask.getDependency().addDependency(dependantTask)
        );
    }




    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (obj instanceof DependencyCommand) {
            return dependeeIndex.equals(((DependencyCommand) obj).dependeeIndex);
        } else {
            // superclass's implementation might pass,
            // although in this instance it's a == relationship.
            return super.equals(obj);
        }
    }
}
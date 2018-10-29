package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASKS;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.achievement.Level;
import seedu.address.model.task.Task;

/**
 * Completes command given by a {@code Task<Predicate>}
 */
public class CompleteLabelCommand extends CompleteCommand {

    // Execution of completion of tasks will differ based on whether it is intended to be a batch operation
    private final Predicate<Task> taskPredicate;

    /**
     * Constructor for batch predicate based execution
     */
    public CompleteLabelCommand(Predicate<Task> taskPredicate) {
        requireNonNull(taskPredicate);
        this.taskPredicate = taskPredicate;
    }

    @Override
    public CommandResult executePrimitive(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        String completedTasksOutput;

        // gets oldXp before updating tasks.
        int oldXp = model.getXpValue();
        Level oldLevel = model.getLevel();

        completedTasksOutput = completeAllTasksReturnStringOfTasks(model);

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
     * Completes all completable tasks that fulfills {@code Task<Predicate>}, and returns a String
     * representing all completed tasks.
     * If a {@code CommandException} is thrown during the execution, all preceding completed tasks
     * will be rollbacked.
     *
     * @param modelToUpdate model to update
     * @return {@code String} representation of all the completed {@code Task}
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
     * @throws CommandException if the given {@code taskToComplete} is already completed
     */
    private String completeOneTaskReturnStringOfTask(Task taskToComplete, Model modelToUpdate)
            throws CommandException {

        if (taskToComplete.isStatusCompleted()) {
            throw new CommandException(MESSAGE_ALREADY_COMPLETED);
        }

        Task completedTask = createCompletedTask(taskToComplete);

        modelToUpdate.updateTaskStatus(taskToComplete, completedTask);
        return completedTask.toString();
    }

    /**
     * Generates a set of tasks that can be completed which also satisfies the supplied predicate.
     */
    private Set<Task> generateSetOfCompletableTasks(Predicate<Task> pred, Model model) {
        // Preserve a shallow copy of original list of task to restore later on
        List<Task> originalTasks = new ArrayList<>(model.getFilteredTaskList());

        // Add all of the completable tasks to setOfTasks
        model.updateFilteredTaskList(pred.and(task -> !task.isStatusCompleted()));
        List<Task> filteredList = model.getFilteredTaskList();
        Set<Task> setOfTasks = new HashSet<>(filteredList);

        // Restore filteredTaskList to it's original view
        model.updateFilteredTaskList(task -> originalTasks.contains(task));

        return setOfTasks;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (obj instanceof CompleteLabelCommand) { // accounts for obj being null
            CompleteLabelCommand other = (CompleteLabelCommand) obj;

            return taskPredicate.equals(other.taskPredicate);
        } else {
            // superclass's implementation might pass,
            // although in this instance it's a == relationship.
            return super.equals(obj);
        }
    }
}

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
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

    /**
     * Completes all tasks identified by label.
     * Side Effects: updates model but does not commit
     *
     * @param model model to be updated
     * @return {@code String} representing the completed tasks
     * @throws CommandException
     */
    public String executePrimitivePrime(Model model) throws CommandException {
        requireNonNull(model);

        return completeAllTasksReturnStringOfTasks(model);
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
    private String completeAllTasksReturnStringOfTasks(Model modelToUpdate) throws CommandException {

        Set<Task> tasksSet = generateSetOfCompletableTasks(this.taskPredicate, modelToUpdate);

        // throws an exception if there are no completable tasks
        if (tasksSet.isEmpty()) {
            throw new CommandException(MESSAGE_NO_COMPLETABLE_TASK_IDENTIFIED_BY_LABEL);
        }

        String completedTasks = completeTasksInSetUpdateModel(modelToUpdate, tasksSet);

        return completedTasks;
    }

    /**
     * Complete all the tasks in the given set and return a {@code String} representation of its output.
     *
     * @param modelToUpdate Model to update completed tasks to
     * @param taskSet Set of task to be completed
     * @return {@code String} representation of its output
     * @throws CommandException
     */
    private String completeTasksInSetUpdateModel(Model modelToUpdate, Set<Task> taskSet) throws CommandException {

        StringBuilder completedTasks = new StringBuilder();

        for (Task taskToComplete : taskSet) {
            completedTasks.append(completeOneTaskReturnStringOfTask(taskToComplete, modelToUpdate));
            completedTasks.append('\n');
        }
        return completedTasks.toString().trim();
    }

    /**
     * Completes the task supplied, updates the model without committing the model and returns
     * the {@code String} representation of the {@code Task}.
     *
     * @param taskToComplete a single task to complete
     * @param modelToUpdate Model to update completed tasks to
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
     *
     * @param pred predicate to identify tasks to be completed
     * @param model model to fetch completable tasks from
     * @return a set of tasks that are completed
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

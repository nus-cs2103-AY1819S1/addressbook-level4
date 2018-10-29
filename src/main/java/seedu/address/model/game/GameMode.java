package seedu.address.model.game;

import seedu.address.model.task.Status;
import seedu.address.model.task.Task;

// @@author chikchengyao

/**
 * A GameMode is a procedure for determining the exact XP amount gained when completing tasks.
 * Each particular method extends GameMode to describe its particular method of calculation.
 */
public abstract class GameMode {

    /**
     * Checks that the two supplied tasks are the same task, i.e., they are identical except
     * for completion status.
     *
     * @param task1 First task to be compared.
     * @param task2 Second task to be compared.
     */
    void checkTaskDetails(Task task1, Task task2) throws XpEvaluationException {
        if (!task1.getName().fullName.equals(task2.getName().fullName)) {
            throw new XpEvaluationException("Tasks have different name.");
        }

        if (!task1.getDueDate().valueDate.equals(task2.getDueDate().valueDate)) {
            throw new XpEvaluationException("Tasks have different due date.");
        }

        if (!task1.getPriorityValue().value.equals(task2.getPriorityValue().value)) {
            throw new XpEvaluationException("Tasks have different priority value.");
        }

        if (!task1.getDescription().value.equals(task2.getDescription().value)) {
            throw new XpEvaluationException("Tasks have different description.");
        }
    }

    /**
     * Makes sure that a task never remains or regresses in progress.
     * An overdue task cannot revert to in-progress, and a completed task cannot be
     * updated to be only overdue or in-progress.
     *
     * @param taskFrom
     * @param taskTo
     */
    public void checkTaskStatus(Task taskFrom, Task taskTo) {
        if (taskFrom.getStatus() == taskTo.getStatus()) {
            throw new XpEvaluationException("Task cannot be completed to same status.");
        }

        if (taskFrom.getStatus() == Status.COMPLETED) {
            throw new XpEvaluationException("Task cannot regress after being completed,"
                    + " except by undo.");
        }

        if (taskTo.getStatus() == Status.COMPLETED) {
            return;
        }

        if (taskFrom.getStatus() == Status.OVERDUE) {
            // There is a regression from overdue to in-progress
            throw new XpEvaluationException("Task cannot regress after being overdue.");
        }

        // Remaining path should be ok, proceed to return void.
    }

    void checkValidTasks(Task taskFrom, Task taskTo) {
        checkTaskDetails(taskFrom, taskTo);
        checkTaskStatus(taskFrom, taskTo);
    }

    abstract int appraiseXpChange(Task taskFrom, Task taskTo);
}

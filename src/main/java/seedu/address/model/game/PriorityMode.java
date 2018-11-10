package seedu.address.model.game;

import seedu.address.model.task.Task;

// @@author chikchengyao

/**
 *
 * A GameMode awarding XP based on the user-defined priority value.
 */
public class PriorityMode extends GameMode {

    public int getOverdueMultiplier() {
        return overdueMultiplier;
    }

    public int getCompletedMultiplier() {
        return completedMultiplier;
    }

    private int overdueMultiplier;
    private int completedMultiplier;

    public PriorityMode() {
        this(3, 6);
    }

    public PriorityMode(int overdueMultiplier, int completedMultiplier) {
        this.overdueMultiplier = overdueMultiplier;
        this.completedMultiplier = completedMultiplier;
    }

    @Override
    int appraiseXpChange(Task taskFrom, Task taskTo) {

        String rawPriorityValue = taskFrom.getPriorityValue().value;
        int priorityValue = Integer.parseInt(rawPriorityValue);

        if (taskFrom.isStatusOverdue()) {
            return priorityValue * overdueMultiplier;
        }

        // If not overdue, task is completed before deadline
        return priorityValue * completedMultiplier;
    }

    @Override
    public PriorityMode copy() {
        return new PriorityMode(overdueMultiplier, completedMultiplier);
    }
}

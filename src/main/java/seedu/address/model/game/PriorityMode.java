package seedu.address.model.game;

import seedu.address.model.task.Task;

// @@author chikchengyao

/**
 *
 * A GameMode awarding XP based on the user-defined priority value.
 */
public class PriorityMode extends GameMode {
    private int overdueMultiplier;
    private int completedMultiplier;

    public PriorityMode() {
        this(3, 6);
    }

    public PriorityMode(int overdueMultiplier, int completedMultiplier) {
        this.overdueMultiplier = overdueMultiplier;
        this.completedMultiplier = completedMultiplier;
    }

    public PriorityMode(int period, int overdueMultiplier, int completedMultiplier) {
        this(overdueMultiplier, completedMultiplier);
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

    @Override
    public int getPeriod() {
        // Priority mode does not use period, so an arbitrary value is returned.
        return -1;
    }

    @Override
    public int getLowXp() {
        return overdueMultiplier;
    }

    @Override
    public int getHighXp() {
        return completedMultiplier;
    }

    @Override
    public String getDescription() {
        return String.format("Completing a task will earn you %d times the priority value as xp, or %d times the xp "
                + "if the task is overdue.", completedMultiplier, overdueMultiplier);
    }
}

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

    PriorityMode() {
        this(3, 6);
    }

    PriorityMode(int overdueMultiplier, int completedMultiplier) {
        this.overdueMultiplier = overdueMultiplier;
        this.completedMultiplier = completedMultiplier;
    }

    @Override
    int appraiseXpChange(Task taskFrom, Task taskTo) {

        String rawPriorityValue = taskFrom.getPriorityValue().value;

        int priorityValue;

        try {
            priorityValue = Integer.valueOf(rawPriorityValue);
        } catch (NumberFormatException n) {
            priorityValue = Integer.MAX_VALUE;
        }

        int xpAward;

        try {
            if (taskFrom.isStatusOverdue()) {
                xpAward = Math.multiplyExact(priorityValue, overdueMultiplier);
            } else {
                // If not overdue, task is completed before deadline
                xpAward = Math.multiplyExact(priorityValue, completedMultiplier);
            }
        } catch (ArithmeticException a) {
            xpAward = Integer.MAX_VALUE;
        }

        return xpAward;
    }
}

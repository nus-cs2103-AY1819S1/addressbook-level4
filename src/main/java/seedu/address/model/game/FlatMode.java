package seedu.address.model.game;

import seedu.address.model.task.Task;

// @@author chikchengyao

/**
 * A game mode where all completed tasks give the same XP.
 */
public class FlatMode extends GameMode {

    private int overdueXp;
    private int completedXp;

    FlatMode() {
        this(25, 50);
    }

    FlatMode(int overdueXp, int completedXp) {
        this.overdueXp = overdueXp;
        this.completedXp = completedXp;
    }

    @Override
    public int appraiseXpChange(Task taskFrom, Task taskTo) {

        // Check that the two tasks are the same, but with different statuses
        checkValidTasks(taskFrom, taskTo);

        // if a task is moving between inProgress to Overdue, award no XP
        if (taskFrom.isInProgress() && taskTo.isOverdue()) {
            return 0;
        }

        // Else award points
        if (taskFrom.isInProgress() && taskTo.isCompleted()) {
            return completedXp;
        }

        // At this point, taskFrom.isOverdue() && taskTo.isCompleted()
        return overdueXp;

    }
}

package seedu.address.model.game;

import seedu.address.model.task.Task;

// @@author chikchengyao

/**
 * A game mode where all completed tasks give the same XP.
 */
public class FlatMode implements GameMode {

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

        // if task status is identical, award no XP
        if (taskFrom.getStatus() == taskTo.getStatus()) {
            return 0;
        }


        // if a task is moving between inProgress to Overdue, award no XP
        if (!taskFrom.isCompleted() && !taskTo.isCompleted()) {
            return 0;
        }

        // Else award points
        int xpFrom = appraiseTask(taskFrom);
        int xpTo = appraiseTask(taskTo);

        int xpDiff = xpTo - xpFrom;

        return xpDiff;
    }

    private int appraiseTask(Task task) {
        if (task.isCompleted()) {
            return completedXp;
        }

        if (task.isOverdue()) {
            return completedXp - overdueXp;
        }

        return 0;
    }
}

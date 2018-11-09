package seedu.address.model.game;

import java.util.Date;

import seedu.address.model.task.Task;

// @@author chikchengyao

/**
 * Offers a mode where a certain amount of XP is offered in the beginning, but the amount ramps
 * up over time if the user does not do the task. This helps the user prioritise tasks due soon.
 */
public class IncreasingMode extends GameMode {

    private int daysBefore;
    private int initialXp;
    private int boostedXp;

    IncreasingMode() {
        this(3, 30, 60);
    }

    /**
     * Sets the game mode to award {@code boostedXp} to tasks completed at least
     * {@code daysBefore} days before the deadline, linearly decreasing to {@overdueXp}
     * for tasks completed at or after the deadline.
     *
     * @param daysBefore  The time period of falling XP.
     * @param overdueXp   The minimum XP, awarded to overdue tasks.
     * @param completedXp The maximum XP, awarded to tasks completed early.
     */
    IncreasingMode(int daysBefore, int overdueXp, int completedXp) {
        this.daysBefore = daysBefore;
        this.initialXp = overdueXp;
        this.boostedXp = completedXp;
    }

    @Override
    public int appraiseXpChange(Task taskFrom, Task taskTo) {

        checkValidTasks(taskFrom, taskTo);

        // if a task is moving between inProgress to Overdue, award no XP
        if (!taskFrom.isStatusCompleted() && !taskTo.isStatusCompleted()) {
            return 0;
        }

        // Else award points

        // Get current time
        Date now = getCurrentDate();

        if (taskFrom.isStatusOverdue() && taskTo.isStatusCompleted()) {
            return initialXp;
        }

        double fraction = interpolateDate(daysBefore, now, taskTo.getDueDate().valueDate);
        double xpEarned = initialXp + (boostedXp - initialXp) * fraction;
        return (int) xpEarned;
    }

    /**
     * Gets Date object representing the current time, for comparison to the due date.
     * <p>
     * Separated out to facilitate mock dates during testing.
     *
     * @return A Date object representing the time now.
     */
    protected Date getCurrentDate() {
        return new Date();
    }

    /**
     * Gives how late the completion date is relative to the due date, as a fraction of
     * to the interval supplied (in days). If completion date precedes the due date by
     * more than the interval, the fraction is 0. If completion is later than
     * the due date, then the result is 1.
     *
     * @param days      The interval (in days) to compare against.
     * @param completed The completion date.
     * @param due       The due date.
     * @return How late the completion date is, as a fraction of the interval, between 0 and 1.
     */
    public double interpolateDate(int days, Date completed, Date due) {
        double earlyByMilliseconds = (due.getTime() - completed.getTime());
        double windowMilliseconds = days * 24 * 60 * 60 * 1000;

        if (earlyByMilliseconds < 0) {
            return 1;
        }

        if (earlyByMilliseconds > windowMilliseconds) {
            return 0;
        }

        return 1 - earlyByMilliseconds / windowMilliseconds;

    }
}

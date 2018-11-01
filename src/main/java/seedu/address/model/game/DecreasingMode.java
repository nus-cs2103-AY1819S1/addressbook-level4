package seedu.address.model.game;

import java.util.Date;

import seedu.address.model.task.Task;

// @@author chikchengyao

/**
 * Offers a mode where full XP is awarded for tasks done earlier than a certain time period
 * before the deadline, measured in days.
 */
public class DecreasingMode extends GameMode {

    private int daysBefore;
    private int overdueXp;
    private int completedXp;

    DecreasingMode() {
        this(7, 25, 50);
    }

    /**
     * Sets the game mode to award {@code completedXp} to tasks completed at least
     * {@code daysBefore} days before the deadline, linearly decreasing to {@overdueXp}
     * for tasks completed at or after the deadline.
     *
     * @param daysBefore  The time period of falling XP.
     * @param overdueXp   The minimum XP, awarded to overdue tasks.
     * @param completedXp The maximum XP, awarded to tasks completed early.
     */
    DecreasingMode(int daysBefore, int overdueXp, int completedXp) {
        this.daysBefore = daysBefore;
        this.overdueXp = overdueXp;
        this.completedXp = completedXp;
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
            return overdueXp;
        }

        double fraction = interpolateDate(daysBefore, now, taskTo.getDueDate().valueDate);
        double xpEarned = overdueXp + (completedXp - overdueXp) * fraction;
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
     * Gives how early the completion date is relative to the due date, as a fraction of
     * to the interval supplied (in days). If completion date precedes the due date by
     * more than the interval, the fraction is capped at 1. If completion is later than
     * the due date, then the result is 0.
     *
     * @param days      The interval (in days) to compare against.
     * @param completed The completion date.
     * @param due       The due date.
     * @return How early the completion date is, as a fraction of the interval, between 0 and 1.
     */
    public double interpolateDate(int days, Date completed, Date due) {
        double earlyByMilliseconds = (due.getTime() - completed.getTime());
        double windowMilliseconds = days * 24 * 60 * 60 * 1000;

        if (earlyByMilliseconds < 0) {
            return 0;
        }

        if (earlyByMilliseconds > windowMilliseconds) {
            return 1;
        }

        return earlyByMilliseconds / windowMilliseconds;

    }
}

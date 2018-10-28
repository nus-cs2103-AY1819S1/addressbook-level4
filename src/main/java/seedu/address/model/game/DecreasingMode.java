package seedu.address.model.game;

import seedu.address.model.task.Task;

import java.util.Date;

/**
 * Offers a mode where full XP is awarded for tasks done more than a certain time period
 * before the deadline, measured in days.
 */
public class DecreasingMode extends GameMode {

    private int daysBefore;
    private int overdueXp;
    private int completedXp;

    DecreasingMode() {
        this(7, 25, 50);
    }

    DecreasingMode(int daysBefore, int overdueXp, int completedXp) {
        this.daysBefore = daysBefore;
        this.overdueXp = overdueXp;
        this.completedXp = completedXp;
    }

    @Override
    public int appraiseXpChange(Task taskFrom, Task taskTo) {

        // if a task is moving between inProgress to Overdue, award no XP
        if (!taskFrom.isCompleted() && !taskTo.isCompleted()) {
            return 0;
        }

        // Else award points

        // Get current time
        Date now = new Date();

        if (taskFrom.isOverdue() && taskTo.isCompleted()) {
            return overdueXp;
        }

        double fraction = interpolateDate(now, taskTo.getDueDate().valueDate, daysBefore);
        double xpEarned = overdueXp + (completedXp - overdueXp) * fraction;
        return (int) xpEarned;
    }

    /**
     * Gives how early the completion date is relative to the due date, as a fraction of
     * to the interval supplied (in days). If completion date precedes the due date by
     * more than the interval, the fraction is capped at 1.
     *
     * @param completed The completion date. Cannot be after due date.
     * @param due       The due date. Cannot be before completion date.
     * @param days      The interval (in days) to compare against.
     * @return How early the completion date is, as a fraction of the interval, capped at 1.
     */
    public double interpolateDate(Date completed, Date due, int days) {
        double earlyByMilliseconds = (due.getTime() - completed.getTime());
        double windowMilliseconds = days * 24 * 60 * 60 * 1000;

        if (earlyByMilliseconds < 0) {
            throw new IllegalArgumentException("Completion date must be before due date.");
        }

        if (earlyByMilliseconds > windowMilliseconds) {
            return 1;
        }

        return earlyByMilliseconds / windowMilliseconds;

    }
}
